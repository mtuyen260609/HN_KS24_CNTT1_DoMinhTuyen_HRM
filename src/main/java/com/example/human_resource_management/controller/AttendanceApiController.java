package com.example.human_resource_management.controller;

import com.example.human_resource_management.dto.CheckInRequest;
import com.example.human_resource_management.entity.Attendance;
import com.example.human_resource_management.entity.Employee;
import com.example.human_resource_management.entity.SystemSetting;
import com.example.human_resource_management.entity.User;
import com.example.human_resource_management.repository.AttendanceRepository;
import com.example.human_resource_management.repository.EmployeeRepository;
import com.example.human_resource_management.repository.SystemSettingRepository;
import com.example.human_resource_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceApiController {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final SystemSettingRepository settingRepository;

    private static final double FACE_MATCH_THRESHOLD = 0.5;

    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn(@RequestBody CheckInRequest request, jakarta.servlet.http.HttpSession session) {
        String username = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } else {
            // Fallback for Demo UI which uses HttpSession
            String email = (String) session.getAttribute("email");
            if (email != null) {
                User u = userRepository.findByEmail(email).orElse(null);
                if (u != null) username = u.getUsername();
            }
        }
        
        if (username == null) return ResponseEntity.status(401).body("Vui lòng đăng nhập.");
        
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null || user.getEmployee() == null) {
            return ResponseEntity.badRequest().body("User not linked to an employee.");
        }

        Employee employee = user.getEmployee();

        // 1. Verify Face
        boolean faceAuthEnabled = Boolean.parseBoolean(
            settingRepository.findById("faceAuth").map(SystemSetting::getValue).orElse("true")
        );
        
        if (faceAuthEnabled) {
            if (request.getFaceDescriptor() == null || request.getFaceDescriptor().isEmpty()) {
                return ResponseEntity.badRequest().body("Vui lòng xác thực khuôn mặt.");
            }
            if (employee.getFaceDescriptor() == null || employee.getFaceDescriptor().isEmpty()) {
                return ResponseEntity.badRequest().body("Tài khoản chưa đăng ký dữ liệu khuôn mặt. Vui lòng liên hệ HR.");
            }

            double distance = calculateEuclideanDistance(
                parseDescriptor(employee.getFaceDescriptor()),
                request.getFaceDescriptor()
            );

            if (distance > FACE_MATCH_THRESHOLD) {
                return ResponseEntity.status(401).body("Khuôn mặt không khớp. Vui lòng thử lại!");
            }
        }

        // 2. Determine LATE or ON_TIME
        String checkInSetting = settingRepository.findById("checkInTime")
                .map(SystemSetting::getValue).orElse("08:00");
        LocalTime requireCheckIn = LocalTime.parse(checkInSetting, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime now = LocalTime.now();

        String status = "Hợp lệ";
        if (now.isAfter(requireCheckIn.plusMinutes(15))) {
            status = "Đi muộn (LATE)";
        } else if (now.isAfter(requireCheckIn)) {
            status = "Đi muộn";
        }

        // 3. Save Record
        LocalDate today = LocalDate.now();
        Optional<Attendance> existing = attendanceRepository.findByEmployeeAndWorkDate(employee, today);
        Attendance attendance;
        if (existing.isPresent()) {
            attendance = existing.get();
            // Re-checkin
            attendance.setCheckInTime(LocalDateTime.now());
            attendance.setCheckInLocation(request.getLocation());
            attendance.setStatus(status);
        } else {
            attendance = Attendance.builder()
                .employee(employee)
                .workDate(today)
                .checkInTime(LocalDateTime.now())
                .checkInLocation(request.getLocation())
                .status(status)
                .build();
        }

        attendanceRepository.save(attendance);
        return ResponseEntity.ok(attendance);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkOut(@RequestBody CheckInRequest request, jakarta.servlet.http.HttpSession session) {
        String username = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } else {
            String email = (String) session.getAttribute("email");
            if (email != null) {
                User u = userRepository.findByEmail(email).orElse(null);
                if (u != null) username = u.getUsername();
            }
        }
        
        if (username == null) return ResponseEntity.status(401).body("Vui lòng đăng nhập.");
        
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null || user.getEmployee() == null) {
            return ResponseEntity.badRequest().body("User not linked to an employee.");
        }

        Employee employee = user.getEmployee();
        LocalDate today = LocalDate.now();
        Optional<Attendance> existing = attendanceRepository.findByEmployeeAndWorkDate(employee, today);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body("Bạn chưa check-in hôm nay.");
        }

        Attendance attendance = existing.get();
        attendance.setCheckOutTime(LocalDateTime.now());
        attendance.setCheckOutLocation(request.getLocation());
        
        String checkOutSetting = settingRepository.findById("checkOutTime")
                .map(SystemSetting::getValue).orElse("17:30");
        LocalTime requireCheckOut = LocalTime.parse(checkOutSetting, DateTimeFormatter.ofPattern("HH:mm"));
        if (LocalTime.now().isBefore(requireCheckOut)) {
            attendance.setStatus(attendance.getStatus() + " & Về sớm");
        }

        attendanceRepository.save(attendance);
        return ResponseEntity.ok(attendance);
    }

    // Helper method to parse comma-separated string to float list
    private List<Float> parseDescriptor(String str) {
        String cleaned = str.replace("[", "").replace("]", "");
        return java.util.Arrays.stream(cleaned.split(","))
                .map(String::trim)
                .map(Float::parseFloat)
                .toList();
    }

    // Euclidean distance calculation
    private double calculateEuclideanDistance(List<Float> vec1, List<Float> vec2) {
        if (vec1.size() != vec2.size()) return Double.MAX_VALUE;
        double sum = 0.0;
        for (int i = 0; i < vec1.size(); i++) {
            sum += Math.pow(vec1.get(i) - vec2.get(i), 2);
        }
        return Math.sqrt(sum);
    }
}

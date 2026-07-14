package com.example.human_resource_management.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class DemoPageController {

    // --- Dynamic In-Memory Store ---
    public static class Employee {
        private String id;
        private String name;
        private String email;
        private String role;
        private String status;
        private String department;

        public Employee(String id, String name, String email, String role, String status, String department) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
            this.status = status;
            this.department = department;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
    }

    public static class LeaveRequest {
        private String id;
        private String employeeName;
        private String employeeEmail;
        private String type;
        private String startDate;
        private String endDate;
        private String reason;
        private String status; // Chờ duyệt, Đã duyệt, Từ chối
        private String tone;   // warning, success, danger

        public LeaveRequest(String id, String employeeName, String employeeEmail, String type, String startDate, String endDate, String reason, String status, String tone) {
            this.id = id;
            this.employeeName = employeeName;
            this.employeeEmail = employeeEmail;
            this.type = type;
            this.startDate = startDate;
            this.endDate = endDate;
            this.reason = reason;
            this.status = status;
            this.tone = tone;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getEmployeeName() { return employeeName; }
        public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
        public String getEmployeeEmail() { return employeeEmail; }
        public void setEmployeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getStartDate() { return startDate; }
        public void setStartDate(String startDate) { this.startDate = startDate; }
        public String getEndDate() { return endDate; }
        public void setEndDate(String endDate) { this.endDate = endDate; }
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getTone() { return tone; }
        public void setTone(String tone) { this.tone = tone; }
    }

    public static class AttendanceRecord {
        private String id;
        private String employeeName;
        private String employeeEmail;
        private String date;
        private String inTime;
        private String outTime;
        private String status; // Hợp lệ, Đi muộn, Về sớm, v.v.
        private String gps;
        private String selfie; // Base64 or placeholder

        public AttendanceRecord(String id, String employeeName, String employeeEmail, String date, String inTime, String outTime, String status, String gps, String selfie) {
            this.id = id;
            this.employeeName = employeeName;
            this.employeeEmail = employeeEmail;
            this.date = date;
            this.inTime = inTime;
            this.outTime = outTime;
            this.status = status;
            this.gps = gps;
            this.selfie = selfie;
        }

        public String getId() { return id; }
        public String getEmployeeName() { return employeeName; }
        public String getEmployeeEmail() { return employeeEmail; }
        public String getDate() { return date; }
        public String getInTime() { return inTime; }
        public void setOutTime(String outTime) { this.outTime = outTime; }
        public String getOutTime() { return outTime; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getGps() { return gps; }
        public String getSelfie() { return selfie; }
    }

    public static class SystemSettings {
        private String checkInTime = "08:00";
        private String checkOutTime = "17:30";
        private int gpsRadius = 200;
        private boolean faceAuth = true;

        public String getCheckInTime() { return checkInTime; }
        public void setCheckInTime(String checkInTime) { this.checkInTime = checkInTime; }
        public String getCheckOutTime() { return checkOutTime; }
        public void setCheckOutTime(String checkOutTime) { this.checkOutTime = checkOutTime; }
        public int getGpsRadius() { return gpsRadius; }
        public void setGpsRadius(int gpsRadius) { this.gpsRadius = gpsRadius; }
        public boolean isFaceAuth() { return faceAuth; }
        public void setFaceAuth(boolean faceAuth) { this.faceAuth = faceAuth; }
    }

    public static class PayrollRecord {
        private String period;
        private String status;
        private String tone;
        private String payday;
        private String note;

        public PayrollRecord(String period, String status, String tone, String payday, String note) {
            this.period = period;
            this.status = status;
            this.tone = tone;
            this.payday = payday;
            this.note = note;
        }

        public String getPeriod() { return period; }
        public void setPeriod(String period) { this.period = period; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getTone() { return tone; }
        public void setTone(String tone) { this.tone = tone; }
        public String getPayday() { return payday; }
        public void setPayday(String payday) { this.payday = payday; }
        public String getNote() { return note; }
        public void setNote(String note) { this.note = note; }
    }

    private static final List<Employee> employees = new CopyOnWriteArrayList<>();
    private static final List<LeaveRequest> leaveRequests = new CopyOnWriteArrayList<>();
    private static final List<AttendanceRecord> attendanceRecords = new CopyOnWriteArrayList<>();
    private static final SystemSettings settings = new SystemSettings();

    static {
        // Prepopulate system with sample data
        employees.add(new Employee("EMP001", "Nguyễn Văn Nhân Viên", "employee@demo.local", "Employee", "Active", "Phòng Kỹ thuật"));
        employees.add(new Employee("HR001", "Nguyễn Thị Nhân Sự", "hr@demo.local", "HR", "Active", "Phòng Nhân sự"));

        leaveRequests.add(new LeaveRequest("LV001", "Nguyễn Văn Nhân Viên", "employee@demo.local", "Nghỉ phép năm", "15/07", "17/07", "Giải quyết công việc gia đình", "Chờ duyệt", "warning"));
        leaveRequests.add(new LeaveRequest("LV002", "Trần Thị Bán Hàng", "employee2@demo.local", "Nghỉ ốm", "05/07", "05/07", "Khám sức khỏe định kỳ", "Đã duyệt", "success"));

        attendanceRecords.add(new AttendanceRecord("AT001", "Nguyễn Văn Nhân Viên", "employee@demo.local", "10/07", "08:05", "17:35", "Hợp lệ", "21.0285, 105.8542", ""));
        attendanceRecords.add(new AttendanceRecord("AT002", "Nguyễn Văn Nhân Viên", "employee@demo.local", "09/07", "08:10", "17:30", "Hợp lệ", "21.0285, 105.8542", ""));
        attendanceRecords.add(new AttendanceRecord("AT003", "Trần Thị Bán Hàng", "employee2@demo.local", "10/07", "08:25", "17:40", "Đi muộn", "21.0286, 105.8543", ""));
    }

    @ModelAttribute("ui")
    public UiContext ui(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null) role = "guest";
        return new UiContext(
                role,
                (String) session.getAttribute("displayName"),
                (String) session.getAttribute("email"),
                "employee".equals(role),
                "hr".equals(role)
        );
    }

    @GetMapping("/")
    public String root(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null) return "redirect:/login";
        return "employee".equals(role) ? "redirect:/attendance" : "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role != null) return "redirect:/";
        return "login";
    }

    @PostMapping("/session/start")
    public String startSession(@RequestParam String email, @RequestParam String password, HttpSession session) {
        if ("employee@demo.local".equals(email) && "123456".equals(password)) {
            session.setAttribute("role", "employee");
            session.setAttribute("displayName", "Nguyễn Văn Nhân Viên");
            session.setAttribute("email", email);
            return "redirect:/attendance";
        } else if ("hr@demo.local".equals(email) && "123456".equals(password)) {
            session.setAttribute("role", "hr");
            session.setAttribute("displayName", "Nguyễn Thị Nhân Sự");
            session.setAttribute("email", email);
            return "redirect:/dashboard";
        }
        return "redirect:/login?error=true";
    }

    @PostMapping("/session/end")
    public String endSession(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!"hr".equals(session.getAttribute("role"))) return "redirect:/attendance";
        model.addAttribute("activePage", "dashboard");

        // Compute metrics dynamically
        int totalStaff = employees.size();
        
        // Count checkins for today (mock date: 10/07 for demo consistency)
        long checkedInToday = attendanceRecords.stream()
                .filter(r -> "10/07".equals(r.getDate()))
                .count();
        
        long pendingLeaves = leaveRequests.stream()
                .filter(r -> "Chờ duyệt".equals(r.getStatus()))
                .count();

        model.addAttribute("metrics", List.of(
                metric("Tổng nhân sự", String.valueOf(totalStaff), "Hoạt động", "info"),
                metric("Chấm công hôm nay", checkedInToday + "/" + totalStaff, "Đã check-in", "success"),
                metric("Đơn nghỉ chờ duyệt", String.valueOf(pendingLeaves), "Cần xử lý", "warning")
        ));

        // Gather recent activities from attendance records
        List<Map<String, Object>> activities = new ArrayList<>();
        attendanceRecords.stream()
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .limit(5)
                .forEach(r -> {
                    activities.add(row(
                            "time", r.getDate() + " " + r.getInTime(),
                            "title", r.getEmployeeName() + " check-in (" + r.getStatus() + ")"
                    ));
                });
        model.addAttribute("activities", activities);

        // Attention needed staff (who are late or absent)
        long lateCount = attendanceRecords.stream()
                .filter(r -> "10/07".equals(r.getDate()) && "Đi muộn".equals(r.getStatus()))
                .count();
        model.addAttribute("lateCount", lateCount);

        return "dashboard";
    }

    @GetMapping("/attendance")
    public String attendance(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";
        model.addAttribute("activePage", "attendance");

        // Filter attendance for the logged-in user
        List<AttendanceRecord> userHistory = attendanceRecords.stream()
                .filter(r -> email.equals(r.getEmployeeEmail()))
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .toList();

        model.addAttribute("history", userHistory);
        model.addAttribute("settings", settings);
        return "attendance";
    }

    @PostMapping("/attendance/checkin")
    public String checkIn(@RequestParam(required = false) String gps, @RequestParam(required = false) String selfie, HttpSession session) {
        String email = (String) session.getAttribute("email");
        String name = (String) session.getAttribute("displayName");
        if (email == null) return "redirect:/login";

        String currentDate = "10/07"; // Hardcoded for matching prepopulated demo
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        
        // Determine status based on config settings
        String status = "Hợp lệ";
        if (currentTime.compareTo(settings.getCheckInTime()) > 0) {
            status = "Đi muộn";
        }

        String gpsCoord = (gps != null && !gps.isEmpty()) ? gps : "21.0285, 105.8542";
        String selfieImg = (selfie != null) ? selfie : "";

        // Remove existing record for today if exists to re-checkin
        attendanceRecords.removeIf(r -> email.equals(r.getEmployeeEmail()) && currentDate.equals(r.getDate()));

        attendanceRecords.add(0, new AttendanceRecord(
                "AT" + System.currentTimeMillis(),
                name,
                email,
                currentDate,
                currentTime,
                "",
                status,
                gpsCoord,
                selfieImg
        ));

        return "redirect:/attendance";
    }

    @PostMapping("/attendance/checkout")
    public String checkOut(HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";

        String currentDate = "10/07";
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

        // Find today's check-in
        for (AttendanceRecord r : attendanceRecords) {
            if (email.equals(r.getEmployeeEmail()) && currentDate.equals(r.getDate())) {
                r.setOutTime(currentTime);
                // Check if early checkout
                if (currentTime.compareTo(settings.getCheckOutTime()) < 0) {
                    r.setStatus(r.getStatus() + " & Về sớm");
                }
                break;
            }
        }

        return "redirect:/attendance";
    }

    @GetMapping("/leave")
    public String leave(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";
        model.addAttribute("activePage", "leave");

        // Calculate leave balance dynamically (starts at 12, decreases by approved leave days)
        long approvedDays = leaveRequests.stream()
                .filter(r -> email.equals(r.getEmployeeEmail()) && "Đã duyệt".equals(r.getStatus()))
                .count(); // Assuming 1 day per request for simplistic demo
        model.addAttribute("balance", String.valueOf(12 - approvedDays));

        // Filter user history
        List<LeaveRequest> userHistory = leaveRequests.stream()
                .filter(r -> email.equals(r.getEmployeeEmail()))
                .sorted((a, b) -> b.getId().compareTo(a.getId()))
                .toList();
        model.addAttribute("history", userHistory);

        return "leave";
    }

    @PostMapping("/leave/submit")
    public String submitLeave(@RequestParam String type, @RequestParam String startDate, @RequestParam String endDate, @RequestParam String reason, HttpSession session) {
        String email = (String) session.getAttribute("email");
        String name = (String) session.getAttribute("displayName");
        if (email == null) return "redirect:/login";

        // Convert date format from YYYY-MM-DD to DD/MM for display
        String startDisp = startDate.substring(8) + "/" + startDate.substring(5, 7);
        String endDisp = endDate.substring(8) + "/" + endDate.substring(5, 7);
        String dateRange = startDisp + " - " + endDisp;

        leaveRequests.add(0, new LeaveRequest(
                "LV" + (leaveRequests.size() + 1),
                name,
                email,
                type,
                dateRange,
                "",
                reason,
                "Chờ duyệt",
                "warning"
        ));

        return "redirect:/leave";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";
        model.addAttribute("activePage", "profile");

        // Get details from employee records
        Employee emp = employees.stream()
                .filter(e -> email.equals(e.getEmail()))
                .findFirst()
                .orElse(new Employee("EMP000", (String) session.getAttribute("displayName"), email, "Employee", "Active", "Kỹ thuật"));
        
        model.addAttribute("employee", emp);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String displayName, @RequestParam String phone, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";

        // Update in employees store
        for (Employee e : employees) {
            if (email.equals(e.getEmail())) {
                e.setName(displayName);
                break;
            }
        }

        // Update in session
        session.setAttribute("displayName", displayName);
        return "redirect:/profile";
    }

    @GetMapping("/management")
    public String management(Model model, HttpSession session) {
        if (!"hr".equals(session.getAttribute("role"))) return "redirect:/attendance";
        model.addAttribute("activePage", "management");
        model.addAttribute("employees", employees);
        return "management";
    }

    @PostMapping("/management/add")
    public String addEmployee(@RequestParam String name, @RequestParam String email, @RequestParam String role, @RequestParam String department) {
        String newId = "EMP00" + (employees.size() + 1);
        employees.add(new Employee(newId, name, email, role, "Active", department));
        return "redirect:/management";
    }

    @PostMapping("/management/delete")
    public String deleteEmployee(@RequestParam String id) {
        employees.removeIf(e -> id.equals(e.getId()));
        return "redirect:/management";
    }

    @GetMapping("/settings")
    public String settings(Model model, HttpSession session) {
        if (!"hr".equals(session.getAttribute("role"))) return "redirect:/attendance";
        model.addAttribute("activePage", "settings");
        model.addAttribute("settings", settings);
        return "settings";
    }

    @PostMapping("/settings/update")
    public String updateSettings(@RequestParam String checkInTime, @RequestParam String checkOutTime, @RequestParam int gpsRadius, @RequestParam(defaultValue = "false") boolean faceAuth) {
        settings.setCheckInTime(checkInTime);
        settings.setCheckOutTime(checkOutTime);
        settings.setGpsRadius(gpsRadius);
        settings.setFaceAuth(faceAuth);
        return "redirect:/settings";
    }

    @GetMapping("/approvals")
    public String approvals(Model model, HttpSession session) {
        if (!"hr".equals(session.getAttribute("role"))) return "redirect:/attendance";
        model.addAttribute("activePage", "approvals");
        model.addAttribute("requests", leaveRequests);
        return "approvals";
    }

    @PostMapping("/approvals/action")
    public String handleApproval(@RequestParam String id, @RequestParam String action) {
        for (LeaveRequest r : leaveRequests) {
            if (id.equals(r.getId())) {
                if ("approve".equals(action)) {
                    r.setStatus("Đã duyệt");
                    r.setTone("success");
                } else {
                    r.setStatus("Từ chối");
                    r.setTone("danger");
                }
                break;
            }
        }
        return "redirect:/approvals";
    }

    @GetMapping("/payroll")
    public String payroll(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";
        model.addAttribute("activePage", "payroll");

        // Personal payroll mock data
        List<PayrollRecord> records = List.of(
                new PayrollRecord("06/2026", "Đã thanh toán", "success", "05/07/2026", "Lương tháng 6 thực nhận"),
                new PayrollRecord("05/2026", "Đã thanh toán", "success", "05/06/2026", "Lương tháng 5 thực nhận"),
                new PayrollRecord("07/2026", "Chờ xử lý", "warning", "Đang xử lý", "Lương tạm tính tháng 7")
        );
        model.addAttribute("records", records);
        return "payroll";
    }

    private Map<String, Object> metric(String l, String v, String n, String t) { return row("label", l, "value", v, "note", n, "tone", t); }
    private Map<String, Object> row(Object... values) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i + 1 < values.length; i += 2) map.put(String.valueOf(values[i]), values[i + 1]);
        return map;
    }
}
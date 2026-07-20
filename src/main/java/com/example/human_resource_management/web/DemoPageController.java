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

import com.example.human_resource_management.entity.User;
import com.example.human_resource_management.entity.Department;
import com.example.human_resource_management.entity.Role;
import com.example.human_resource_management.entity.SystemSetting;
import java.time.LocalDateTime;
import com.example.human_resource_management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequiredArgsConstructor
public class DemoPageController {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final PayrollRepository payrollRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final AttendanceRepository attendanceRepository;
    private final SystemSettingRepository systemSettingRepository;

    @jakarta.annotation.PostConstruct
    @org.springframework.transaction.annotation.Transactional
    public void initDemoData() {
        if (userRepository.count() == 0) {
            Role employeeRole = roleRepository.findByName("ROLE_EMPLOYEE").orElseGet(() -> {
                Role r = new Role();
                r.setName("ROLE_EMPLOYEE");
                return roleRepository.save(r);
            });
            Role hrRole = roleRepository.findByName("ROLE_HR").orElseGet(() -> {
                Role r = new Role();
                r.setName("ROLE_HR");
                return roleRepository.save(r);
            });
            
            Department d1 = new Department();
            d1.setName("Phòng Kỹ thuật");
            departmentRepository.save(d1);
            
            Department d2 = new Department();
            d2.setName("Phòng Nhân sự");
            departmentRepository.save(d2);

            com.example.human_resource_management.entity.Employee e1 = new com.example.human_resource_management.entity.Employee();
            e1.setFullName("Nguyễn Văn Nhân Viên");
            e1.setDepartment(d1);
            e1.setBaseSalary(15000000L);
            employeeRepository.save(e1);

            User u1 = new User();
            u1.setUsername("employee");
            u1.setPassword(passwordEncoder.encode("123456"));
            u1.setEmail("employee@demo.local");
            u1.setRole(employeeRole);
            u1.setEmployee(e1);
            u1.setActive(true);
            userRepository.save(u1);

            com.example.human_resource_management.entity.Employee e2 = new com.example.human_resource_management.entity.Employee();
            e2.setFullName("Nguyễn Thị Nhân Sự");
            e2.setDepartment(d2);
            e2.setBaseSalary(25000000L);
            employeeRepository.save(e2);

            User u2 = new User();
            u2.setUsername("hr");
            u2.setPassword(passwordEncoder.encode("123456"));
            u2.setEmail("hr@demo.local");
            u2.setRole(hrRole);
            u2.setEmployee(e2);
            u2.setActive(true);
            userRepository.save(u2);
        } // end of userRepository.count() == 0 block

        // Force populate mock data if there are only 2 employees (the default ones)
        if (employeeRepository.count() <= 2) {
            Role employeeRole = roleRepository.findByName("ROLE_EMPLOYEE").orElse(null);
            Department d1 = departmentRepository.findAll().stream().filter(d -> "Phòng Kỹ thuật".equals(d.getName())).findFirst().orElse(null);
            
            Department d3 = new Department();
            d3.setName("Phòng Kinh doanh");
            departmentRepository.save(d3);

            String[] names = {"Trần Minh Tân", "Lê Hải Yến", "Phạm Quốc Bảo", "Hoàng Thu Thảo", "Vũ Đức Trí"};
            String[] emails = {"tan.tm@demo.local", "yen.lh@demo.local", "bao.pq@demo.local", "thao.ht@demo.local", "tri.vd@demo.local"};
            long[] salaries = {18000000L, 16500000L, 22000000L, 14000000L, 19500000L};

            for (int i = 0; i < names.length; i++) {
                com.example.human_resource_management.entity.Employee e = new com.example.human_resource_management.entity.Employee();
                e.setFullName(names[i]);
                e.setDepartment(i % 2 == 0 ? d1 : d3);
                e.setBaseSalary(salaries[i]);
                employeeRepository.save(e);

                User u = new User();
                u.setUsername(emails[i].split("@")[0]);
                u.setPassword(passwordEncoder.encode("123456"));
                u.setEmail(emails[i]);
                u.setRole(employeeRole);
                u.setEmployee(e);
                u.setActive(true);
                userRepository.save(u);

                // Generate some mock attendances for this month
                LocalDate today = LocalDate.now();
                for (int day = 1; day <= 20; day++) {
                    LocalDate workDate = today.withDayOfMonth(day);
                    if (workDate.getDayOfWeek().getValue() >= 6) continue; // Skip weekend
                    
                    com.example.human_resource_management.entity.Attendance a = new com.example.human_resource_management.entity.Attendance();
                    a.setEmployee(e);
                    a.setWorkDate(workDate);
                    a.setCheckInTime(workDate.atTime(7, 50 + (int)(Math.random() * 20)));
                    a.setCheckOutTime(workDate.atTime(17, 30 + (int)(Math.random() * 40)));
                    a.setStatus("Hợp lệ");
                    a.setCheckInLocation("21.0285, 105.8542");
                    attendanceRepository.save(a);
                }
                
                // Generate some leave requests
                if (i % 2 == 0) {
                    com.example.human_resource_management.entity.LeaveRequest req = new com.example.human_resource_management.entity.LeaveRequest();
                    req.setEmployee(e);
                    req.setStartDate(today.plusDays(1));
                    req.setEndDate(today.plusDays(2));
                    req.setReason("[Nghỉ phép năm] Giải quyết việc gia đình");
                    req.setStatus("Chờ duyệt");
                    leaveRequestRepository.save(req);
                }
            }
        }
    }

    @GetMapping("/demo/populate")
    public String populateMoreData() {
        return "redirect:/management";
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
    @org.springframework.transaction.annotation.Transactional
    public String startSession(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = userRepository.findByEmail(email).orElseGet(() -> userRepository.findByUsername(email).orElse(null));
        if (user == null) {
            user = userRepository.findByUsername(email).orElse(null);
        }
        if (user != null && (password.equals(user.getPassword()) || passwordEncoder.matches(password, user.getPassword()))) {
            String role = user.getRole().getName().replace("ROLE_", "").toLowerCase();
            session.setAttribute("role", role);
            if (user.getEmployee() != null) {
                session.setAttribute("displayName", user.getEmployee().getFullName());
            } else {
                session.setAttribute("displayName", user.getUsername());
            }
            session.setAttribute("email", user.getEmail() != null ? user.getEmail() : user.getUsername());
            session.setAttribute("username", user.getUsername());
            
            if (role.equals("employee")) {
                return "redirect:/attendance";
            } else {
                return "redirect:/dashboard";
            }
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

        long totalStaff = employeeRepository.count();
        
        LocalDate today = LocalDate.now();
        List<com.example.human_resource_management.entity.Attendance> todaysAttendance = attendanceRepository.findAll().stream().filter(a -> a.getWorkDate().equals(today)).toList();
        long checkedInToday = todaysAttendance.size();
        
        long pendingLeaves = leaveRequestRepository.findAll().stream()
                .filter(r -> "PENDING".equals(r.getStatus()) || "Chờ duyệt".equals(r.getStatus()))
                .count();

        model.addAttribute("metrics", List.of(
                metric("Tổng nhân sự", String.valueOf(totalStaff), "Hoạt động", "info"),
                metric("Chấm công hôm nay", checkedInToday + "/" + totalStaff, "Đã check-in", "success"),
                metric("Đơn nghỉ chờ duyệt", String.valueOf(pendingLeaves), "Cần xử lý", "warning")
        ));

        List<Map<String, Object>> activities = new ArrayList<>();
        attendanceRepository.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(5)
                .forEach(r -> {
                    activities.add(row(
                            "time", r.getWorkDate().toString() + (r.getCheckInTime() != null ? " " + r.getCheckInTime().toLocalTime().toString() : ""),
                            "title", r.getEmployee().getFullName() + " check-in (" + r.getStatus() + ")"
                    ));
                });
        model.addAttribute("activities", activities);

        long lateCount = todaysAttendance.stream()
                .filter(r -> "Đi muộn".equals(r.getStatus()))
                .count();
        model.addAttribute("lateCount", lateCount);

        return "dashboard";
    }

    @GetMapping("/attendance")
    public String attendance(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";
        model.addAttribute("activePage", "attendance");

        User user = userRepository.findByEmail(email).orElseGet(() -> userRepository.findByUsername(email).orElse(null));
        if (user != null && user.getEmployee() != null) {
            List<com.example.human_resource_management.entity.Attendance> userHistory = attendanceRepository.findByEmployeeIdOrderByWorkDateDesc(user.getEmployee().getId());
            model.addAttribute("history", userHistory);
        }
        
        SystemSetting checkInTime = systemSettingRepository.findById("checkInTime").orElse(new SystemSetting("checkInTime", "08:00"));
        SystemSetting checkOutTime = systemSettingRepository.findById("checkOutTime").orElse(new SystemSetting("checkOutTime", "17:30"));
        SystemSetting gpsRadius = systemSettingRepository.findById("gpsRadius").orElse(new SystemSetting("gpsRadius", "200"));
        SystemSetting faceAuth = systemSettingRepository.findById("faceAuth").orElse(new SystemSetting("faceAuth", "true"));
        
        Map<String, Object> settings = new HashMap<>();
        settings.put("checkInTime", checkInTime.getValue());
        settings.put("checkOutTime", checkOutTime.getValue());
        settings.put("gpsRadius", gpsRadius.getValue());
        settings.put("faceAuth", Boolean.parseBoolean(faceAuth.getValue()));
        model.addAttribute("settings", settings);
        
        return "attendance";
    }

    @PostMapping("/attendance/checkin")
    public String checkIn(@RequestParam(required = false) String gps, @RequestParam(required = false) String selfie, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";

        User user = userRepository.findByEmail(email).orElseGet(() -> userRepository.findByUsername(email).orElse(null));
        if (user != null && user.getEmployee() != null) {
            LocalDate today = LocalDate.now();
            com.example.human_resource_management.entity.Attendance existing = attendanceRepository.findByEmployeeAndWorkDate(user.getEmployee(), today).orElse(null);
            
            if (existing == null) {
                String checkInTimeStr = systemSettingRepository.findById("checkInTime").map(SystemSetting::getValue).orElse("08:00");
                LocalTime checkInTime = LocalTime.parse(checkInTimeStr);
                
                String status = "Hợp lệ";
                if (LocalTime.now().isAfter(checkInTime)) {
                    status = "Đi muộn";
                }
                
                com.example.human_resource_management.entity.Attendance attendance = new com.example.human_resource_management.entity.Attendance();
                attendance.setEmployee(user.getEmployee());
                attendance.setWorkDate(today);
                attendance.setCheckInTime(LocalDateTime.now());
                attendance.setCheckInLocation(gps);
                attendance.setStatus(status);
                attendanceRepository.save(attendance);
            }
        }
        return "redirect:/attendance";
    }

    @PostMapping("/attendance/checkout")
    public String checkOut(HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";

        User user = userRepository.findByEmail(email).orElseGet(() -> userRepository.findByUsername(email).orElse(null));
        if (user != null && user.getEmployee() != null) {
            LocalDate today = LocalDate.now();
            com.example.human_resource_management.entity.Attendance existing = attendanceRepository.findByEmployeeAndWorkDate(user.getEmployee(), today).orElse(null);
            
            if (existing != null) {
                existing.setCheckOutTime(LocalDateTime.now());
                String checkOutTimeStr = systemSettingRepository.findById("checkOutTime").map(SystemSetting::getValue).orElse("17:30");
                LocalTime checkOutTime = LocalTime.parse(checkOutTimeStr);
                
                if (LocalTime.now().isBefore(checkOutTime)) {
                    existing.setStatus(existing.getStatus() + " & Về sớm");
                }
                attendanceRepository.save(existing);
            }
        }

        return "redirect:/attendance";
    }

    @GetMapping("/leave")
    public String leave(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";
        model.addAttribute("activePage", "leave");

        User user = userRepository.findByEmail(email).orElseGet(() -> userRepository.findByUsername(email).orElse(null));
        if (user != null && user.getEmployee() != null) {
            List<com.example.human_resource_management.entity.LeaveRequest> userHistory = leaveRequestRepository.findByEmployeeIdOrderByCreatedAtDesc(user.getEmployee().getId());
            long approvedDays = userHistory.stream()
                    .filter(r -> "Đã duyệt".equals(r.getStatus()))
                    .mapToLong(r -> java.time.temporal.ChronoUnit.DAYS.between(r.getStartDate(), r.getEndDate()) + 1)
                    .sum();
            model.addAttribute("balance", String.valueOf(12 - approvedDays));
            model.addAttribute("history", userHistory);
        } else {
            model.addAttribute("balance", "12");
            model.addAttribute("history", new ArrayList<>());
        }

        return "leave";
    }

    @PostMapping("/leave/submit")
    public String submitLeave(@RequestParam String type, @RequestParam String startDate, @RequestParam String endDate, @RequestParam String reason, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";

        User user = userRepository.findByEmail(email).orElseGet(() -> userRepository.findByUsername(email).orElse(null));
        if (user != null && user.getEmployee() != null) {
            com.example.human_resource_management.entity.LeaveRequest req = new com.example.human_resource_management.entity.LeaveRequest();
            req.setEmployee(user.getEmployee());
            req.setStartDate(LocalDate.parse(startDate));
            req.setEndDate(LocalDate.parse(endDate));
            // Assuming we repurpose 'reason' or append type
            req.setReason("[" + type + "] " + reason);
            req.setStatus("Chờ duyệt");
            leaveRequestRepository.save(req);
        }

        return "redirect:/leave";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";
        model.addAttribute("activePage", "profile");
        
        User user = userRepository.findByEmail(email).orElseGet(() -> userRepository.findByUsername(email).orElse(null));
        if (user == null) {
            session.invalidate();
            return "redirect:/login";
        }
        
        model.addAttribute("user", user);
            if (user.getEmployee() != null) {
                model.addAttribute("employee", user.getEmployee());
                String fd = user.getEmployee().getFaceDescriptor();
                model.addAttribute("hasFace", fd != null && !fd.trim().isEmpty());
            } else {
                model.addAttribute("employee", new com.example.human_resource_management.entity.Employee());
                model.addAttribute("hasFace", false);
            }
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String displayName, @RequestParam String phone, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";

        User user = userRepository.findByEmail(email).orElseGet(() -> userRepository.findByUsername(email).orElse(null));
        if (user != null && user.getEmployee() != null) {
            user.getEmployee().setFullName(displayName);
            user.getEmployee().setPhone(phone);
            employeeRepository.save(user.getEmployee());
        }

        // Update in session
        session.setAttribute("displayName", displayName);
        return "redirect:/profile";
    }

    @GetMapping("/management")
    public String management(Model model, HttpSession session) {
        if (!"hr".equals(session.getAttribute("role"))) return "redirect:/attendance";
        model.addAttribute("activePage", "management");
        List<com.example.human_resource_management.entity.Employee> dbEmployees = employeeRepository.findAll();
        List<Map<String, Object>> uiEmployees = new ArrayList<>();
        for (com.example.human_resource_management.entity.Employee e : dbEmployees) {
            String roleStr = "Employee";
            String emailStr = "";
            User u = userRepository.findByEmployee(e).orElse(null);
            if (u != null) {
                roleStr = u.getRole().getName().replace("ROLE_", "");
                emailStr = u.getEmail();
            }
            uiEmployees.add(row(
                "id", String.valueOf(e.getId()),
                "name", e.getFullName(),
                "email", emailStr,
                "role", roleStr,
                "status", "Active",
                "department", e.getDepartment() != null ? e.getDepartment().getName() : ""
            ));
        }
        model.addAttribute("employees", uiEmployees);
        return "management";
    }

    @PostMapping("/management/add")
    public String addEmployee(@RequestParam String name, @RequestParam String email, @RequestParam String role, @RequestParam String department) {
        // Create DB Employee
        com.example.human_resource_management.entity.Employee dbEmp = new com.example.human_resource_management.entity.Employee();
        dbEmp.setFullName(name);
        
        // Find or create department
        Department dept = departmentRepository.findAll().stream().filter(d -> d.getName().equalsIgnoreCase(department)).findFirst().orElse(null);
        if (dept == null) {
            dept = new Department();
            dept.setName(department);
            dept = departmentRepository.save(dept);
        }
        dbEmp.setDepartment(dept);
        dbEmp = employeeRepository.save(dbEmp);
        
        // Create DB User
        User dbUser = new User();
        String username = email.split("@")[0];
        // Ensure unique username
        if (userRepository.existsByUsername(username)) {
            username = username + System.currentTimeMillis();
        }
        dbUser.setUsername(username);
        dbUser.setEmail(email);
        dbUser.setPassword(passwordEncoder.encode("123456"));
        
        String roleName = "ROLE_" + role.toUpperCase();
        Role dbRole = roleRepository.findByName(roleName).orElse(null);
        if (dbRole == null) {
            dbRole = new Role();
            dbRole.setName(roleName);
            dbRole = roleRepository.save(dbRole);
        }
        dbUser.setRole(dbRole);
        dbUser.setEmployee(dbEmp);
        dbUser.setActive(true);
        userRepository.save(dbUser);
        
        return "redirect:/management";
    }

    @PostMapping("/management/delete")
    public String deleteEmployee(@RequestParam String id) {
        try {
            Long empId = Long.parseLong(id);
            // Delete associated user first
            com.example.human_resource_management.entity.Employee e = employeeRepository.findById(empId).orElse(null);
            if (e != null) {
                User u = userRepository.findByEmployee(e).orElse(null);
                if (u != null) {
                    userRepository.delete(u);
                }
                employeeRepository.delete(e);
            }
        } catch (NumberFormatException ex) {
            // ignore
        }
        return "redirect:/management";
    }

    @GetMapping("/settings")
    public String settings(Model model, HttpSession session) {
        if (!"hr".equals(session.getAttribute("role"))) return "redirect:/attendance";
        model.addAttribute("activePage", "settings");
        
        SystemSetting checkInTime = systemSettingRepository.findById("checkInTime").orElse(new SystemSetting("checkInTime", "08:00"));
        SystemSetting checkOutTime = systemSettingRepository.findById("checkOutTime").orElse(new SystemSetting("checkOutTime", "17:30"));
        SystemSetting gpsRadius = systemSettingRepository.findById("gpsRadius").orElse(new SystemSetting("gpsRadius", "200"));
        SystemSetting faceAuth = systemSettingRepository.findById("faceAuth").orElse(new SystemSetting("faceAuth", "true"));
        
        Map<String, Object> settingsMap = new HashMap<>();
        settingsMap.put("checkInTime", checkInTime.getValue());
        settingsMap.put("checkOutTime", checkOutTime.getValue());
        settingsMap.put("gpsRadius", gpsRadius.getValue());
        settingsMap.put("faceAuth", Boolean.parseBoolean(faceAuth.getValue()));
        model.addAttribute("settings", settingsMap);
        
        return "settings";
    }

    @PostMapping("/settings/update")
    public String updateSettings(@RequestParam String checkInTime, @RequestParam String checkOutTime, @RequestParam String gpsRadius, @RequestParam(defaultValue = "false") boolean faceAuth) {
        systemSettingRepository.save(new SystemSetting("checkInTime", checkInTime));
        systemSettingRepository.save(new SystemSetting("checkOutTime", checkOutTime));
        systemSettingRepository.save(new SystemSetting("gpsRadius", gpsRadius));
        systemSettingRepository.save(new SystemSetting("faceAuth", String.valueOf(faceAuth)));
        return "redirect:/settings";
    }

    @GetMapping("/approvals")
    public String approvals(Model model, HttpSession session) {
        if (!"hr".equals(session.getAttribute("role"))) return "redirect:/attendance";
        model.addAttribute("activePage", "approvals");
        model.addAttribute("requests", leaveRequestRepository.findAll());
        return "approvals";
    }

    @PostMapping("/approvals/action")
    public String handleApproval(@RequestParam String id, @RequestParam String action, HttpSession session) {
        String email = (String) session.getAttribute("email");
        User approverUser = userRepository.findByEmail(email).orElseGet(() -> userRepository.findByUsername(email).orElse(null));
        
        try {
            Long reqId = Long.parseLong(id);
            com.example.human_resource_management.entity.LeaveRequest req = leaveRequestRepository.findById(reqId).orElse(null);
            if (req != null) {
                if ("approve".equals(action)) {
                    req.setStatus("Đã duyệt");
                } else {
                    req.setStatus("Từ chối");
                }
                if (approverUser != null) {
                    req.setApprover(approverUser.getEmployee());
                }
                leaveRequestRepository.save(req);
            }
        } catch (Exception e) {}
        return "redirect:/approvals";
    }

    @GetMapping("/payroll")
    public String payroll(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) return "redirect:/login";
        model.addAttribute("activePage", "payroll");

        User user = userRepository.findByEmail(email).orElseGet(() -> userRepository.findByUsername(email).orElse(null));
        if (user != null && user.getEmployee() != null) {
            List<com.example.human_resource_management.entity.Payroll> records = payrollRepository.findByEmployeeIdOrderByCreatedAtDesc(user.getEmployee().getId());
            model.addAttribute("records", records);
        } else {
            model.addAttribute("records", new ArrayList<>());
        }
        return "payroll";
    }

    @GetMapping("/reports")
    public String reports(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"hr".equals(role)) return "redirect:/login";
        model.addAttribute("activePage", "reports");
        
        List<Map<String, Object>> payrollReport = new ArrayList<>();
        int i = 1;
        List<com.example.human_resource_management.entity.Employee> employees = employeeRepository.findAll();
        for (com.example.human_resource_management.entity.Employee e : employees) {
            long baseSalary = e.getBaseSalary() != null ? e.getBaseSalary() : 15000000L;
            
            // Calculate real working days in current month
            LocalDate now = LocalDate.now();
            List<com.example.human_resource_management.entity.Attendance> attendances = attendanceRepository.findByEmployeeId(e.getId());
            long attendDays = 0;
            long penalties = 0;
            for (com.example.human_resource_management.entity.Attendance a : attendances) {
                if (a.getWorkDate().getMonth() == now.getMonth() && a.getWorkDate().getYear() == now.getYear()) {
                    if ("Hợp lệ".equals(a.getStatus())) {
                        attendDays++;
                    } else if (a.getStatus() != null && (a.getStatus().contains("Đi muộn") || a.getStatus().contains("Về sớm"))) {
                        attendDays++;
                        penalties += 100000;
                    }
                }
            }
            
            List<com.example.human_resource_management.entity.LeaveRequest> leaves = leaveRequestRepository.findByEmployeeIdOrderByCreatedAtDesc(e.getId());
            long leaveDays = 0;
            for (com.example.human_resource_management.entity.LeaveRequest req : leaves) {
                if ("Đã duyệt".equals(req.getStatus()) && req.getStartDate() != null && req.getEndDate() != null && req.getStartDate().getMonth() == now.getMonth() && req.getStartDate().getYear() == now.getYear()) {
                    leaveDays += java.time.temporal.ChronoUnit.DAYS.between(req.getStartDate(), req.getEndDate()) + 1;
                }
            }
            
            long workingDays = attendDays + leaveDays;
            long total = (baseSalary / 22) * workingDays - penalties;
            if (total < 0) total = 0;
            
            payrollReport.add(row(
                "stt", i++,
                "code", "EMP" + e.getId(),
                "name", e.getFullName(),
                "department", e.getDepartment() != null ? e.getDepartment().getName() : "",
                "baseSalary", baseSalary,
                "workingDays", workingDays,
                "bonus", -penalties,
                "total", total
            ));
        }
        model.addAttribute("payrollReport", payrollReport);
        
        return "reports";
    }

    @PostMapping("/reports/chot-luong")
    public String chotLuong(HttpSession session) {
        if (!"hr".equals(session.getAttribute("role"))) return "redirect:/attendance";
        
        List<com.example.human_resource_management.entity.Employee> employees = employeeRepository.findAll();
        LocalDate now = LocalDate.now();
        String monthYear = String.format("%02d/%d", now.getMonthValue(), now.getYear());
        
        for (com.example.human_resource_management.entity.Employee e : employees) {
            long baseSalary = e.getBaseSalary() != null ? e.getBaseSalary() : 15000000L;
            List<com.example.human_resource_management.entity.Attendance> attendances = attendanceRepository.findByEmployeeId(e.getId());
            long attendDays = 0;
            long penalties = 0;
            for (com.example.human_resource_management.entity.Attendance a : attendances) {
                if (a.getWorkDate().getMonth() == now.getMonth() && a.getWorkDate().getYear() == now.getYear()) {
                    if ("Hợp lệ".equals(a.getStatus())) {
                        attendDays++;
                    } else if (a.getStatus() != null && (a.getStatus().contains("Đi muộn") || a.getStatus().contains("Về sớm"))) {
                        attendDays++;
                        penalties += 100000;
                    }
                }
            }
            
            List<com.example.human_resource_management.entity.LeaveRequest> leaves = leaveRequestRepository.findByEmployeeIdOrderByCreatedAtDesc(e.getId());
            long leaveDays = 0;
            for (com.example.human_resource_management.entity.LeaveRequest req : leaves) {
                if ("Đã duyệt".equals(req.getStatus()) && req.getStartDate() != null && req.getEndDate() != null && req.getStartDate().getMonth() == now.getMonth() && req.getStartDate().getYear() == now.getYear()) {
                    leaveDays += java.time.temporal.ChronoUnit.DAYS.between(req.getStartDate(), req.getEndDate()) + 1;
                }
            }
            
            long workingDays = attendDays + leaveDays;
            long total = (baseSalary / 22) * workingDays - penalties;
            if (total < 0) total = 0;
            
            com.example.human_resource_management.entity.Payroll p = payrollRepository.findByEmployeeIdAndMonthYear(e.getId(), monthYear).orElse(new com.example.human_resource_management.entity.Payroll());
            p.setEmployee(e);
            p.setMonthYear(monthYear);
            p.setBaseSalary(baseSalary);
            p.setWorkingDays((int) workingDays);
            p.setBonus(-penalties);
            p.setTotalSalary(total);
            p.setStatus("Đã thanh toán");
            payrollRepository.save(p);
        }
        
        return "redirect:/reports";
    }


    private Map<String, Object> metric(String l, String v, String n, String t) { return row("label", l, "value", v, "note", n, "tone", t); }
    private Map<String, Object> row(Object... values) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i + 1 < values.length; i += 2) map.put(String.valueOf(values[i]), values[i + 1]);
        return map;
    }
}

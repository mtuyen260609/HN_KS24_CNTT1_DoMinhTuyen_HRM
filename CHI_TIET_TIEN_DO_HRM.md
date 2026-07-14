# CHI TIẾT TIẾN ĐỘ THỰC HIỆN DỰ ÁN HRM

| Mã Task | Phân hệ | Hạng mục | Tên công việc | Mô tả chi tiết | Bắt đầu | Kết thúc | Ưu tiên | Trạng thái | Đầu ra (Deliverable) | Ghi chú |
| :--- | :--- | :--- | :--- | :--- | :---: | :---: | :---: | :---: | :--- | :--- |
| HRM-TASK-001 | Planning | Project | Chốt đề tài và phạm vi | Chốt mục tiêu, 2 actor (Employee, HR), chức năng MVP. | 2026-07-07 | 2026-07-07 | High | Done | Scope MVP + 2 actors | Khớp SRS_HRM.md. |
| HRM-TASK-002 | Planning | Project | Tạo cấu trúc tài liệu | Tạo repo, đặt tên file, thống nhất định dạng Markdown. | 2026-07-07 | 2026-07-07 | Medium | Done | Folder doc .md | Đã có SRS_HRM.md, KE_HOACH... |
| HRM-TASK-003 | SRS | Requirement | Viết problem statement | Mô tả bài toán chấm công 4.0 và lý do cần hệ thống. | 2026-07-07 | 2026-07-07 | High | Done | Problem statement | Đã thể hiện trong SRS_HRM.md. |
| HRM-TASK-004 | SRS | Requirement | Xác định phân quyền | Phân rạch ròi quyền cho 2 Actor: HR và Employee. | 2026-07-07 | 2026-07-07 | High | Done | Actors & Permissions | HR nắm full quyền, Employee chỉ xem. |
| HRM-TASK-005 | SRS | Requirement | Liệt kê chức năng chính | Auth, Dashboard, Profile, Attendance, Leave, Payroll. | 2026-07-07 | 2026-07-07 | High | Done | Module list | Chốt 10 Use Case chuẩn trong SRS. |
| HRM-TASK-006 | SRS | SRS | Viết phần giới thiệu SRS | Mục đích, phạm vi, tổng quan ứng dụng nội bộ SPA. | 2026-07-08 | 2026-07-08 | High | Done | SRS intro | Đã có trong SRS_HRM.md phần 1. |
| HRM-TASK-007 | SRS | SRS | Viết yêu cầu tổng thể | Sơ đồ ERD, flow, sitemap, phân quyền 2 role. | 2026-07-08 | 2026-07-08 | High | Done | Overall requirements | Đã cập nhật 100% chuẩn yêu cầu. |
| HRM-TASK-008 | Design | Use Case | Vẽ Use Case tổng quát | Tạo biểu đồ use case cho HR và Employee. | 2026-07-08 | 2026-07-08 | High | Done | Use case overview | Đã đính kèm link sơ đồ. |
| HRM-TASK-009 | SRS | Use Case | Đặc tả UC-01, UC-02 | Đăng nhập hệ thống và xem dashboard theo role. | 2026-07-08 | 2026-07-08 | High | Done | UC-01, UC-02 | Auth đã mô tả chi tiết luồng. |
| HRM-TASK-010 | SRS | Use Case | Đặc tả UC-04, UC-08 | Xem hồ sơ cá nhân và Quản lý tối thiểu. | 2026-07-08 | 2026-07-08 | High | Done | Profile & Management | Chỉ HR được quản lý danh sách. |
| HRM-TASK-011 | SRS | Use Case | Đặc tả UC-09 | Cấu hình giờ làm việc, giờ ca, giờ nghỉ trưa. | 2026-07-09 | 2026-07-09 | Medium | Done | Settings | HR thiết lập cấu hình. |
| HRM-TASK-012 | SRS | Use Case | Đặc tả UC-05 | Chấm công 4.0 (Tích hợp Camera/GPS check-in/out). | 2026-07-09 | 2026-07-09 | High | Done | UC-05 | Attendance flow đã có. |
| HRM-TASK-013 | SRS | Use Case | Đặc tả UC-06, UC-07 | Tạo đơn xin nghỉ phép (Employee) và Duyệt đơn (HR). | 2026-07-09 | 2026-07-09 | High | Done | UC-06, UC-07 | Leave Request flow hoàn chỉnh. |
| HRM-TASK-014 | SRS | Use Case | Đặc tả UC-10 | Bảng lương (hiển thị trạng thái scaffold). | 2026-07-09 | 2026-07-09 | Medium | Done | UC-10 | Payroll pending logic thực tế. |
| HRM-TASK-015 | Design | Database | Thiết kế ERD (No Enum) | Xác định Entity: User, Role, Employee, Attendance... | 2026-07-09 | 2026-07-09 | High | Done | ERD/database draft | Đã loại bỏ hoàn toàn việc dùng Enum. |
| HRM-TASK-016 | Design | UI/UX | Thiết kế wireframe | Dashboard, Attendance panel, Leave panel. | 2026-07-10 | 2026-07-10 | High | Done | Wireframe/layout draft | UI dùng CSS thuần/Minimalist. |
| HRM-TASK-017 | Frontend | Setup | Khởi tạo Frontend SPA | Chuẩn bị tích hợp API Backend Spring Boot. | 2026-07-10 | 2026-07-10 | High | Todo | frontend/ | Làm SPA theo định hướng SRS. |
| HRM-TASK-018 | Frontend | Auth | Làm UI Login | Giao diện login (email, pass), bắt lỗi role. | 2026-07-10 | 2026-07-10 | High | Todo | LoginPage, authService | Login gọi API lấy Token. |
| HRM-TASK-019 | Frontend | Layout | Làm layout chung | Sidebar phân quyền rành mạch HR và Employee. | 2026-07-10 | 2026-07-10 | High | Todo | MainLayout | Layout Header/Sidebar. |
| HRM-TASK-020 | Frontend | Dashboard | Làm UI Dashboard | Hiển thị số liệu, cảnh báo (Đi muộn, LATE). | 2026-07-10 | 2026-07-10 | High | Todo | DashboardPage | UI dashboard theo role. |
| HRM-TASK-021 | Frontend | Management | Trang Quản lý | Danh sách Nhân viên & Phòng ban (Chỉ cho HR). | 2026-07-13 | 2026-07-13 | Medium | Todo | ManagementPage | Dành cho HR. |
| HRM-TASK-022 | Frontend | Profile | Làm trang Hồ sơ | Hiển thị thông tin cá nhân của Employee. | 2026-07-13 | 2026-07-13 | High | Todo | ProfilePage | View only. |
| HRM-TASK-023 | Frontend | Settings | Làm trang Cấu hình | Form nhập giờ vào ca, giờ ra ca, nghỉ trưa. | 2026-07-13 | 2026-07-13 | Medium | Todo | SettingsForm | Chỉ HR thao tác. |
| HRM-TASK-024 | Frontend | Attendance | Làm trang Chấm công | Giao diện Camera chụp selfie và lấy tọa độ GPS. | 2026-07-13 | 2026-07-13 | High | Todo | AttendancePage | Nút Check-in, Check-out. |
| HRM-TASK-025 | Frontend | Leave | Làm trang Nghỉ phép | Form tạo đơn, hiển thị số phép dư. | 2026-07-14 | 2026-07-14 | High | Todo | LeaveRequestPage | Dành cho Employee. |
| HRM-TASK-026 | Frontend | Approval | Trang Duyệt phép | Danh sách chờ duyệt, Nút Approve/Reject. | 2026-07-14 | 2026-07-14 | High | Todo | ApprovalPage | Dành cho HR. |
| HRM-TASK-027 | Frontend | Payroll | Làm trang Bảng lương | Giao diện hiển thị trạng thái lương (MVP). | 2026-07-14 | 2026-07-14 | Medium | Todo | PayrollPage | Scaffold. |
| HRM-TASK-028 | Backend | Setup | Khởi tạo Spring Boot | Maven, MySQL, JPA, Spring Security (JWT). | 2026-07-14 | 2026-07-14 | High | Done | backend/ setup | Đã tạo project cơ sở thành công. |
| HRM-TASK-029 | Backend | Entity | Code Entity (No Enum) | Cài đặt các class User, Role, Employee, Department... | 2026-07-14 | 2026-07-14 | High | Done | Entities | Đổi Enum thành Entity Map chuẩn. |
| HRM-TASK-030 | Backend | Auth | Code Security Filter | Setup CustomUserDetails, JwtAuthFilter. | 2026-07-15 | 2026-07-15 | High | Done | SecurityConfig | Phân quyền API. |
| HRM-TASK-031 | Backend | Auth API | Xây dựng AuthController | API đăng nhập trả về JWT Token. | 2026-07-15 | 2026-07-15 | High | In Progress | AuthController | Có xử lý xác thực. |
| HRM-TASK-032 | Backend | Management | Cấu trúc Employee/Dept| Khởi tạo các API cơ bản để liên kết DB. | 2026-07-15 | 2026-07-15 | Medium | In Progress | Repositories | Setup quan hệ DB. |
| HRM-TASK-033 | Backend | Settings | Xây dựng API Cấu hình | Lưu tham số HH:mm cho giờ làm việc. | 2026-07-15 | 2026-07-15 | Medium | Todo | Settings logic | Validate format giờ. |
| HRM-TASK-034 | Backend | Attendance | Logic Check-in/out | Xét trạng thái LATE, ON_TIME (> 15phút). | 2026-07-16 | 2026-07-16 | High | Todo | Attendance Service | Chờ hoàn thiện API. |
| HRM-TASK-035 | Backend | Leave API | Logic Đơn nghỉ phép | Đánh dấu PENDING, ghi nhận loại phép. | 2026-07-16 | 2026-07-16 | High | Todo | Leave Controller | Xử lý usedLeave. |
| HRM-TASK-036 | Backend | Approval | Logic Duyệt/Từ chối | Cập nhật APPROVED/REJECTED. | 2026-07-16 | 2026-07-16 | High | Todo | HR Approval | Phân quyền HR strict. |
| HRM-TASK-037 | Backend | Payroll API | Cấu trúc Payroll | API rỗng scaffold cho Bảng Lương. | 2026-07-16 | 2026-07-16 | Low | Todo | Payroll Controller | Trả data mẫu. |
| HRM-TASK-038 | Integration | CORS | Cấu hình CORS | Thiết lập WebMvcConfigurer mở port. | 2026-07-17 | 2026-07-17 | High | Todo | WebConfig | Mở cho frontend SPA gọi. |
| HRM-TASK-039 | Integration | Flow | Test API Backend | Chạy full luồng Login -> Chấm công -> Nghỉ phép. | 2026-07-17 | 2026-07-17 | High | Todo | Postman Collection | Ensure luồng trơn tru. |
| HRM-TASK-040 | Testing | Logic | Unit Test | Test rule "Đi muộn 15p -> LATE". | 2026-07-17 | 2026-07-17 | Medium | Todo | JUnit tests | Đảm bảo tính toán đúng. |
| HRM-TASK-041 | Documentation | Timeline | Cập nhật KE_HOACH | Đồng bộ file kế hoạch 07/07 - 20/07. | 2026-07-20 | 2026-07-20 | High | Done | KE_HOACH_DU_AN_HRM.md | File timeline đồng bộ. |
| HRM-TASK-042 | Documentation | Schedule | Cập nhật CHI_TIET | Rà soát bảng task 44 mục. | 2026-07-20 | 2026-07-20 | High | Done | CHI_TIET_TIEN_DO_HRM.md | Chính là file này. |
| HRM-TASK-043 | Documentation | SRS | Hoàn thiện SRS | Kiểm tra loại bỏ từ khóa Enum, scope 2 Roles. | 2026-07-20 | 2026-07-20 | High | Done | SRS_HRM.md | Đã 100% khớp hệ thống. |
| HRM-TASK-044 | Release | Codebase | Dọn dẹp Code | Refactor gọn mã nguồn Spring Boot. | 2026-07-20 | 2026-07-20 | High | Todo | Repository sạch | Code sạch sẵn sàng demo. |

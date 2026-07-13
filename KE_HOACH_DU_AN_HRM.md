# KẾ HOẠCH PHÁT TRIỂN DỰ ÁN HRM

**Công ty:** Bộ môn CNPM  
**Dự án:** DỰ ÁN HRM (Human Resource Management)  
**Khách hàng:** CÔNG TY YYY  
**Từ ngày:** 19/08/2024  
**Đến ngày:** 19/12/2024  

---

### BẢNG CHÚ THÍCH (GLOSSARY)

| Thuật ngữ | Diễn giải |
| :--- | :--- |
| **RBAC** | Role-Based Access Control - Phân quyền dựa trên vai trò (Employee, Manager, HR) |
| **Camera/GPS Auth** | Xác thực chấm công qua ảnh selfie và tọa độ vị trí địa lý |
| **Attendance Status** | Trạng thái: ON_TIME (Đúng giờ), LATE (Muộn), EARLY_LEAVE (Về sớm), ABSENT (Vắng) |
| **Leave Status** | Trạng thái đơn: PENDING (Chờ duyệt), APPROVED (Đã duyệt), REJECTED (Từ chối) |
| **MVP** | Minimum Viable Product - Sản phẩm tối thiểu đáp ứng nghiệp vụ lõi |

---

### KẾ HOẠCH CHI TIẾT

| STT | Nội dung công việc | Ghi chú | Thời gian (ngày) | Effort (manday) | Comment |
| :--- | :--- | :--- | :---: | :---: | :--- |
| **I** | **PHÂN TÍCH THIẾT KẾ** | | **25** | **25** | |
| 1 | Lập kế hoạch dự án | Lập kế hoạch chi tiết, phân bổ 10 buổi | 3 | 3 | |
| 2 | Phân tích yêu cầu nghiệp vụ | Đặc tả SRS: Luồng chấm công Camera/GPS, Quy trình nghỉ phép | 7 | 7 | |
| 3 | Thiết kế kiến trúc hệ thống | Backend Spring Boot, Frontend React, JWT Security | 5 | 5 | |
| 4 | Thiết kế CSDL (Master DB) | Sơ đồ ERD (Employee, Attendance, Leave, Dept, Auth) | 5 | 5 | |
| 5 | Thiết kế UI/UX (Web/App) | Wireframe 20 màn hình: Dashboard, Profile, Attendance Form... | 5 | 5 | |
| **II** | **PHÁT TRIỂN VÀ XÂY DỰNG** | | **60** | **120** | |
| **1** | **Quản trị hệ thống & Xác thực** | | **15** | **30** | |
| 1,1 | Đăng nhập hệ thống | | 5 | 10 | |
| | + Đăng nhập/Đăng xuất (JWT) | Phân quyền 3 vai trò: Employee, Manager, HR | | | |
| | + Refresh Token | Đảm bảo phiên đăng nhập liên tục | | | |
| 1,2 | Quản lý tài khoản & Hồ sơ | | 4 | 8 | |
| | + Xem/Cập nhật thông tin cá nhân | Thông tin liên hệ, phòng ban | | | |
| | + Đổi mật khẩu | Bảo mật tài khoản | | | |
| 1,3 | Dashboard tổng quan | | 3 | 6 | |
| | + Dashboard Employee | Cá nhân: Trạng thái công hôm nay, Quỹ phép | | | |
| | + Dashboard Manager/HR | Tổng số nhân sự, đơn chờ duyệt, cảnh báo | | | |
| 1,4 | Quản lý danh mục nền | | 3 | 6 | |
| | + Quản lý Phòng ban | CRUD, ràng buộc nhân viên | | | |
| | + Quản lý Nhân viên | CRUD, gán vai trò, cấp tài khoản | | | |
| **2** | **Nghiệp vụ Chấm công & Nghỉ phép** | | **35** | **70** | |
| 2,1 | Chức năng Chấm công 4.0 | | 15 | 30 | |
| | + Check-in/Check-out (Camera) | Tích hợp chụp selfie xác thực khuôn mặt | | | |
| | + Xác thực vị trí (GPS) | Kiểm tra tọa độ trong vùng cho phép | | | |
| | + Phân loại trạng thái công | Tự động tính LATE, EARLY_LEAVE | | | |
| 2,2 | Quản lý Lịch sử & Cảnh báo | | 5 | 10 | |
| | + Lịch sử chấm công cá nhân | Xem bảng công 7 ngày/30 ngày | | | |
| | + Cảnh báo quên Check-out | Thông báo nhắc nhở cuối ca | | | |
| 2,3 | Quản lý Nghỉ phép | | 8 | 16 | |
| | + Tạo đơn nghỉ phép | Kiểm tra quỹ phép thực tế | | | |
| | + Luồng Duyệt/Từ chối đơn | Manager phê duyệt đơn của cấp dưới | | | |
| 2,4 | Cấu hình & Bảng lương | | 7 | 14 | |
| | + Cấu hình giờ làm việc | Setup ca làm việc cho hệ thống | | | |
| | + Phân hệ lương (Scaffold) | Hiển thị bảng lương MVP | | | |
| **3** | **Tích hợp & Kiểm thử mở rộng** | | **10** | **20** | |
| 3,1 | Tích hợp Thông báo | Email báo đơn mới, Onesignal Push notification | 3 | 6 | |
| 3,2 | Tích hợp Google Analytics | Theo dõi hành vi người dùng | 2 | 4 | |
| 3,3 | Đa ngôn ngữ (Vi/En) | Chuyển đổi ngôn ngữ giao diện | 3 | 6 | |
| 3,4 | Kiểm thử & Fix bug | Test luồng Camera/GPS, Validation dữ liệu | 2 | 4 | |
| | **TỔNG CỘNG** | | **85** | **145** | |

---

### CHI PHÍ DỰ ÁN (PROJECT COSTING)

Dưới đây là bảng tính toán chi phí dự án, dựa trên việc dự án thực tế **chỉ có 1 người thực hiện (Solo Developer)**. Mặc dù khối lượng quy đổi theo tiêu chuẩn (dành cho cấp quản lý/khách hàng) hiển thị là 145 mandays, nhưng chi phí nhân sự thực trả sẽ được tính trên cơ sở thù lao khoán việc cho cá nhân.

#### 1. Chi phí Hạ tầng & Dịch vụ Bên Thứ Ba (Infrastructure)
Tối ưu bằng cách sử dụng các dịch vụ cloud nhỏ gọn.

| Loại chi phí / Dịch vụ | Đơn giá (VND) | Thành tiền (VND) | Ghi chú |
| :--- | :---: | :---: | :--- |
| Máy chủ Cloud VPS (Gói 3 tháng) | 300.000 | 900.000 | Phục vụ demo/chạy thử |
| Các API mở rộng & Domain | 500.000 | 500.000 | Dịch vụ cơ bản |
| **Tổng chi phí hạ tầng** | | **1.400.000** | |

#### 2. Dự phòng rủi ro & Chi phí khác
Dành cho các khoản phát sinh nhỏ lẻ (công cụ AI, xăng xe...).
- **Thành tiền:** **1.100.000 VND**

#### 3. Tổng hợp Chi phí Dự án (Grand Total)

| Hạng mục chi phí | Số tiền (VND) | Tỷ trọng |
| :--- | :---: | :---: |
| 1. Chi phí nhân sự (1 Lập trình viên) | 22.500.000 | 90% |
| 2. Chi phí hạ tầng & Dịch vụ | 1.400.000 | 5,6% |
| 3. Dự phòng rủi ro | 1.100.000 | 4,4% |
| **TỔNG CỘNG CHI PHÍ DỰ ÁN** | **25.000.000** | **100%** |

*(Bằng chữ: Hai mươi lăm triệu đồng chẵn).*

---

#### 4. Kế hoạch Thanh toán Đề xuất
- **Đợt 1: Tạm ứng (30%)** - 7.500.000 VND.
- **Đợt 2: Nghiệm thu Giai đoạn Phát triển (40%)** - 10.000.000 VND.
- **Đợt 3: Bàn giao toàn bộ (30%)** - 7.500.000 VND.

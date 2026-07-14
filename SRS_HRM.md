# PHẦN 1: GIỚI THIỆU

## 1.1 Mục đích tài liệu
Tài liệu này mô tả nghiệp vụ và yêu cầu của hệ thống HRM để làm cơ sở cho thiết kế, triển khai và kiểm thử. Tài liệu tập trung vào các luồng cốt lõi gồm đăng nhập, hồ sơ cá nhân, chấm công, nghỉ phép, duyệt phép, quản lý tối thiểu và cấu hình hệ thống.

## 1.2 Phạm vi tài liệu
Hệ thống HRM trong phạm vi hiện tại phục vụ 2 vai trò chính:
- Employee
- HR

Các chức năng được hỗ trợ:
- đăng nhập, đăng xuất;
- xem dashboard;
- xem hồ sơ cá nhân;
- check-in, check-out;
- tạo và duyệt đơn nghỉ phép;
- xem bảng lương ở trạng thái scaffold;
- quản lý nhân viên/phòng ban tối thiểu;
- cập nhật cấu hình giờ làm việc.

## 1.3 Tổng quan ứng dụng
Ứng dụng là một web app nội bộ:
- frontend SPA hiển thị giao diện và xử lý tương tác;
- backend Spring Boot cung cấp API trạng thái và xác thực;
- dữ liệu demo được lưu cục bộ để phục vụ mô phỏng nghiệp vụ.

## 1.4 Thuật ngữ viết tắt

| STT | Từ viết tắt | Diễn giải |
| :--- | :--- | :--- |
| 1 | HRM | Human Resource Management |
| 2 | RBAC | Role-Based Access Control |
| 3 | JWT | JSON Web Token |
| 4 | SPA | Single Page Application |
| 5 | MVP | Minimum Viable Product |

---

# PHẦN 2: YÊU CẦU TỔNG THỂ

## 2.1 Sơ đồ quan hệ đối tượng
Sơ đồ ERD của hệ thống:

`assets/diagrams/erd.svg`

Các đối tượng chính:
- Employee
- Department
- Attendance
- LeaveRequest
- Role
- User

## 2.2 Sơ đồ Use Case
Sơ đồ Use Case của hệ thống:

`assets/diagrams/use-case.svg`

## 2.3 Sơ đồ luồng
Sơ đồ luồng nghiệp vụ tổng quát:

`assets/diagrams/business-flow.svg`

## 2.4 Sơ đồ chuyển trạng thái
Các luồng có chuyển trạng thái chính:
- trạng thái chấm công: ON_TIME, LATE, EARLY_LEAVE, ABSENT;
- trạng thái đơn nghỉ: PENDING, APPROVED, REJECTED;
- trạng thái phiên đăng nhập: active / inactive.

## 2.5 Phân quyền

### 2.5.1 Phân quyền chức năng
| Chức năng | Employee | HR |
| :--- | :---: | :---: |
| Đăng nhập / đăng xuất | Có | Có |
| Dashboard | Có | Có |
| Hồ sơ cá nhân | Có | Có |
| Chấm công | Có | Có |
| Nghỉ phép | Có | Có |
| Duyệt phép | Không | Có |
| Bảng lương | Có | Có |
| Quản lý tối thiểu | Không | Có |
| Cấu hình | Không | Có |

### 2.5.2 Phân quyền dữ liệu
- Employee chỉ xem dữ liệu của chính mình.
- HR xem và cập nhật dữ liệu toàn bộ nhân sự, phòng ban, đơn từ nghỉ phép và cấu hình hệ thống.

## 2.6 Site Map
- Login
- Dashboard
- Hồ sơ cá nhân
- Chấm công
- Nghỉ phép
- Bảng lương
- Duyệt phép (Chỉ HR)
- Quản lý tối thiểu (Chỉ HR)
- Cấu hình (Chỉ HR)

---

# PHẦN 3: CHỨC NĂNG

## Danh sách chức năng

| ID | Tên chức năng | Phân hệ | Tác nhân |
| :--- | :--- | :--- | :--- |
| 1 | Đăng nhập | Authentication | Employee, HR |
| 2 | Đăng xuất | Authentication | Employee, HR |
| 3 | Xem dashboard | Dashboard | Employee, HR |
| 4 | Xem hồ sơ cá nhân | Profile | Employee, HR |
| 5 | Chấm công | Attendance | Employee, HR |
| 6 | Nghỉ phép | Leave | Employee, HR |
| 7 | Duyệt nghỉ phép | Approvals | HR |
| 8 | Bảng lương | Payroll | Employee, HR |
| 9 | Quản lý tối thiểu | Management | HR |
| 10 | Cấu hình hệ thống | Settings | HR |

## 3.1 Đăng nhập

### 3.1.1 Mục tiêu chức năng
Người dùng nhập email, mật khẩu và vai trò để xác thực và vào hệ thống.

| Trường | Giá trị |
| :--- | :--- |
| Use Case ID | UC-01 |
| Tác nhân (Actor) | Employee, HR |
| Sự ưu tiên (Priority) | Cao |
| Trigger | Người dùng mở màn hình đăng nhập. |
| Tiền điều kiện (Pre-Condition) | Tài khoản tồn tại trong dữ liệu hệ thống. |
| Hậu điều kiện (Post-Condition) | Token phiên được tạo và lưu lại. |
| Luồng cơ bản (Basic Flow) | Người dùng nhập thông tin -> hệ thống kiểm tra -> trả về token -> hiển thị dashboard. |
| Luồng thay thế (Alternative Flow) | Sai thông tin hoặc vai trò không đúng -> báo lỗi đăng nhập. |
| Business Rule | Mỗi tài khoản chỉ đăng nhập với đúng vai trò đã khai báo. |
| Non-Functional Requirement | Phản hồi lỗi phải rõ ràng, ngắn gọn. |

### 3.1.2 Sơ đồ luồng chi tiết
Sơ đồ chi tiết:

`assets/diagrams/login-sequence.svg`

### 3.1.3 Giao diện
- Màn hình có trường email, mật khẩu.
- Nút hành động chính: Đăng nhập.

## 3.2 Chấm công

### 3.2.1 Mục tiêu chức năng
Người dùng ghi nhận giờ vào và giờ ra trong ngày làm việc.

| Trường | Giá trị |
| :--- | :--- |
| Use Case ID | UC-05 |
| Tác nhân (Actor) | Employee, HR |
| Sự ưu tiên (Priority) | Cao |
| Trigger | Người dùng mở màn hình chấm công. |
| Tiền điều kiện (Pre-Condition) | Người dùng đã đăng nhập. |
| Hậu điều kiện (Post-Condition) | Bản ghi chấm công được cập nhật. |
| Luồng cơ bản (Basic Flow) | Check-in -> hệ thống ghi giờ vào -> Check-out -> hệ thống ghi giờ ra. |
| Luồng thay thế (Alternative Flow) | Check-in trễ hoặc quên check-out -> hệ thống gắn cảnh báo. |
| Business Rule | Nếu check-in trễ hơn 15 phút so với giờ vào ca thì trạng thái là LATE. |
| Non-Functional Requirement | Lưu dữ liệu phải nhất quán giữa frontend và backend. |

### 3.2.2 Sơ đồ luồng chi tiết
Sơ đồ nghiệp vụ liên quan:

`assets/diagrams/business-flow.svg`

### 3.2.3 Giao diện
- Nút Check-in.
- Nút Check-out.
- Danh sách lịch sử 7 ngày gần nhất.

## 3.3 Nghỉ phép

### 3.3.1 Mục tiêu chức năng
Người dùng tạo đơn xin nghỉ và theo dõi trạng thái phê duyệt.

| Trường | Giá trị |
| :--- | :--- |
| Use Case ID | UC-06 |
| Tác nhân (Actor) | Employee, HR |
| Sự ưu tiên (Priority) | Cao |
| Trigger | Người dùng gửi đơn nghỉ phép. |
| Tiền điều kiện (Pre-Condition) | Người dùng đã đăng nhập. |
| Hậu điều kiện (Post-Condition) | Đơn nghỉ được lưu với trạng thái PENDING hoặc được duyệt/từ chối. |
| Luồng cơ bản (Basic Flow) | Nhập loại phép, từ ngày, đến ngày, lý do -> gửi đơn -> chờ duyệt. |
| Luồng thay thế (Alternative Flow) | Dữ liệu không hợp lệ -> hiển thị lỗi. |
| Business Rule | Khi đơn được duyệt, số ngày phép đã dùng phải được cộng vào `usedLeave`. |
| Non-Functional Requirement | Phải hiển thị rõ số ngày phép còn lại. |

### 3.3.2 Sơ đồ luồng chi tiết
Sơ đồ:

`assets/diagrams/leave-activity.svg`

### 3.3.3 Giao diện
- Form tạo đơn nghỉ.
- Danh sách đơn đã gửi.
- Khu vực hiển thị quỹ phép còn lại.

## 3.4 Duyệt nghỉ phép

### 3.4.1 Mục tiêu chức năng
HR xử lý đơn chờ duyệt.

| Trường | Giá trị |
| :--- | :--- |
| Use Case ID | UC-07 |
| Tác nhân (Actor) | HR |
| Sự ưu tiên (Priority) | Cao |
| Trigger | Người dùng mở danh sách chờ duyệt. |
| Tiền điều kiện (Pre-Condition) | Người dùng có quyền HR. |
| Hậu điều kiện (Post-Condition) | Đơn chuyển sang APPROVED hoặc REJECTED. |
| Luồng cơ bản (Basic Flow) | Chọn đơn -> duyệt/từ chối -> hệ thống cập nhật trạng thái. |
| Luồng thay thế (Alternative Flow) | Không có quyền -> không hiển thị màn hình. |
| Business Rule | Chỉ HR được duyệt đơn. |
| Non-Functional Requirement | Kết quả xử lý phải phản hồi tức thời trên UI. |

### 3.4.2 Sơ đồ luồng chi tiết
Luồng được nằm trong sơ đồ nghiệp vụ và luồng nghỉ phép.

### 3.4.3 Giao diện
- Danh sách đơn chờ duyệt.
- Nút Duyệt.
- Nút Từ chối.

## 3.5 Quản lý tối thiểu

### 3.5.1 Mục tiêu chức năng
HR quản lý danh mục nhân viên và phòng ban ở mức cơ bản.

| Trường | Giá trị |
| :--- | :--- |
| Use Case ID | UC-08 |
| Tác nhân (Actor) | HR |
| Sự ưu tiên (Priority) | Trung bình |
| Trigger | HR mở màn hình quản lý. |
| Tiền điều kiện (Pre-Condition) | HR đã đăng nhập. |
| Hậu điều kiện (Post-Condition) | Dữ liệu nhân viên/phòng ban được cập nhật. |
| Luồng cơ bản (Basic Flow) | Thêm/sửa dữ liệu -> lưu -> cập nhật danh sách. |
| Luồng thay thế (Alternative Flow) | Dữ liệu không hợp lệ -> báo lỗi. |
| Business Rule | Không xóa phòng ban nếu còn nhân viên thuộc phòng ban đó. |
| Non-Functional Requirement | Thao tác CRUD phải rõ ràng, tối thiểu số bước nhập liệu. |

### 3.5.2 Sơ đồ luồng chi tiết
Gắn với luồng quản trị nội bộ trong sơ đồ nghiệp vụ.

### 3.5.3 Giao diện
- Danh sách nhân viên.
- Danh sách phòng ban.
- Form thêm/sửa.

## 3.6 Cấu hình hệ thống

### 3.6.1 Mục tiêu chức năng
HR điều chỉnh giờ làm việc của hệ thống.

| Trường | Giá trị |
| :--- | :--- |
| Use Case ID | UC-09 |
| Tác nhân (Actor) | HR |
| Sự ưu tiên (Priority) | Trung bình |
| Trigger | HR mở màn hình cấu hình. |
| Tiền điều kiện (Pre-Condition) | HR có quyền truy cập. |
| Hậu điều kiện (Post-Condition) | Các tham số thời gian được lưu. |
| Luồng cơ bản (Basic Flow) | Sửa thời gian -> lưu cấu hình -> cập nhật hệ thống. |
| Luồng thay thế (Alternative Flow) | Sai định dạng giờ -> báo lỗi. |
| Business Rule | Chỉ HR mới được sửa cấu hình. |
| Non-Functional Requirement | Định dạng thời gian phải là `HH:mm`. |

### 3.6.2 Sơ đồ luồng chi tiết
Không có sơ đồ riêng, dùng chung với luồng quản trị hệ thống.

### 3.6.3 Giao diện
- Giờ vào ca.
- Giờ ra ca.
- Nghỉ trưa bắt đầu.
- Nghỉ trưa kết thúc.

## 3.7 Bảng lương

### 3.7.1 Mục tiêu chức năng
Hiển thị trạng thái phân hệ lương ở mức scaffold.

| Trường | Giá trị |
| :--- | :--- |
| Use Case ID | UC-10 |
| Tác nhân (Actor) | Employee, HR |
| Sự ưu tiên (Priority) | Trung bình |
| Trigger | Người dùng mở tab Bảng lương. |
| Tiền điều kiện (Pre-Condition) | Người dùng đã đăng nhập. |
| Hậu điều kiện (Post-Condition) | Màn hình trạng thái lương được hiển thị. |
| Luồng cơ bản (Basic Flow) | Mở màn hình -> xem trạng thái scaffold. |
| Luồng thay thế (Alternative Flow) | Chưa có dữ liệu lương thực tế. |
| Business Rule | Phân hệ lương hiện tại chưa tính lương thật. |
| Non-Functional Requirement | Phải nói rõ đây là chức năng đang phát triển. |

### 3.7.2 Sơ đồ luồng chi tiết
Không có.

### 3.7.3 Giao diện
- Danh sách trạng thái.
- Nhãn `MVP pending`.

---

# PHẦN 4: CÁC COMPONENT, THÔNG BÁO, CẢNH BÁO

## 4.1 Component

| Component | Mô tả |
| :--- | :--- |
| Header | Hiển thị tên hệ thống, người dùng và vai trò. |
| Sidebar / Navigation | Chuyển giữa các màn hình. |
| Dashboard cards | Hiển thị số liệu tổng quan. |
| Attendance panel | Chấm công và lịch sử. |
| Leave panel | Tạo và theo dõi đơn nghỉ. |
| Approval panel | Duyệt hoặc từ chối đơn. |
| Settings form | Cập nhật giờ làm việc. |

## 4.2 Thông báo

| Tình huống | Thông báo |
| :--- | :--- |
| Đăng nhập thành công | Hiển thị dashboard theo vai trò. |
| Sai thông tin đăng nhập | Báo lỗi xác thực. |
| Gửi đơn nghỉ thành công | Đơn được chuyển sang trạng thái chờ duyệt. |
| Duyệt đơn thành công | Cập nhật trạng thái đơn và quỹ phép. |
| Lưu cấu hình thành công | Hiển thị thông báo đã lưu. |

## 4.3 Cảnh báo

| Tình huống | Cảnh báo |
| :--- | :--- |
| Chưa check-out | Cảnh báo quên check-out trên dashboard. |
| Đi muộn | Gắn nhãn trạng thái LATE. |
| Sắp hết phép | Hiển thị pill cảnh báo quỹ phép thấp. |
| Không có quyền | Ẩn hoặc chặn màn hình tương ứng. |

---

# PHỤ LỤC

## A. Tài khoản demo
- `employee@demo.local / 123456`
- `hr@demo.local / 123456`

## B. Sơ đồ trong repo
- `assets/diagrams/business-flow.svg`
- `assets/diagrams/erd.svg`
- `assets/diagrams/leave-activity.svg`
- `assets/diagrams/login-sequence.svg`
- `assets/diagrams/use-case.svg`

## C. Ghi chú phạm vi
Tài liệu này ưu tiên đúng cấu trúc trình bày theo mẫu đính kèm. Phần nội dung đã được bám theo hệ thống HRM hiện có trong repo, đặc biệt là các luồng đăng nhập, chấm công, nghỉ phép, duyệt phép, quản lý tối thiểu và cấu hình.

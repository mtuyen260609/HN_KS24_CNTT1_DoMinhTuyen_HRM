# Đặc Tả API Specs - HRM System

Tài liệu này mô tả danh sách các API (RESTful) sẽ được phát triển cho hệ thống HRM.

## 1. Authentication (Xác thực & Phân quyền)
| Endpoint | Method | Description | Roles | Body/Query | Response |
| :--- | :---: | :--- | :---: | :--- | :--- |
| `/api/auth/login` | POST | Đăng nhập hệ thống | All | `{username, password}` | `{accessToken, refreshToken, user}` |
| `/api/auth/refresh` | POST | Cấp lại Access Token | All | `{refreshToken}` | `{accessToken, refreshToken}` |
| `/api/auth/logout` | POST | Đăng xuất | All | - | `200 OK` |
| `/api/auth/me` | GET | Lấy thông tin user hiện tại | All | - | `{UserDto}` |
| `/api/auth/password` | PUT | Đổi mật khẩu | All | `{oldPassword, newPassword}` | `200 OK` |

## 2. Quản lý Tài khoản & Hồ sơ
| Endpoint | Method | Description | Roles | Body/Query | Response |
| :--- | :---: | :--- | :---: | :--- | :--- |
| `/api/employees/me` | GET | Xem hồ sơ cá nhân | All | - | `{EmployeeDto}` |
| `/api/employees/me` | PUT | Cập nhật thông tin liên hệ | All | `{phone, address, avatarUrl}` | `{EmployeeDto}` |
| `/api/employees` | GET | Lấy danh sách nhân viên | HR | `?page, size, search, deptId` | `Page<EmployeeDto>` |
| `/api/employees` | POST | Thêm mới nhân viên | HR | `{EmployeeFormDto}` | `{EmployeeDto}` |
| `/api/employees/{id}` | GET | Xem chi tiết nhân viên | HR | - | `{EmployeeDto}` |
| `/api/employees/{id}` | PUT | Cập nhật nhân viên | HR | `{EmployeeFormDto}` | `{EmployeeDto}` |
| `/api/employees/{id}` | DELETE | Xóa/Vô hiệu hóa NV | HR | - | `200 OK` |

## 3. Quản lý Danh mục (Phòng ban)
| Endpoint | Method | Description | Roles | Body/Query | Response |
| :--- | :---: | :--- | :---: | :--- | :--- |
| `/api/departments` | GET | Lấy DS phòng ban | All | - | `List<DepartmentDto>` |
| `/api/departments` | POST | Tạo phòng ban mới | HR | `{name, description, managerId}` | `{DepartmentDto}` |
| `/api/departments/{id}` | PUT | Cập nhật phòng ban | HR | `{DepartmentForm}` | `{DepartmentDto}` |
| `/api/departments/{id}` | DELETE| Xóa phòng ban | HR | - | `200 OK` |

## 4. Chấm công 4.0 (Camera & GPS)
| Endpoint | Method | Description | Roles | Body/Query | Response |
| :--- | :---: | :--- | :---: | :--- | :--- |
| `/api/attendances/check-in` | POST | Check-in (Vào ca) | All | `{photo, latitude, longitude}` | `{AttendanceDto}` |
| `/api/attendances/check-out`| POST | Check-out (Tan ca) | All | `{photo, latitude, longitude}` | `{AttendanceDto}` |
| `/api/attendances/me` | GET | Xem lịch sử của mình | All | `?startDate, endDate` | `List<AttendanceDto>` |
| `/api/attendances` | GET | Xem lịch sử toàn c.ty | HR | `?employeeId, date` | `Page<AttendanceDto>` |

## 5. Quản lý Nghỉ phép
| Endpoint | Method | Description | Roles | Body/Query | Response |
| :--- | :---: | :--- | :---: | :--- | :--- |
| `/api/leaves` | POST | Tạo đơn xin nghỉ | All | `{startDate, endDate, reason}` | `{LeaveDto}` |
| `/api/leaves/me` | GET | Xem DS đơn của mình | All | - | `List<LeaveDto>` |
| `/api/leaves` | GET | Xem DS đơn chờ duyệt | HR | `?status, employeeId` | `Page<LeaveDto>` |
| `/api/leaves/{id}/approve` | PUT | Duyệt đơn nghỉ phép | HR | `{status: APPROVED/REJECTED, comment}`| `{LeaveDto}` |

## 6. Dashboard & Thống kê
| Endpoint | Method | Description | Roles | Body/Query | Response |
| :--- | :---: | :--- | :---: | :--- | :--- |
| `/api/dashboard/employee` | GET | Thống kê cá nhân | Employee | - | `{todayStatus, totalLeaves, ...}` |
| `/api/dashboard/manager` | GET | Thống kê tổng quan | HR | - | `{totalEmployees, pendingLeaves, ...}`|

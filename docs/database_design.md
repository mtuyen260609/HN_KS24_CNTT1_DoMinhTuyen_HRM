# Thiết Kế Cơ Sở Dữ Liệu (ERD) - HRM System

## Sơ đồ ERD (Entity Relationship Diagram)

```mermaid
erDiagram
    USERS ||--o| EMPLOYEES : "has"
    ROLES ||--o{ USERS : "assigned to"
    DEPARTMENTS ||--o{ EMPLOYEES : "belongs to"
    EMPLOYEES ||--o{ ATTENDANCES : "records"
    EMPLOYEES ||--o{ LEAVES : "requests"
    EMPLOYEES |o--o{ LEAVES : "approves"

    USERS {
        bigint id PK
        varchar username UK
        varchar password
        varchar email UK
        bigint role_id FK
        bigint employee_id FK "nullable"
        boolean is_active
        timestamp created_at
        timestamp updated_at
    }

    ROLES {
        bigint id PK
        varchar name UK "EMPLOYEE, HR"
        varchar description
    }

    EMPLOYEES {
        bigint id PK
        varchar full_name
        date date_of_birth
        varchar gender
        varchar phone
        varchar address
        bigint department_id FK
        varchar position
        varchar avatar_url
        timestamp created_at
        timestamp updated_at
    }

    DEPARTMENTS {
        bigint id PK
        varchar name UK
        varchar description
        timestamp created_at
        timestamp updated_at
    }

    ATTENDANCES {
        bigint id PK
        bigint employee_id FK
        date work_date
        timestamp check_in_time
        timestamp check_out_time
        varchar check_in_photo_url
        varchar check_out_photo_url
        varchar check_in_location
        varchar check_out_location
        varchar status "ON_TIME, LATE, EARLY_LEAVE, ABSENT"
        timestamp created_at
    }

    LEAVES {
        bigint id PK
        bigint employee_id FK
        date start_date
        date end_date
        varchar reason
        varchar status "PENDING, APPROVED, REJECTED"
        bigint approver_id FK "nullable"
        timestamp created_at
        timestamp updated_at
    }
```

## Mô tả chi tiết các bảng
- **USERS**: Lưu trữ thông tin tài khoản đăng nhập của người dùng. Mỗi user có thể liên kết với một nhân viên (`employee_id`).
- **ROLES**: Quản lý các vai trò trong hệ thống (RBAC) bao gồm EMPLOYEE, HR.
- **EMPLOYEES**: Lưu trữ thông tin chi tiết hồ sơ cá nhân của nhân viên.
- **DEPARTMENTS**: Quản lý phòng ban.
- **ATTENDANCES**: Lưu trữ dữ liệu lịch sử chấm công, bao gồm thời gian, hình ảnh selfie và tọa độ vị trí (GPS), cùng với trạng thái chấm công.
- **LEAVES**: Lưu trữ đơn xin nghỉ phép của nhân viên, quản lý quy trình duyệt đơn với `approver_id` là người duyệt.

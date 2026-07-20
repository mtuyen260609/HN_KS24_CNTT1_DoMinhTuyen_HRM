INSERT INTO system_settings (setting_key, setting_value) VALUES ('checkInTime', '08:00');
INSERT INTO system_settings (setting_key, setting_value) VALUES ('checkOutTime', '17:30');
INSERT INTO system_settings (setting_key, setting_value) VALUES ('gpsRadius', '200');
INSERT INTO system_settings (setting_key, setting_value) VALUES ('faceAuth', 'false');

INSERT INTO roles (id, name) VALUES (1, 'ROLE_EMPLOYEE');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_HR');
INSERT INTO roles (id, name) VALUES (3, 'ROLE_MANAGER');

INSERT INTO departments (id, name, description) VALUES (1, 'Phòng Kỹ thuật', 'Tech Dept');
INSERT INTO departments (id, name, description) VALUES (2, 'Phòng Nhân sự', 'HR Dept');

INSERT INTO employees (id, full_name, email, department_id, position, created_at, updated_at) 
VALUES (1, 'Nguyễn Văn Nhân Viên', 'employee@demo.local', 1, 'Developer', NOW(), NOW());

INSERT INTO employees (id, full_name, email, department_id, position, created_at, updated_at) 
VALUES (2, 'Nguyễn Thị Nhân Sự', 'hr@demo.local', 2, 'HR Manager', NOW(), NOW());

INSERT INTO users (id, username, password, email, role_id, employee_id, is_active, created_at, updated_at) 
VALUES (1, 'employee', '123456', 'employee@demo.local', 1, 1, 1, NOW(), NOW());

INSERT INTO users (id, username, password, email, role_id, employee_id, is_active, created_at, updated_at) 
VALUES (2, 'hr', '123456', 'hr@demo.local', 2, 2, 1, NOW(), NOW());

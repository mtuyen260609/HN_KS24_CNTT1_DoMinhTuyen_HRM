# KẾ HOẠCH PHÁT TRIỂN DỰ ÁN HRM

**Công ty:** Bộ môn CNPM  
**Dự án:** DỰ ÁN HRM (Human Resource Management)  
**Khách hàng:** CÔNG TY YYY  

---

### BẢNG CHÚ THÍCH (GLOSSARY)

| Thuật ngữ | Diễn giải |
| :--- | :--- |
| **Role** | Các vai trò trong hệ thống: Employee, HR |
| **Camera/GPS Auth** | Xác thực chấm công qua ảnh selfie và tọa độ vị trí địa lý |
| **Attendance Status** | Trạng thái: ON_TIME (Đúng giờ), LATE (Muộn), EARLY_LEAVE (Về sớm), ABSENT (Vắng) |
| **Leave Status** | Trạng thái đơn: PENDING (Chờ duyệt), APPROVED (Đã duyệt), REJECTED (Từ chối) |
| **MVP** | Minimum Viable Product - Sản phẩm tối thiểu đáp ứng nghiệp vụ lõi |

---

### TỔNG QUAN PHẠM VI DỰ ÁN

Dự án được triển khai chạy liên tục trong **chính xác 10 ngày làm việc thực tế** (loại trừ các ngày cuối tuần Thứ 7, Chủ Nhật):

*(Chi tiết tiến độ 44 công việc chia đều trong 10 ngày vui lòng xem file `CHI_TIET_TIEN_DO_HRM.md`)*

---

### CHI PHÍ DỰ ÁN (PROJECT COSTING)

Dưới đây là bảng tính toán chi phí dự án, dựa trên đội ngũ phát triển gồm **01 Lập trình viên (Developer)** và **01 Kiểm thử viên (QA/Tester) bán thời gian**. Số ngày triển khai thực tế là 10 ngày.

#### 1. Chi phí Hạ tầng & Dịch vụ Bên Thứ Ba (Infrastructure)

| Loại chi phí / Dịch vụ | Đơn giá (VND) | Thành tiền (VND) | Ghi chú |
| :--- | :---: | :---: | :--- |
| Máy chủ Cloud VPS (Gói 3 tháng) | 300.000 | 900.000 | Phục vụ demo/chạy thử |
| Các API mở rộng & Domain | 500.000 | 500.000 | Dịch vụ cơ bản |
| **Tổng chi phí hạ tầng** | | **1.400.000** | |

#### 2. Dự phòng rủi ro & Chi phí khác
- **Thành tiền:** **600.000 VND**

#### 3. Tổng hợp Chi phí Dự án (Grand Total)

| Hạng mục chi phí | Số tiền (VND) | Tỷ trọng |
| :--- | :---: | :---: |
| 1. Chi phí Lập trình (1 Dev x 10 ngày) | 10.000.000 | 66.7% |
| 2. Chi phí Kiểm thử (1 QA x 3 ngày) | 2.400.000 | 16.0% |
| 3. Chi phí hạ tầng & Dịch vụ | 1.400.000 | 9.3% |
| 4. Dự phòng rủi ro | 1.200.000 | 8.0% |
| **TỔNG CỘNG CHI PHÍ DỰ ÁN** | **15.000.000** | **100%** |

*(Bằng chữ: Mười lăm triệu đồng chẵn).*

---

#### 4. Kế hoạch Thanh toán Đề xuất
- **Đợt 1: Tạm ứng (30%)** - 4.500.000 VND (Ký HĐ ngày 07/07/2026).
- **Đợt 2: Nghiệm thu Giai đoạn Phát triển (40%)** - 6.000.000 VND (Ngày 15/07/2026).
- **Đợt 3: Bàn giao toàn bộ Golive (30%)** - 4.500.000 VND (Ngày 20/07/2026).

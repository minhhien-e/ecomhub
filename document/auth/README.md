# Auth Service

## 1. Giới thiệu tổng quan
Đây là service xác thực & ủy quyền trong hệ thống.
Nó đóng vai trò:
-  **Đăng ký / Đăng nhập người dùng**
-  **Cấp phát & quản lý Access Token / Refresh Token**
-  **Thu hồi token**
-  **Cung cấp JWK cho các service khác xác thực token**  

>  Service đang chạy tại **port `8081`**.

---

##  2. Chức năng chi tiết

### Quản lý tài khoản
- **Đăng ký** → Tạo mới một tài khoản người dùng.  
- **Đăng nhập** → Đăng nhập bằng **tên đăng nhập / email / số điện thoại** và mật khẩu.  
- **Đăng xuất** → Thu hồi **access token** và **refresh token** đã cấp.  
- **Gán role** → Thêm role cho tài khoản.  
- **Thu hồi role** → Gỡ bỏ role khỏi tài khoản.  

### Quản lý role
- **Tạo role** → Khởi tạo một role mới.  
- **Xóa role** → Loại bỏ một role.  
- **Đổi tên** → Cập nhật tên của role hiện có.  
- **Đổi cấp độ** → Cập nhật cấp độ (level) của role hiện có.  
- **Đổi mô tả** → Cập nhật mô tả chi tiết cho role.  
- **Gán quyền** → Thêm quyền cho role.  
- **Thu hồi quyền** → Gỡ quyền khỏi role.  
- **Lấy danh sách role** → Truy xuất toàn bộ các role trong hệ thống.  

### Quản lý quyền
- **Đổi tên** → Cập nhật tên quyền hiện có.  
- **Đổi mô tả** → Cập nhật mô tả quyền hiện có.  
- **Lấy danh sách quyền** → Truy xuất toàn bộ các quyền trong hệ thống.  

---

##  3. Cấu trúc thư mục

```bash
auth-services/
├── application/
│   ├── command/       # Các lệnh (command) và bộ xử lý (command handler)
│   ├── query/         # Các truy vấn (query) và bộ xử lý (query handler)
│   ├── port/          # Định nghĩa các cổng kết nối (port) với thành phần/hệ thống bên ngoài
│   ├── service/       # Hiện thực các trường hợp sử dụng (use case) trong lớp ứng dụng
├── domain/            # Chứa entity, value object và domain service để đóng gói nghiệp vụ cốt lõi
├── infrastructure/    # Hiện thực hạ tầng: giao tiếp đầu vào, bảo mật, tích hợp thư viện/hệ thống ngoài
├── shared/            # Các thành phần dùng chung: class tiện ích, request/response, exception


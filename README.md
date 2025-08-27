# Cart Service

## 1. Giới thiệu tổng quan
Đây là **service quản lý giỏ hàng** trong hệ thống.  
Nó đóng vai trò:
-  **Cập nhật thông tin giỏ hàng cho người dùng**  

>  Service đang chạy tại **port `8083`**.

---

##  2. Chức năng chi tiết

###  Quản lý Giỏ Hàng
- **Thêm sản phẩm vào giỏ** → Thêm một sản phẩm cụ thể vào giỏ hàng của người dùng.  
- **Xem giỏ hàng theo người dùng** → Lấy toàn bộ danh sách sản phẩm hiện có trong giỏ hàng của người dùng. 
- **Cập nhật sản phẩm trong giỏ hàng** → Cập nhật số lượng sản phẩm đã có trong giỏ hàng.
- **Xóa một sản phẩm khỏi giỏ** → Loại bỏ sản phẩm cụ thể khỏi giỏ hàng của người dùng.
- **Xóa toàn bộ giỏ hàng** → Xóa sạch tất cả sản phẩm trong giỏ hàng của người dùng (dùng khi người dùng "xóa giỏ hàng").

---

## 📂 3. Cấu trúc thư mục

```bash
com.ecomhub.cartservice/
├── adapters/          # Controller: định nghĩa endpoint
├── application/      #  Service layer: xử lý logic ứng dụng
├── domain/           #  Business logic & entity chính
├── infrastructure/   #  Repository, kết nối DB, cấu hình hệ thống
├── shared/           #  Định nghĩa các phần dùng chung

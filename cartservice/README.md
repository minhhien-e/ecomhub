# Cart Service

**CartService** là microservice chịu trách nhiệm quản lý giỏ hàng của người dùng trong hệ sinh thái **EcomHub**. Dịch vụ
cung cấp khả năng lưu trữ, tính toán giá trị giỏ hàng tạm thời và xác thực sản phẩm thông qua việc gọi các dịch vụ liên
quan.

---

- **Port**: `9090`
- **Database**: `MySQL` (Map port 3306)
- **Java Version**: `Java 21 (LTS)`

---

## 1. Giới thiệu

- **Mục đích**: Quản lý các mặt hàng người dùng chọn mua, xử lý số lượng và trạng thái giỏ hàng trước khi chuyển sang
  giai đoạn thanh toán.
- **Đặc điểm nổi bật**:
    - **JPA Optimization**: Các thực thể (Entity) sử dụng `@NoArgsConstructor` để tối ưu hóa quá trình khởi tạo của
      Hibernate.
    - **Security**: Tích hợp OAuth2 Resource Server với JWT để xác thực và phân quyền người dùng thông qua Bearer Token.
    - **Refactored Package**: Toàn bộ dự án đã được chuyển đổi sang package chuẩn `ecomhub.cartservice` để đồng bộ hệ
      thống.

---

## 2. Chức năng chi tiết

### 2.1. Quản lý giỏ hàng

* **Khởi tạo**: Tự động tạo giỏ hàng mới khi nhận sự kiện (Event) từ Account Service thông qua Kafka.
* **Xóa giỏ hàng**: Thực hiện xóa giỏ hàng dựa trên UUID khách hàng được trích xuất từ Bearer Token.
* **Làm rỗng**: Xóa tất cả sản phẩm khỏi giỏ hàng sau khi đơn hàng được tạo thành công.

### 2.2. Quản lý sản phẩm (Items)

* **Thêm sản phẩm**: Thêm sản phẩm vào giỏ dựa trên thông tin định danh khách hàng, UUID của Variant sản phẩm và số
  lượng tương ứng.
* **Xóa sản phẩm**: Loại bỏ một sản phẩm cụ thể khỏi giỏ hàng dựa trên UUID của Variant.
* **Cập nhật**: Thay đổi số lượng Variant sản phẩm trực tiếp trong giỏ hàng.

### 2.3. Xác thực & Sự kiện

* **Xác thực**: Sử dụng **RestTemplate** gọi **Product Service** để kiểm tra tính hợp lệ của sản phẩm về ID, giá và số
  lượng tồn kho thực tế.
* **Xử lý sự kiện**: Lắng nghe sự kiện Account Service để tiến hành khởi tạo giỏ hàng

---

## 3. Cấu trúc thư mục (CHỈ CẤP FOLDER)

```text
src/main/java/ecomhub/cartservice/
├── config          → Cấu hình hệ thống (Security, Kafka, JPA)
├── controller      → REST Controller xử lý yêu cầu từ Client
├── converter       → Chuyển đổi dữ liệu Entity ↔ DTO (Mappers)
├── dto             → Chứa các Data Transfer Objects (Request/Response)
├── enums           → Định nghĩa ErrorCode và HttpStatus chuẩn hệ thống
├── event           → Xử lý sự kiện nội bộ và Kafka
│   ├── listener    → Lắng nghe các thay đổi hệ thống (Account Event)
│   └── model       → Định nghĩa Schema cho Event
├── exception       → Xử lý lỗi tập trung và Global Handler
├── model           → JPA Entities (Sử dụng @NoArgsConstructor cho Hibernate)
├── repository      → Giao tiếp MySQL qua Spring Data JPA
└── service         → Logic nghiệp vụ chính và tích hợp dịch vụ ngoài
```

## 4. flow architecture
````
flowchart TD
User["Client (Web/Mobile)"]
Controller["controller (CartController)"]
Service["service (CartServiceImpl)"]
ProductSvc["Product Service (External)"]
AccountSvc["Account Service"]
Kafka["Kafka Consumer (Event Listener)"]
DB[(MySQL)]

    %% HTTP Request Flow
    User        -->|"HTTP + JWT"| Controller
    Controller  -->|"Gọi nghiệp vụ"| Service
    Service     -->|"GET /variants/{id}"| ProductSvc
    %% Response Flow
    ProductSvc  -- "JSON (ProductCheckDto)"         --> Service
    Service     -->|"Lưu/Cập nhật"| DB
    Controller  -- "JSON Response (ApiResponse)"    --> User

    %% Event Flow
    AccountSvc  -- "Account Created Event"          --> Kafka
    Kafka       -- "Trigger Create Cart"            --> Service
````
````
path img: picture/flow_architecture.jpg
````
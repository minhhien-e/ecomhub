# Cart Service

**CartService** là microservice chịu trách nhiệm quản lý giỏ hàng của người dùng trong hệ sinh thái **EcomHub**. Dịch vụ
cung cấp khả năng lưu trữ, tính toán giá trị giỏ hàng tạm thời và xác thực sản phẩm thông qua việc gọi các dịch vụ liên
quan.

---

- **Port**: `9090`
- **Database**: `MySQL` (Map port 3306)

---

## 1. Giới thiệu

- **Mục đích**: Quản lý các mặt hàng người dùng chọn mua, xử lý số lượng và trạng thái giỏ hàng trước khi chuyển sang
  giai đoạn thanh toán.
- **Đặc điểm nổi bật**:
    - **Refactored Package**: Toàn bộ dự án đã được chuyển đổi sang package chuẩn `ecomhub.cartservice`.
    - **JPA Optimization**: Các thực thể (Entity) sử dụng `@NoArgsConstructor` để tối ưu hóa quá trình khởi tạo của
      Hibernate.
    - **Java 21 LTS**: Tận dụng các tính năng mới nhất và tính ổn định của JDK 21.
    - **Security**: Tích hợp OAuth2 Resource Server với JWT để xác thực và phân quyền người dùng.

---

## 2. Chức năng chi tiết

1. **Quản lý giỏ hàng**: Lấy thông tin giỏ hàng chi tiết của người dùng hiện tại dựa trên JWT.
2. **Quản lý Item**: Thêm, cập nhật số lượng hoặc xóa sản phẩm khỏi giỏ hàng.
3. **Xác thực sản phẩm**: Gọi **Product Service** để kiểm tra tính hợp lệ của sản phẩm (ID, giá, tồn kho).
4. **Tính toán**: Tự động tính lại tổng tiền tạm tính (Subtotal) khi có thay đổi trong giỏ.
5. **Dọn dẹp**: Làm rỗng giỏ hàng sau khi đơn hàng được tạo thành công.
   6**Sự kiện**: Phát hành các sự kiện (Events) lên Kafka để phục vụ xử lý dữ liệu ở các microservice khác.

---

## 3. Cấu trúc thư mục (CHỈ CẤP FOLDER)

```text
src/main/java/ecomhub/cartservice/
├── config          → Cấu hình hệ thống (Security, Kafka, JPA)
├── controller      → REST Controller xử lý yêu cầu từ Client
├── converter       → Chuyển đổi dữ liệu Entity ↔ DTO (Mappers)
├── dto             → Chứa các Data Transfer Objects
│   ├── request     → DTO nhận dữ liệu đầu vào (Add/Update Cart)
│   └── response    → DTO trả về kết quả (ApiResponse)
├── enums           → Định nghĩa các kiểu dữ liệu liệt kê (ErrorCode, HttpStatus)
├── event           → Xử lý sự kiện nội bộ và sự kiện Kafka
│   ├── listener    → Lắng nghe các thay đổi hệ thống
│   └── model       → Định nghĩa Schema cho Event
├── exception       → Xử lý lỗi tập trung và Global Handler
│   ├── custom      → Các ngoại lệ nghiệp vụ (ProductNotFound, v.v.)
│   └── handler     → Trả về định dạng ApiResponse chuẩn
├── helper          → Các lớp tiện ích bổ trợ logic
├── model           → JPA Entities (Sử dụng @NoArgsConstructor)
├── repository      → Giao tiếp MySQL qua Spring Data JPA
└── service         → Logic nghiệp vụ giỏ hàng
    └── external    → Chứa service sang Product/Promotion Service


```

## 4. Công nghệ & Thư viện sử dụng

| Công nghệ / Thư viện          | Tác dụng                                                                                 | Link tham khảo                                          |
|:------------------------------|:-----------------------------------------------------------------------------------------|:--------------------------------------------------------|
| **Java 21 (LTS)**             | Phiên bản chạy chính thức, đảm bảo hiệu suất và tính ổn định cao.                        | [oracle.com](https://www.oracle.com/java/)              |
| **Spring Boot 3.4.0**         | Framework chính hỗ trợ phát triển microservices.                                         | [spring.io](https://spring.io/projects/spring-boot)     |
| **Spring Kafka**              | Nhận và xử lý các sự kiện (Event Listener) từ Message Broker.                            | [spring.io](https://spring.io/projects/spring-kafka)    |
| **Spring Web (RestTemplate)** | Sử dụng **RestTemplate** để gọi API đồng bộ sang Product Service nhằm xác thực sản phẩm. | [spring.io](https://spring.io/projects/spring-web)      |
| **Spring Data JPA**           | Quản lý thực thể (Entity) và lưu trữ dữ liệu bền vững vào MySQL.                         | [spring.io](https://spring.io/projects/spring-data-jpa) |
| **Spring Security + OAuth2**  | Bảo mật Resource Server, xác thực JWT và phân quyền theo scope.                          | [spring.io](https://spring.io/projects/spring-security) |
| **Jakarta Validation**        | Kiểm tra tính hợp lệ của dữ liệu đầu vào (@NotNull, @Min...).                            | [beanvalidation.org](https://beanvalidation.org)        |
| **Lombok 1.18.34**            | Giảm thiểu mã lặp, hỗ trợ `@NoArgsConstructor` cho chuẩn JPA.                            | [projectlombok.org](https://projectlombok.org)          |
| **MySQL 8**                   | Cơ sở dữ liệu quan hệ chính lưu trữ thông tin giỏ hàng lâu dài.                          | [mysql.com](https://www.mysql.com)                      |

## 5. Sơ đồ luồng xử lý (Architecture & Request Flow)

````
flowchart TD
    User["Client (Web/Mobile)"]
    Controller["controller (CartController)"]
    Service["service (CartServiceImpl)"]
    ProductSvc["service/external (Product Service)"]
    Repo["repository"]
    DB[(MySQL)]
    Kafka["event (Kafka Producer)"]

    User -->|"HTTP + JWT"| Controller
    Controller -->|"Gọi nghiệp vụ"| Service

    %% Logic Flow
    Service -->|"Validate Product"| ProductSvc
    Service -->|"Lưu/Cập nhật"| Repo --> DB

    %% Messaging
    Service -->|"Gửi sự kiện"| Kafka

    %% Response
    Controller -->|"ApiResponse JSON"| User

    classDef normal fill:#ffffff,stroke:#333,stroke-width:2px;
    classDef db fill:#e3f2fd,stroke:#1565c0,stroke-width:2px;
    classDef ext fill:#fafafa,stroke:#555,stroke-dasharray:6 4;

    class User,Controller,Service,Repo normal
    class DB db
    class ProductSvc,Kafka ext
```
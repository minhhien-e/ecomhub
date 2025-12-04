# Promotion Discount Service

**PromotionDiscountService** là microservice quản lý chương trình khuyến mãi trong hệ thống **EcomHub**.  
Dịch vụ hỗ trợ CRUD, lọc theo trạng thái thời gian (active/expired/upcoming), tính giá cuối sau giảm, bảo mật bằng JWT (`read`, `write`, `delete`), và sẵn sàng tích hợp Kafka.

---

## 1. Giới thiệu

- **Mục đích**: Quản lý vòng đời khuyến mãi và cung cấp API tính giá cuối cho Cart, Order, v.v.
- **Đặc điểm nổi bật**:
  - Bảo mật OAuth2 Resource Server với JWT + phân quyền theo scope (`authorities`)
  - Hỗ trợ 2 dạng giảm giá: `PERCENTAGE`, `FIXED`
  - Validate thời gian áp dụng (không cho apply khi chưa đến hạn hoặc đã hết hạn)
  - Mock Kafka, sẵn sàng thay bằng Kafka thật

---

## 2. Chức năng chi tiết

1. Lấy danh sách khuyến mãi theo phân trang, sắp xếp theo ngày bắt đầu.
2. Lấy chi tiết khuyến mãi theo ID.
3. Tra cứu khuyến mãi theo mã code.
4. Lọc danh sách khuyến mãi đang active.
5. Lọc danh sách khuyến mãi đã hết hạn.
6. Lọc danh sách khuyến mãi sắp hết hạn trong X ngày (mặc định 7).
7. Lọc theo trạng thái khuyến mãi ACTIVE / INACTIVE.
8. Tạo mới khuyến mãi kèm validate nghiệp vụ.
9. Cập nhật thông tin khuyến mãi.
10. Xóa (deactivate) khuyến mãi.
11. Áp dụng khuyến mãi → trả về `finalPrice` + `discountAmount`.

---

## 3. Cấu trúc thư mục (CHỈ CẤP FOLDER — KHÔNG LIỆT KÊ FILE)

```text
src/main/java/ecomhub/PromotionDiscountService/
├── config
│   └── security           → Cấu hình bảo mật JWT + OAuth2 Resource Server
├── controller              → Chứa REST Controller xử lý HTTP request
├── dto                     → Các DTO request/response
├── exception               → Toàn bộ exception + global handler
├── mapper                  → Chuyển đổi giữa Entity ↔ DTO
├── model                   → Entity + enum (Promotion, DiscountType, Status)
├── repository              → Giao tiếp DB bằng JPA Repository
├── service
│   ├── impl                → QueryService, CommandService, Applicator, Facade
│   ├── MessageQueueService → Mock Kafka (log event)
│   └── PromotionService    → Interface chính
└── util                    → Các tiện ích chung (response wrapper…)

``` 
## 4. Công nghệ & Thư viện sử dụng

| Công nghệ / Thư viện                              | Tác dụng                                           | Link tham khảo                                      |
|---------------------------------------------------|--------------------------------------------------|----------------------------------------------------|
| Spring Boot 3                                     | Framework chính, auto-configuration             | [spring.io](https://spring.io/projects/spring-boot) |
| Spring Web                                        | Xây dựng REST API                                | [spring.io](https://spring.io/projects/spring-web) |
| Spring Data JPA + Hibernate                       | ORM, quản lý entity, query                        | [spring.io](https://spring.io/projects/spring-data-jpa) |
| Spring Security + OAuth2 Resource Server          | Bảo mật JWT, phân quyền theo scope (`authorities`) | [spring.io](https://spring.io/projects/spring-security) |
| Jakarta Validation                                | Validate request (@NotNull, @DecimalMax, @FutureOrPresent, ...) | [beanvalidation.org](https://beanvalidation.org) |
| Lombok                                            | Giảm boilerplate (getter, setter, @RequiredArgsConstructor, ...) | [projectlombok.org](https://projectlombok.org) |
| MySQL 8                                           | Database chính                                   | [mysql.com](https://www.mysql.com)                |
| Flyway                                            | Database migration (V1__init.sql)               | [flywaydb.org](https://flywaydb.org)             |
| Hibernate Types (UUID)                            | Hỗ trợ lưu UUID dưới dạng BINARY(16)            | [GitHub](https://github.com/vladmihalcea/hibernate-types) |

## 5. Sơ đồ luồng xử lý (Architecture & Request Flow)

```mermaid
flowchart TD
    Client["Client"]
    Controller["controller (REST)"]
    Service["service (Query + Command + Applicator + Facade)"]
    Repo["repository"]
    DB[(MySQL)]
    MQ["service/MessageQueueService\n(Mock Kafka)"]

    Client -->|"HTTP + JWT"| Controller
    Controller -->|"Gọi nghiệp vụ"| Service

    %% Query
    Service -->|"Truy vấn"| Repo --> DB --> Repo --> Service

    %% Command
    Service -->|"Tạo/Cập nhật/Xóa"| Repo --> DB
    Service -->|"Gửi sự kiện"| MQ

    %% Apply
    Service -->|"Lấy dữ liệu + tính toán"| Repo --> DB

    %% Response
    Controller -->|"JSON Response"| Client

    classDef normal fill:#ffffff,stroke:#333,stroke-width:2px;
    classDef db fill:#e3f2fd,stroke:#1565c0,stroke-width:2px;
    classDef mq fill:#fafafa,stroke:#555,stroke-dasharray:6 4;

    class Client,Controller,Service,Repo normal
    class DB db
    class MQ mq

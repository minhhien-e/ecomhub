# Promotion Discount Service

**PromotionDiscountService** là microservice quản lý chương trình khuyến mãi trong hệ thống **EcomHub**.  
Dịch vụ hỗ trợ đầy đủ CRUD, lọc theo trạng thái thời gian (active/expired/upcoming), áp dụng giảm giá theo phần trăm hoặc số tiền cố định, bảo mật bằng JWT + phân quyền chi tiết (`read`, `write`, `delete`), và đợi tích hợp Kafka.

---

## 1. Giới thiệu

- **Mục đích**: Quản lý vòng đời khuyến mãi và cung cấp API tính giá sau giảm cho các service khác (Cart, Order, v.v.).
- **Đặc điểm nổi bật**:
    - Bảo mật JWT Resource Server với scope-based authority (`read`, `write`, `delete`)
    - Hỗ trợ 2 loại giảm giá: `PERCENTAGE` và `FIXED`
    - Tự động validate thời gian áp dụng, không cho apply khuyến mãi chưa bắt đầu/đã hết hạn
    - Kiến trúc sạch: phân tách rõ ràng Query – Command – Applicator
    - Mock message queue, sẵn sàng thay bằng Kafka

---

## 2. Chức năng chi tiết

| STT | API                                   | Method | Authority   | Mô tả                                                                                   | Ghi chú                                      |
|-----|---------------------------------------|--------|-------------|-----------------------------------------------------------------------------------------|----------------------------------------------|
| 1   | `/api/promotions`                     | GET    | `read`      | Lấy danh sách phân trang (mặc định 20, sort `startDate DESC`)                           |                                              |
| 2   | `/api/promotions/{id}`                | GET    | `read`      | Lấy chi tiết theo ID                                                                    |                                              |
| 3   | `/api/promotions/code/{code}`         | GET    | `read`      | Lấy theo mã khuyến mãi                                                                  |                                              |
| 4   | `/api/promotions/active`              | GET    | `read`      | Danh sách đang active (now ∈ [startDate, endDate])                                      |                                              |
| 5   | `/api/promotions/expired`             | GET    | `read`      | Danh sách đã hết hạn                                                                    |                                              |
| 6   | `/api/promotions/upcoming-expired`    | GET    | `read`      | Sắp hết hạn trong X ngày (default 7)                                                    |                                              |
| 7   | `/api/promotions/status/{status}`    | GET    | `read`      | Lọc theo trạng thái ACTIVE/INACTIVE + phân trang                                        |                                              |
| 8   | `/api/promotions`                     | POST   | `write`     | Tạo mới                                                                                 |                                              |
| 9   | `/api/promotions/{id}`                | PUT    | `write`     | Cập nhật                                                                                |                                              |
| 10  | `/api/promotions/{id}`                | DELETE | `delete`    | Xóa                                                                                     |                                              |
| 11  | `/api/promotions/{id}/apply`          | GET    | `read`      | Áp dụng khuyến mãi → trả về `finalPrice` + `discountAmount`                             | Kiểm tra thời gian + trạng thái              |

## 3. Cấu trúc thư mục cấp folder

```text
src/main/java/ecomhub/PromotionDiscountService/
├── config
│   └── security
│       ├── JwtAuthenticationConverterConfig.java    → Map claim "authorities" → GrantedAuthority
│       └── SecurityFilterChainConfig.java            → Cấu hình Security + OAuth2 Resource Server
├── controller
│   └── PromotionDiscountController.java              → REST endpoints + @PreAuthorize
├── dto
│   ├── request   → PromotionDiscountRequest.java
│   └── response  → ApiResponse, PromotionDiscountResponse.java
├── exception
│   ├── GlobalExceptionHandler.java
│   ├── PromotionNotFoundException.java
│   └── PromotionValidationException.java
├── mapper
│   └── PromotionDiscountMapper.java                  → Entity ↔ DTO
├── model
│   ├── PromotionDiscount.java                        → @Entity
│   ├── DiscountType.java
│   └── PromotionStatus.java
├── repository
│   └── PromotionDiscountRepository.java              → JpaRepository + các query tùy chỉnh
├── service
│   ├── impl
│   │   ├── PromotionQueryService.java                → GET, filter
│   │   ├── PromotionCommandService.java              → CREATE, UPDATE, DELETE + validate nghiệp vụ
│   │   ├── PromotionDiscountApplicator.java          → Logic apply discount
│   │   └── PromotionDiscountServiceImpl.java         → Facade implement interface
│   ├── MessageQueueService.java                      → Mock Kafka (log), dễ thay bằng KafkaTemplate
│   └── PromotionDiscountService.java                 → Interface chính
├── util
│   └── ResponseUtil.java                             → Wrapper ResponseEntity
└── PromotionDiscountServiceApplication.java          → @SpringBootApplication + @EnableJpaAuditing

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
    Service["PromotionDiscountService\nport: 8088"]
    Sec[["Security Filter\nJWT + authorities"]]
    Ctrl["PromotionDiscountController"]
    Facade["PromotionDiscountServiceImpl\n(Facade)"]
    Query["PromotionQueryService"]
    Cmd["PromotionCommandService"]
    Apply["PromotionDiscountApplicator"]
    Repo["JPA Repository"]
    DB[(MySQL)]
    MQ["Message Queue\n(Mock Kafka)"]

    Client -->|"HTTP + Bearer Token"| Service
    Service --> Sec

    Sec -->|"Hợp lệ + đủ quyền"| Ctrl
    Sec -->|"Token sai / thiếu quyền"| E401[401 / 403]

    Ctrl --> Facade

    %% Query
    Facade --> Query
    Query --> Repo --> DB --> Repo --> Query --> Facade

    %% Command
    Facade --> Cmd
    Cmd --> Repo --> DB
    Cmd -->|"CREATED / UPDATED / DELETED"| MQ

    %% Apply
    Facade --> Apply
    Apply --> Repo --> DB --> Repo
    Apply -->|Thành công| Success[200 OK\n+ finalPrice]
    Apply -->|Thất bại| Error400[400\nKhông tồn tại\nhoặc hết hạn]

    %% Response
    Facade -->|"200 / 201 / 204"| Resp[Response]

    %% === Styling đẹp, chuẩn IntelliJ & GitHub ===
    classDef normal   fill:#ffffff,stroke:#333333,stroke-width:2px,color:#000000
    classDef success  fill:#e8f5e8,stroke:#4caf50,stroke-width:3px,color:#1b5e20
    classDef error    fill:#ffebee,stroke:#f44336,stroke-width:3px,color:#c62828
    classDef mq       fill:#f5f5f5,stroke:#999999,stroke-dasharray: 6 4,color:#444444

    class Client,Service,Sec,Ctrl,Facade,Query,Cmd,Apply,Repo,DB,Resp normal
    class Success success
    class E401,Error400 error
    class MQ mq
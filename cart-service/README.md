# Cart Service

## 1. Security Layer (OAuth2 Resource Server + JWT)
 
-  Thiết lập OAuth2 Resource Server để validate JWT từ Authorization Server.
-  **Cấu hình CustomJwtAuthenticationConverter để map claim authorities → GrantedAuthority**
-  **SecurityFilterChainConfig:**
    * /api/v1/cart/ → authentication  
    * /api/v1/cartitem/ → authentication
   
-  **Kích hoạt @EnableMethodSecurity hỗ trợ @PreAuthorize.**  

## Authorities sử dụng trong service:
- role.get_cart
- role.clear_cart
- role.delete_cart
- role.get_cartitem
- role.add_cartitem
- role.create_cartitem
- role.update_cartitem
- role.remove_cartitem
- role.get_cartitem


---

##  2. API CartService

## Presentation layer
### Controller
* Chịu trách nhiệm thao tác với Cart ( get, clear, delete) 
* Chịu trách nhiệm Thao tác với varient trong cart (add cart item, update cart item, remove cart item )

### Validation
- VariantId is required
- Quantity is required
- Price must be >= 0
- quantity is required
- CartId is required

## Business layer
### Service
* Khi Controller yêu cầu, thì Service tương ứng sẽ tiếp nhận và cho ra dữ liệu trả cho Controller
#### external service
* dùng RestTemplate để gọi service bên ngoài (vd: lấy thông tin sản phẩm từ product service)
## Data Access layer
### Repository
* Thao tác truy vấn dữ liệu 
* 
## 3. DTO
* Request → Entity
* Entity → Response
 
## 4. Exception Handling
* Bean Validation error
* Not found error
* Business validation error

#### Exception mới
* ExternalApiException để gói thông tin quan trọng gửi về từ external service

## 5. Hạ tầng & Auditing
* Thêm Flyway migration
* Cấu hình database, Flyway, và issuer-uri cho JWT trong application.yml

## 6. Event-Driven Architecture (Kafka)
* Service tích hợp Apache Kafka để xử lý các luồng dữ liệu bất đồng bộ từ các Microservices khác, đảm bảo tính toàn vẹn dữ liệu (Eventual Consistency) mà không làm tăng độ trễ của hệ thống.
* Cấu hình Kafka (KafkaConfig)
* Topics:
* 
**user-events:**  Lắng nghe các sự kiện từ User/Identity Service (số lượng partition: 3).

**resource-events:** Lắng nghe các thay đổi về tài nguyên hệ thống.

**Consumer Group:** cart-service-group.

#### Luồng xử lý tạo Giỏ hàng (Auto-create Cart):
* Khi một người dùng mới được đăng ký thành công trong hệ thống, Cart Service sẽ tự động khởi tạo một giỏ hàng trống cho người dùng đó thông qua cơ chế Event Listener.

**Listener:** UserEventListener đăng ký lắng nghe topic user-events.

* Xử lý sự kiện: 
* Nhận thông điệp dạng JSON và map vào UserEvent model.
* Kiểm tra eventType. Nếu là "USER_CREATED", tiến hành trích xuất userId.
* Chuyển đổi userId (String) sang UUID.
* Thực thi: Gọi CreateService.createCart(userIdUuid) để lưu giỏ hàng mới vào Database thông qua Repository.
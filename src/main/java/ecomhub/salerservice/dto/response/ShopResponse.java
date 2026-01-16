package ecomhub.salerservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ShopResponse {
    private UUID id;
    private String name;
    private String bannerUrl;
    private String contact;
    private String email;
    private String phone;
    private String address;
    private String status;

    // Thêm 2 trường này
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
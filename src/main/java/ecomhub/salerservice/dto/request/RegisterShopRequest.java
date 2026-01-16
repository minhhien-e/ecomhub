package ecomhub.salerservice.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class RegisterShopRequest {
    private String name;
    private String bannerUrl;
    private String email;
    private String phone;
    private String address;
    private String contact;
}

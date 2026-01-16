package ecomhub.salerservice.dto.request;

import ecomhub.salerservice.enums.Status;
import lombok.Data;

@Data
public class ShopVerificationRequest {
    private Status status;
}

package ecomhub.salerservice.dto.response;


import lombok.Data;

import java.util.UUID;

@Data
public class ExternalProductResponse {
    private UUID id;
    private String name;
}

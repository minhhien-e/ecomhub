package ecomhub.salerservice.dto.response;

import ecomhub.salerservice.model.Product;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class ShopDetailResponse {
    private ShopResponse shopInfo;
    private Page<Product> products;
}

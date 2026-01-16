package ecomhub.salerservice.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductCreateRequest {
   private UUID shopId;
   private String name;
   private Boolean isActive;
//   CÒn nữa, tạm thời để vậy nữa thêm sau
}

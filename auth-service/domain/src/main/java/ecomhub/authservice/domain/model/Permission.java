package ecomhub.authservice.domain.model;

import ecomhub.authservice.common.helper.ValidationHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    private UUID id;
    private String name;
    private String description;

    /**
     * Kiểm tra tính hợp lệ để thêm quyền
     */
    public void validateForAdd() {
        ValidationHelper.requireNonBlank("name", name);
        ValidationHelper.requireNonBlank("description", description);
    }
}

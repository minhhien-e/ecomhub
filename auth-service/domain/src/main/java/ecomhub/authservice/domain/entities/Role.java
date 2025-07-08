package ecomhub.authservice.domain.entities;

import ecomhub.authservice.common.helper.ValidationHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private UUID id;
    private String name;
    private String description;
    private Set<UUID> permissionIds;

    /**
     * Kiểm tra tính hợp lệ để thêm quyền
     */
    public void validateForAdd() {
        ValidationHelper.requireNonBlank("name", name);
        ValidationHelper.requireNonBlank("description", description);
    }
}

package ecomhub.authservice.infrastructure.outbound.persistence.specification;

import ecomhub.authservice.infrastructure.outbound.persistence.entity.AccountEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class AccountSpecification {

    /**
     * Tạo điều kiện Specification để tìm người dùng theo thông tin định danh:
     * username, email hoặc số điện thoại.
     *
     * @param identifier Giá trị định danh (có thể là email, username hoặc số điện thoại)
     * @return Specification cho truy vấn động
     */
    public static Specification<AccountEntity> byIdentifier(String identifier) {
        return (root, query, cb) -> {
            try {
                UUID uuid = UUID.fromString(identifier);
                return cb.or(
                        cb.equal(root.get("id"), uuid)
                );
            } catch (IllegalArgumentException ex) {
                return cb.or(
                        cb.equal(root.get("email"), identifier),
                        cb.equal(root.get("username"), identifier),
                        cb.equal(root.get("phoneNumber"), identifier)
                );
            }
        };
    }

}

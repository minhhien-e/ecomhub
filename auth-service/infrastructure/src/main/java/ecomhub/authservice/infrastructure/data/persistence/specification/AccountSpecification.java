package ecomhub.authservice.infrastructure.data.persistence.specification;

import ecomhub.authservice.infrastructure.data.persistence.entity.AccountEntity;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {

    /**
     * Tạo điều kiện Specification để tìm người dùng theo thông tin định danh:
     * username, email hoặc số điện thoại.
     *
     * @param identifier Giá trị định danh (có thể là email, username hoặc số điện thoại)
     * @return Specification cho truy vấn động
     */
    public static Specification<AccountEntity> byIdentifier(String identifier) {
        return (root, query, cb) -> cb.or(
                cb.equal(root.get("email"), identifier),
                cb.equal(root.get("username"), identifier),
                cb.equal(root.get("phoneNumber"), identifier)
        );
    }
}

package ecomhub.PromotionDiscountService.repository;

import ecomhub.PromotionDiscountService.model.PromotionDiscount;
import ecomhub.PromotionDiscountService.model.PromotionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PromotionDiscountRepository extends JpaRepository<PromotionDiscount, UUID> {
    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code, UUID id);
    boolean existsByIdAndStatus(UUID id, PromotionStatus status);
    Optional<PromotionDiscount> findByCode(String code);
    List<PromotionDiscount> findByStatusAndStartDateBeforeAndEndDateAfter(PromotionStatus status, Instant now1, Instant now2);
    List<PromotionDiscount> findByEndDateBefore(Instant now);
    List<PromotionDiscount> findByEndDateBetween(Instant now, Instant soon);
    Page<PromotionDiscount> findByStatus(PromotionStatus status, Pageable pageable);
}

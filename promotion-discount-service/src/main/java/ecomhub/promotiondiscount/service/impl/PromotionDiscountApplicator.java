package ecomhub.promotiondiscount.service.impl;

import ecomhub.promotiondiscount.dto.response.PromotionDiscountResponse;
import ecomhub.promotiondiscount.exception.PromotionNotFoundException;
import ecomhub.promotiondiscount.exception.PromotionValidationException;
import ecomhub.promotiondiscount.mapper.PromotionDiscountMapper;
import ecomhub.promotiondiscount.model.DiscountType;
import ecomhub.promotiondiscount.model.PromotionDiscount;
import ecomhub.promotiondiscount.model.PromotionStatus;
import ecomhub.promotiondiscount.repository.PromotionDiscountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PromotionDiscountApplicator {

    private final PromotionDiscountRepository repo;
    private final PromotionDiscountMapper mapper;

    @Transactional(readOnly = true)
    public PromotionDiscountResponse applyDiscount(UUID id, Double originalPrice) {
        if (originalPrice == null || originalPrice <= 0) {
            throw new PromotionValidationException("Giá gốc phải lớn hơn 0");
        }
        if (!repo.existsByIdAndStatus(id, PromotionStatus.ACTIVE)) {
            throw new PromotionValidationException("Khuyến mãi không tồn tại hoặc không active");
        }
        PromotionDiscount e = repo.findById(id)
                .orElseThrow(() -> new PromotionNotFoundException(id.toString()));
        Instant now = Instant.now();
        if (e.getStartDate().isAfter(now) || e.getEndDate().isBefore(now)) {
            throw new PromotionValidationException("Khuyến mãi ngoài thời gian áp dụng");
        }
        double discounted = (e.getDiscountType() == DiscountType.PERCENTAGE)
                ? originalPrice * (1 - e.getDiscountValue() / 100)
                : Math.max(0, originalPrice - e.getDiscountValue());
        PromotionDiscountResponse r = mapper.toResponse(e);
        r.setOriginalPrice(originalPrice);
        r.setDiscountedPrice(discounted);
        return r;
    }
}
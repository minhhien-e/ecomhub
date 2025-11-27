package ecomhub.PromotionDiscountService.mapper;

import ecomhub.PromotionDiscountService.dto.request.PromotionDiscountRequest;
import ecomhub.PromotionDiscountService.dto.response.PromotionDiscountResponse;
import ecomhub.PromotionDiscountService.model.DiscountType;
import ecomhub.PromotionDiscountService.model.PromotionDiscount;
import ecomhub.PromotionDiscountService.model.PromotionStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class PromotionDiscountMapper {
    public PromotionDiscount toEntity(PromotionDiscountRequest req) {
        if (req == null) return null;
        PromotionDiscount e = new PromotionDiscount();
        e.setId(UUID.randomUUID()); // Manual UUID, JPA sẽ không override vì strategy UUID
        mapFields(req, e);
        e.setCreatedAt(Instant.now());
        e.setUpdatedAt(Instant.now());
        return e;
    }

    public PromotionDiscountResponse toResponse(PromotionDiscount e) {
        if (e == null) return null;
        PromotionDiscountResponse r = new PromotionDiscountResponse();
        r.setId(e.getId());
        r.setName(e.getName());
        r.setCode(e.getCode());
        r.setDescription(e.getDescription());
        r.setDiscountType(String.valueOf(e.getDiscountType()));
        r.setDiscountValue(e.getDiscountValue());
        r.setStartDate(e.getStartDate());
        r.setEndDate(e.getEndDate());
        r.setStatus(String.valueOf(e.getStatus()));
        r.setCreatedAt(e.getCreatedAt());
        r.setUpdatedAt(e.getUpdatedAt());
        return r;
    }

    public void updateEntity(PromotionDiscountRequest req, PromotionDiscount e) {
        mapFields(req, e);
        e.setUpdatedAt(Instant.now());
    }

    private void mapFields(PromotionDiscountRequest r, PromotionDiscount e) {
        e.setName(r.getName());
        e.setCode(r.getCode());
        e.setDescription(r.getDescription());
        e.setDiscountType(DiscountType.valueOf(r.getDiscountType()));
        e.setDiscountValue(r.getDiscountValue());
        e.setStartDate(r.getStartDate());
        e.setEndDate(r.getEndDate());
        e.setStatus(PromotionStatus.valueOf(r.getStatus()));
    }
}
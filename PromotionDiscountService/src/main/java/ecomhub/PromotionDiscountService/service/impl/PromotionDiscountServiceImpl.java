package ecomhub.PromotionDiscountService.service.impl;

import ecomhub.PromotionDiscountService.dto.request.PromotionDiscountRequest;
import ecomhub.PromotionDiscountService.dto.response.PromotionDiscountResponse;
import ecomhub.PromotionDiscountService.model.PromotionStatus;
import ecomhub.PromotionDiscountService.service.PromotionDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PromotionDiscountServiceImpl implements PromotionDiscountService {

    private final PromotionQueryService queryService;
    private final PromotionCommandService commandService;
    private final PromotionDiscountApplicator applicator;

    @Override
    public Page<PromotionDiscountResponse> getAll(Pageable p) {
        return queryService.getAll(p);
    }

    @Override
    public PromotionDiscountResponse getById(UUID id) {
        return queryService.getById(id);
    }

    @Override
    public PromotionDiscountResponse getByCode(String code) {
        return queryService.getByCode(code);
    }

    @Override
    public List<PromotionDiscountResponse> getActivePromotions() {
        return queryService.getActivePromotions();
    }

    @Override
    public List<PromotionDiscountResponse> getExpiredPromotions() {
        return queryService.getExpiredPromotions();
    }

    @Override
    public List<PromotionDiscountResponse> getUpcomingExpiredPromotions(int days) {
        return queryService.getUpcomingExpiredPromotions(days);
    }

    @Override
    public Page<PromotionDiscountResponse> getByStatus(PromotionStatus status, Pageable p) {
        return queryService.getByStatus(status, p);
    }

    @Override
    public PromotionDiscountResponse create(PromotionDiscountRequest r) {
        return commandService.create(r);
    }

    @Override
    public PromotionDiscountResponse update(UUID id, PromotionDiscountRequest r) {
        return commandService.update(id, r);
    }

    @Override
    public void delete(UUID id) {
        commandService.delete(id);
    }

    @Override
    public PromotionDiscountResponse applyDiscount(UUID id, Double originalPrice) {
        return applicator.applyDiscount(id, originalPrice);
    }
}
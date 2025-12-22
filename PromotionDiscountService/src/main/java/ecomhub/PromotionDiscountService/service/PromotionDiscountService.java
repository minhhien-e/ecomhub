package ecomhub.PromotionDiscountService.service;

import ecomhub.PromotionDiscountService.dto.request.PromotionDiscountRequest;
import ecomhub.PromotionDiscountService.dto.response.PromotionDiscountResponse;
import ecomhub.PromotionDiscountService.model.PromotionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PromotionDiscountService {
    Page<PromotionDiscountResponse> getAll(Pageable p);
    PromotionDiscountResponse getById(UUID id);
    PromotionDiscountResponse getByCode(String code);
    List<PromotionDiscountResponse> getActivePromotions();
    List<PromotionDiscountResponse> getExpiredPromotions();
    List<PromotionDiscountResponse> getUpcomingExpiredPromotions(int days);
    Page<PromotionDiscountResponse> getByStatus(PromotionStatus status, Pageable p);
    PromotionDiscountResponse create(PromotionDiscountRequest r);
    PromotionDiscountResponse update(UUID id, PromotionDiscountRequest r);
    void delete(UUID id);
    PromotionDiscountResponse applyDiscount(UUID id, Double originalPrice);
}
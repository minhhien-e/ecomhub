package ecomhub.PromotionDiscountService.service.impl;

import ecomhub.PromotionDiscountService.dto.response.PromotionDiscountResponse;
import ecomhub.PromotionDiscountService.exception.PromotionNotFoundException;
import ecomhub.PromotionDiscountService.mapper.PromotionDiscountMapper;
import ecomhub.PromotionDiscountService.model.PromotionStatus;
import ecomhub.PromotionDiscountService.repository.PromotionDiscountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PromotionQueryService {

    private final PromotionDiscountRepository repo;
    private final PromotionDiscountMapper mapper;

    @Transactional(readOnly = true)
    public Page<PromotionDiscountResponse> getAll(Pageable p) {
        return repo.findAll(p).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PromotionDiscountResponse getById(UUID id) {
        return mapper.toResponse(repo.findById(id)
                .orElseThrow(() -> new PromotionNotFoundException(id.toString())));
    }

    @Transactional(readOnly = true)
    public PromotionDiscountResponse getByCode(String code) {
        return mapper.toResponse(repo.findByCode(code)
                .orElseThrow(() -> new PromotionNotFoundException("Code: " + code)));
    }

    @Transactional(readOnly = true)
    public List<PromotionDiscountResponse> getActivePromotions() {
        Instant now = Instant.now();
        return repo.findByStatusAndStartDateBeforeAndEndDateAfter(PromotionStatus.ACTIVE, now, now)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PromotionDiscountResponse> getExpiredPromotions() {
        Instant now = Instant.now();
        return repo.findByEndDateBefore(now)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PromotionDiscountResponse> getUpcomingExpiredPromotions(int days) {
        Instant now = Instant.now();
        Instant soon = now.plus(days, ChronoUnit.DAYS);
        return repo.findByEndDateBetween(now, soon)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PromotionDiscountResponse> getByStatus(PromotionStatus status, Pageable p) {
        return repo.findByStatus(status, p).map(mapper::toResponse);
    }
}
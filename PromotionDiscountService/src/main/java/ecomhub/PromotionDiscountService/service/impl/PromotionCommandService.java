package ecomhub.PromotionDiscountService.service.impl;

import ecomhub.PromotionDiscountService.dto.request.PromotionDiscountRequest;
import ecomhub.PromotionDiscountService.dto.response.PromotionDiscountResponse;
import ecomhub.PromotionDiscountService.exception.PromotionNotFoundException;
import ecomhub.PromotionDiscountService.exception.PromotionValidationException;
import ecomhub.PromotionDiscountService.mapper.PromotionDiscountMapper;
import ecomhub.PromotionDiscountService.model.PromotionDiscount;
import ecomhub.PromotionDiscountService.repository.PromotionDiscountRepository;
import ecomhub.PromotionDiscountService.service.MessageQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PromotionCommandService {

    private final PromotionDiscountRepository repo;
    private final PromotionDiscountMapper mapper;
    private final MessageQueueService mq;

    @Transactional
    public PromotionDiscountResponse create(PromotionDiscountRequest r) {
        validate(r, null);
        if (repo.existsByCode(r.getCode())) {
            throw new PromotionValidationException("Mã khuyến mãi '" + r.getCode() + "' đã tồn tại");
        }
        PromotionDiscount e = mapper.toEntity(r);
        PromotionDiscount saved = repo.save(e);
        mq.sendPromotionUpdateMessage(saved.getId(), "CREATED");
        return mapper.toResponse(saved);
    }

    @Transactional
    public PromotionDiscountResponse update(UUID id, PromotionDiscountRequest r) {
        if (!repo.existsById(id)) {
            throw new PromotionNotFoundException(id.toString());
        }
        validate(r, id);
        if (repo.existsByCodeAndIdNot(r.getCode(), id)) {
            throw new PromotionValidationException("Mã khuyến mãi '" + r.getCode() + "' đã tồn tại ở promotion khác");
        }
        PromotionDiscount e = repo.findById(id).orElseThrow();
        mapper.updateEntity(r, e);
        PromotionDiscount saved = repo.save(e);
        mq.sendPromotionUpdateMessage(id, "UPDATED");
        return mapper.toResponse(saved);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new PromotionNotFoundException(id.toString());
        }
        repo.deleteById(id);
        mq.sendPromotionUpdateMessage(id, "DELETED");
    }

    private void validate(PromotionDiscountRequest r, UUID id) {
        if (r.getStartDate().isAfter(r.getEndDate())) {
            throw new PromotionValidationException("Ngày bắt đầu phải trước ngày kết thúc");
        }
        if ("PERCENTAGE".equals(r.getDiscountType()) && (r.getDiscountValue() > 100 || r.getDiscountValue() <= 0)) {
            throw new PromotionValidationException("Phần trăm giảm giá phải từ 1 đến 100");
        }
        if ("FIXED".equals(r.getDiscountType()) && r.getDiscountValue() <= 0) {
            throw new PromotionValidationException("Giá trị giảm cố định phải lớn hơn 0");
        }
        long durationSeconds = r.getEndDate().getEpochSecond() - r.getStartDate().getEpochSecond();
        if (durationSeconds > 31_536_000L) {
            throw new PromotionValidationException("Thời gian khuyến mãi không được vượt quá 1 năm");
        }
    }
}
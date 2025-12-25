package ecomhub.promotiondiscount.controller;

import ecomhub.promotiondiscount.dto.request.PromotionDiscountRequest;
import ecomhub.promotiondiscount.dto.response.ApiResponse;
import ecomhub.promotiondiscount.dto.response.PromotionDiscountResponse;
import ecomhub.promotiondiscount.model.PromotionStatus;
import ecomhub.promotiondiscount.service.PromotionDiscountService;
import ecomhub.promotiondiscount.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//api/v1/promotions
@RestController
@RequestMapping("/api/v1/promotions")
@RequiredArgsConstructor
public class PromotionDiscountController {

    private final PromotionDiscountService service;

    @PreAuthorize("hasAuthority('promotion.read')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PromotionDiscountResponse>>> getAll(
            @PageableDefault(size = 20, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PromotionDiscountResponse> page = service.getAll(pageable);
        return ResponseUtil.ok(page);
    }

    @PreAuthorize("hasAuthority('promotion.read')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PromotionDiscountResponse>> getById(@PathVariable UUID id) {
        PromotionDiscountResponse resp = service.getById(id);
        return ResponseUtil.ok(resp);
    }

    @PreAuthorize("hasAuthority('promotion.read')")
    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<PromotionDiscountResponse>> getByCode(@PathVariable String code) {
        PromotionDiscountResponse resp = service.getByCode(code);
        return ResponseUtil.ok(resp);
    }

    @PreAuthorize("hasAuthority('promotion.read')")
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<PromotionDiscountResponse>>> getActive() {
        List<PromotionDiscountResponse> resp = service.getActivePromotions();
        return ResponseUtil.ok(resp);
    }

    @PreAuthorize("hasAuthority('promotion.read')")
    @GetMapping("/expired")
    public ResponseEntity<ApiResponse<List<PromotionDiscountResponse>>> getExpired() {
        List<PromotionDiscountResponse> resp = service.getExpiredPromotions();
        return ResponseUtil.ok(resp);
    }
    //Các khuyến mãi sắp hết hạn trong X ngày (hiện tại là 7)
    @PreAuthorize("hasAuthority('promotion.read')")
    @GetMapping("/upcoming-expired")
    public ResponseEntity<ApiResponse<List<PromotionDiscountResponse>>> getUpcomingExpired(
            @RequestParam(defaultValue = "7") int days) {  // Mặc định 7 ngày tới
        List<PromotionDiscountResponse> resp = service.getUpcomingExpiredPromotions(days);
        return ResponseUtil.ok(resp);
    }

    @PreAuthorize("hasAuthority('promotion.read')")
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<Page<PromotionDiscountResponse>>> getByStatus(
            @PathVariable String status,
            @PageableDefault(size = 20, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        PromotionStatus promotionStatus = PromotionStatus.valueOf(status.toUpperCase());
        Page<PromotionDiscountResponse> page = service.getByStatus(promotionStatus, pageable);
        return ResponseUtil.ok(page);
    }

    @PreAuthorize("hasAuthority('promotion.write')")
    @PostMapping
    public ResponseEntity<ApiResponse<PromotionDiscountResponse>> create(@RequestBody @Valid PromotionDiscountRequest req) {
        PromotionDiscountResponse resp = service.create(req);
        return ResponseUtil.created(resp);
    }

    @PreAuthorize("hasAuthority('promotion.write')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PromotionDiscountResponse>> update(@PathVariable UUID id,
                                                                         @RequestBody @Valid PromotionDiscountRequest req) {
        PromotionDiscountResponse resp = service.update(id, req);
        return ResponseUtil.ok(resp);
    }

    @PreAuthorize("hasAuthority('promotion.delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseUtil.noContent();
    }
    //Áp dụng khuyến mãi cho giá gốc → trả về giá sau giảm
    @PreAuthorize("hasAuthority('promotion.read')")
    @GetMapping("/{id}/apply")
    public ResponseEntity<ApiResponse<PromotionDiscountResponse>> apply(@PathVariable UUID id,
                                                                        @RequestParam Double originalPrice) {
        PromotionDiscountResponse resp = service.applyDiscount(id, originalPrice);
        return ResponseUtil.ok(resp);
    }
}
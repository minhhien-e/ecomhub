package ecomhub.productservice.service.impl;

import ecomhub.productservice.dto.request.ProductRequest;
import ecomhub.productservice.dto.response.ProductResponse;
import ecomhub.productservice.mapper.ProductMapper;
import ecomhub.productservice.model.ProductReviews;
import ecomhub.productservice.repository.ProductReviewsRepository;
import ecomhub.productservice.service.ProductReviewsService;
import ecomhub.productservice.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductReviewsServiceImpl implements ProductReviewsService {
    private final ProductReviewsRepository productReviewsRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public void saveReviews(UUID productId, List<ProductRequest.ProductReviewDTO> reviews) {
        if (reviews != null && !reviews.isEmpty()) {
            UUID userIdFromToken = SecurityUtils.getCurrentUserId().orElse(null);

            List<ProductReviews> productReviews = reviews.stream()
                    .map(r -> productMapper.toProductReviewEntity(r, userIdFromToken))
                    .toList();

            for (ProductReviews review : productReviews) {
                review.setId(UUID.randomUUID());
                review.setProductId(productId);
                review.setCreatedAt(Instant.now());
            }
            productReviewsRepository.saveAll(productReviews);
            log.info("Saved {} reviews for product ID: {}", productReviews.size(), productId);
        } else {
            log.info("No reviews to save for product ID: {}", productId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse.ProductReviewDTO> getReviewsByProductId(UUID productId) {
        List<ProductReviews> reviews = productReviewsRepository.findByProductId(productId);
        if (reviews.isEmpty()) {
            log.warn("No reviews found for product ID: {}", productId);
        }
        return reviews.stream().map(productMapper::toProductReviewDTO).toList();
    }

    @Override
    @Transactional
    public void deleteByProductId(UUID productId) {
        long before = productReviewsRepository.count();
        productReviewsRepository.deleteByProductId(productId);
        long after = productReviewsRepository.count();
        log.info("Deleted reviews for product ID: {} ({} records)", productId, (before - after));
    }
}
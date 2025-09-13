package ecomhub.productservice.service.impl;

import ecomhub.productservice.dto.request.ProductRequest;
import ecomhub.productservice.dto.response.ProductResponse;
import ecomhub.productservice.mapper.ProductMapper;
import ecomhub.productservice.model.ProductImages;
import ecomhub.productservice.repository.ProductImagesRepository;
import ecomhub.productservice.service.ProductImagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductImagesServiceImpl implements ProductImagesService {
    private final ProductImagesRepository productImagesRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public void saveImages(UUID productId, List<ProductRequest.ProductImageDTO> images) {
        if (images != null && !images.isEmpty()) {
            List<ProductImages> productImages = images.stream()
                    .map(productMapper::toProductImageEntity)
                    .collect(Collectors.toList());

            for (ProductImages image : productImages) {
                image.setId(UUID.randomUUID());
                image.setProductId(productId);
                image.setCreatedAt(Instant.now());
            }
            productImagesRepository.saveAll(productImages);
            log.info("Đã lưu {} hình ảnh cho sản phẩm ID: {}", productImages.size(), productId);
        } else {
            log.info("Không có hình ảnh để lưu cho sản phẩm ID: {}", productId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse.ProductImageDTO> getImagesByProductId(UUID productId) {
        List<ProductImages> images = productImagesRepository.findByProductId(productId);
        if (images.isEmpty()) {
            log.warn("Không tìm thấy hình ảnh cho sản phẩm ID: {}", productId);
        }
        return images.stream().map(productMapper::toProductImageDTO).toList();
    }

    @Override
    @Transactional
    public void deleteByProductId(UUID productId) {
        long before = productImagesRepository.count();
        productImagesRepository.deleteByProductId(productId);
        long after = productImagesRepository.count();
        log.info("Đã xóa hình ảnh của sản phẩm ID: {} ({} bản ghi)", productId, (before - after));
    }
}
package ecomhub.productservice.service.impl;

import ecomhub.productservice.dto.request.ProductRequest;
import ecomhub.productservice.dto.response.ProductResponse;
import ecomhub.productservice.exception.ProductNotFoundException;
import ecomhub.productservice.exception.ProductValidationException;
import ecomhub.productservice.mapper.ProductMapper;
import ecomhub.productservice.model.Product;
import ecomhub.productservice.repository.ProductRepository;
import ecomhub.productservice.service.MessageQueueService;
import ecomhub.productservice.service.ProductImagesService;
import ecomhub.productservice.service.ProductReviewsService;
import ecomhub.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductImagesService productImagesService;
    private final ProductReviewsService productReviewsService;
    private final ProductMapper productMapper;
    private final MessageQueueService messageQueueService;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> productMapper.toResponseDTO(product,
                productImagesService.getImagesByProductId(product.getId()),
                productReviewsService.getReviewsByProductId(product.getId())));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID id) {
        Product product = getProductOrThrow(id);
        return productMapper.toResponseDTO(product,
                productImagesService.getImagesByProductId(id),
                productReviewsService.getReviewsByProductId(id));
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        validateProductRequest(request);
        Product product = productMapper.toEntity(request);
        product.setId(UUID.randomUUID());
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        Product savedProduct = productRepository.save(product);
        saveRelatedData(savedProduct, request);
        return productMapper.toResponseDTO(savedProduct,
                productImagesService.getImagesByProductId(savedProduct.getId()),
                productReviewsService.getReviewsByProductId(savedProduct.getId()));
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest request) {
        validateProductRequest(request);
        Product existingProduct = getProductOrThrow(id);

        productMapper.merge(existingProduct, request);
        existingProduct.setUpdatedAt(Instant.now());

        Product savedProduct = productRepository.save(existingProduct);

        deleteRelatedData(id);
        saveRelatedData(savedProduct, request);

        return productMapper.toResponseDTO(savedProduct,
                productImagesService.getImagesByProductId(savedProduct.getId()),
                productReviewsService.getReviewsByProductId(savedProduct.getId()));
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product");
        }
        deleteRelatedData(id);
        productRepository.deleteById(id);
        log.info("Deleted product ID: {}", id);
    }

    @Override
    @Transactional
    public void processPurchase(UUID variantId, int quantity) {
        productRepository.updateVariantStock(variantId, quantity);
        messageQueueService.sendInventoryUpdateMessage(variantId, quantity);
        log.info("Processed purchase for variant ID: {}", variantId);
    }

    @Override
    @Transactional
    public void reserveStock(UUID variantId, int quantity) {
        productRepository.reserveVariantStock(variantId, quantity);
        log.info("Reserved stock for variant ID: {}", variantId);
    }

    @Override
    @Transactional
    public void unReserveStock(UUID variantId, int quantity) {
        productRepository.unReserveVariantStock(variantId, quantity);
        log.info("Unreserved stock for variant ID: {}", variantId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductsByCategory(UUID categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findByCategoryId(categoryId, pageable);
        return products.map(product -> productMapper.toResponseDTO(product,
                productImagesService.getImagesByProductId(product.getId()),
                productReviewsService.getReviewsByProductId(product.getId())));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse.ProductImageDTO> getProductImages(UUID productId) {
        validateProductExists(productId);
        return productImagesService.getImagesByProductId(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse.ProductReviewDTO> getProductReviews(UUID productId) {
        validateProductExists(productId);
        return productReviewsService.getReviewsByProductId(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse.ProductVariantDTO> getProductVariants(UUID productId) {
        Product product = getProductOrThrow(productId);
        return productMapper.toResponseDTO(product,
                productImagesService.getImagesByProductId(productId),
                productReviewsService.getReviewsByProductId(productId)).getVariants();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> filterProducts(Map<String, String> filters, String logic, Pageable pageable) {
        Page<Product> page = productRepository.findByDynamicFilters(filters, logic, pageable);
        return page.map(product -> productMapper.toResponseDTO(product,
                productImagesService.getImagesByProductId(product.getId()),
                productReviewsService.getReviewsByProductId(product.getId())));
    }

    private Product getProductOrThrow(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product"));
    }

    private void validateProductRequest(ProductRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ProductValidationException("Product name cannot be empty");
        }
        if (request.getPrice() == null || request.getPrice() <= 0) {
            throw new ProductValidationException("Product price must be greater than 0");
        }
        if (request.getCategoryId() == null || request.getCategoryId().isBlank()) {
            throw new ProductValidationException("Category is required");
        }
    }

    private void validateProductExists(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product");
        }
    }

    private void saveRelatedData(Product product, ProductRequest request) {
        productImagesService.saveImages(product.getId(), request.getImages());
        productReviewsService.saveReviews(product.getId(), request.getReviews());
    }

    private void deleteRelatedData(UUID productId) {
        productImagesService.deleteByProductId(productId);
        productReviewsService.deleteByProductId(productId);
    }
}
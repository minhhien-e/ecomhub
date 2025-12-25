package ecomhub.cart.service.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ecomhub.cart.dto.response.ApiResponse;
import ecomhub.cart.exception.custom.CartItemNotFoundException;
import ecomhub.cart.exception.custom.ExternalApiException;
import ecomhub.cart.exception.custom.ServerException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceClient {

    private final RestTemplate restTemplate;

    @Value("${services.product.url:http://localhost:8082/api/product}")
    private String productServiceBaseUrl;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductCheckDto {
        private String variantName;
        private UUID variantId;
        private String note;
    }

    private static final ParameterizedTypeReference<ApiResponse<ProductCheckDto>> TEMP_RESPONSE_TYPE =
            new ParameterizedTypeReference<ApiResponse<ProductCheckDto>>() {};
    @Transactional
    public ProductCheckDto getProductInfo(UUID variantId) {
        if (variantId == null) {
            log.warn("Attempted to get product info with null variantId");
            throw new CartItemNotFoundException(null);
        }

        URI url = UriComponentsBuilder.fromUriString(productServiceBaseUrl)
                .path("/variants/{variantId}")
                .buildAndExpand(variantId)
                .toUri();

        log.info("Calling Product Service at URL: {}", url);

        try {
            ResponseEntity<ApiResponse<ProductCheckDto>> responseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, null, TEMP_RESPONSE_TYPE
            );

            ApiResponse<ProductCheckDto> safeResponse = responseEntity.getBody();

            if (safeResponse == null) {
                log.error("Product Service returned an empty body for variantId: {}", variantId);
                throw new ServerException();
            }

            Integer errorCode = safeResponse.errorCode();
            if (errorCode != null && errorCode != 0) {
                log.warn("Product Service returned an error - StatusCode: {}, ErrorCode: {}, VariantId: {}",
                        safeResponse.statusCode(), errorCode, variantId);

                throw new ExternalApiException(
                        safeResponse.statusCode(),
                        safeResponse.errorCode()
                );
            }

            log.debug("Product Service returned an id successfully for variantId: {}", variantId);
            return safeResponse.data();

        } catch (ExternalApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Critical error communicating with Product Service for variantId {}: {}",
                    variantId, e.getMessage(), e);
            throw new ServerException();
        }
    }
}
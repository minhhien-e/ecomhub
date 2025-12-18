package com.feature.cartservice.service.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.feature.cartservice.dto.response.ApiResponse;
import com.feature.cartservice.exception.custom.ExternalApiException;
import com.feature.cartservice.exception.custom.ServerException;
import com.feature.cartservice.model.CartItem;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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
            new ParameterizedTypeReference<ApiResponse<ProductCheckDto>>() {
            };

    public ApiResponse<CartItem> getVariantDetails(UUID variantId) {
        URI url = UriComponentsBuilder.fromUriString(productServiceBaseUrl)
                .path("/variants/{variantId}")
                .buildAndExpand(variantId)
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity.get(url).build();

        try {
            ResponseEntity<ApiResponse<ProductCheckDto>> responseEntity = restTemplate.exchange(
                    requestEntity,
                    TEMP_RESPONSE_TYPE
            );

            ApiResponse<ProductCheckDto> safeResponse = responseEntity.getBody();

            if (safeResponse == null) {
                throw new ServerException();
            }
            if (safeResponse.errorCode() != 0) {
                throw new ExternalApiException(responseEntity.getBody().statusCode(),responseEntity.getBody().errorCode());
            }

            ProductCheckDto dtoData = safeResponse.data();
            CartItem dummyCartItem = new CartItem();
            dummyCartItem.setVariantId(dtoData.getVariantId());
            //building, wrapper in new object
            return new ApiResponse<>(
                    safeResponse.statusCode(),
                    0,
                    dummyCartItem
            );

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
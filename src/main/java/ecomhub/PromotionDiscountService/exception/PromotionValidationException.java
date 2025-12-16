package ecomhub.PromotionDiscountService.exception;

import lombok.Getter;

@Getter
public class PromotionValidationException extends RuntimeException {

    private final int errorCode;
    public PromotionValidationException(String message) {
        this(message, 602);
    }

    public PromotionValidationException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
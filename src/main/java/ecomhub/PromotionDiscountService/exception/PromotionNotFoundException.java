package ecomhub.PromotionDiscountService.exception;

public class PromotionNotFoundException extends RuntimeException {
    public PromotionNotFoundException(String id) {
        super("Khuyến mãi với ID " + id + " không tìm thấy");
    }
}
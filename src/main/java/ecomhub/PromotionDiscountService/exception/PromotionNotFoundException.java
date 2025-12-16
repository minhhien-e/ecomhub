package ecomhub.PromotionDiscountService.exception;

public class PromotionNotFoundException extends RuntimeException {
    private static final int ERROR_CODE = 601;  // 601: Promotion not found
    public PromotionNotFoundException(String id) {
        super("Khuyến mãi với ID " + id + " không tìm thấy");
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
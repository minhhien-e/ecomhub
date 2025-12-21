package ecomhub.cartservice.cartservice.enums;

public enum ErrorCode {
    // 1100–1119: VALIDATION (Lỗi dữ liệu đầu vào)
    REQUIRED_FIELD_MISSING(1101, "Thiếu trường bắt buộc"),
    INVALID_FORMAT(1102, "Sai định dạng"),
    INVALID_VALUE(1103, "Giá trị không hợp lệ"),

    // 1120–1139: CART (Lỗi liên quan đến giỏ hàng)
    MISSING_CART_ID(1121, "Thiếu Cart ID"),
    CART_NOT_FOUND(1122, "Không tìm thấy giỏ hàng"),
    CART_ITEM_NOT_FOUND(1123, "Không tìm thấy sản phẩm trong giỏ hàng"),

    // 1140–1159: VARIANT & PRODUCT (Lỗi liên quan đến sản phẩm/biến thể)
    MISSING_VARIANT_ID(1141, "Thiếu Variant ID"),
    PRODUCT_SERVICE_REFUSE_VARIANT_ID(1142, "Variant ID không tồn tại"),

    // 1160–1169: CUSTOMER (Lỗi liên quan đến khách hàng)
    MISSING_CUSTOMER_ID(1161, "Thiếu Customer ID"),
    CUSTOMER_NOT_FOUND(1162, "Không tìm thấy người dùng với ID này"),

    // 1170–1179: PRICING & QUANTITY
    INVALID_PRICE(1171, "Giá sản phẩm không hợp lệ"),
    INVALID_QUANTITY(1172, "Số lượng không hợp lệ"),

    // 1180–1189: SERVER / SYSTEM
    INTERNAL_SERVER_ERROR(1181, "Lỗi hệ thống nội bộ"),

    // 1190–1199: OTHERS
    TOO_MANY_REQUESTS(1191, "Quá nhiều yêu cầu cùng lúc");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
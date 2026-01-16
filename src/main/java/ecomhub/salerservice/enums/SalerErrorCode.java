package ecomhub.salerservice.enums;

public enum SalerErrorCode {
    SHOP_NOT_FOUND(800),
    EXTERNAL_SERVICE_ERROR(801),
    INTERNAL_SERVER_ERROR(899);


    private final int code;
    SalerErrorCode(int code){this.code = code;}

    public int getCode() {
        return this.code;
    }
    
}

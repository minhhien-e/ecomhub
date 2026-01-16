package ecomhub.salerservice.exception;

import ecomhub.salerservice.enums.SalerErrorCode;
import lombok.Getter;

@Getter
public class SalerException extends RuntimeException{
    private final SalerErrorCode errorCode;

    public SalerException(SalerErrorCode errorCode){
        super(String.valueOf(errorCode.getCode()));
        this.errorCode = errorCode;
    }
}

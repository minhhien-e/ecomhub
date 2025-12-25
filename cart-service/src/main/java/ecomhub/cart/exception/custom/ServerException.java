package ecomhub.cart.exception.custom;

import ecomhub.cart.enums.ErrorCode;

public class ServerException extends HttpException{
    public ServerException( ) {
        super(ErrorCode.INTERNAL_SERVER_ERROR, "an internal server error occurred");
    }
}

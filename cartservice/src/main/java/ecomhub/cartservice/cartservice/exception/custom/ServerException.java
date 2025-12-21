package ecomhub.cartservice.cartservice.exception.custom;

import ecomhub.cartservice.cartservice.enums.ErrorCode;

public class ServerException extends HttpException{
    public ServerException( ) {
        super(ErrorCode.INTERNAL_SERVER_ERROR, "an internal server error occurred");
    }
}

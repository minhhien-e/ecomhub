package com.feature.cartservice.exception.custom;

import com.feature.cartservice.enums.ErrorCode;

public class ServerException extends HttpException{
    public ServerException( ) {
        super(ErrorCode.INTERNAL_SERVER_ERROR, "an internal server error occurred");
    }
}

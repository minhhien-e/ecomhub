package com.ecomhub.cartservice.api.exception;

import com.ecomhub.cartservice.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCartNotFound(CartNotFoundException ex, WebRequest request) {
        return new ErrorResponse("CartNotFound", ex.getMessage());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleItemNotFound(ItemNotFoundException ex) {
        return new ErrorResponse("ItemNotFound", ex.getMessage());
    }

    @ExceptionHandler(CacheWriteException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleCacheWrite(CacheWriteException ex) {
        return new ErrorResponse("CacheError", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnknown(Exception ex) {
        return new ErrorResponse("InternalError", "Lỗi không xác định xảy ra.");
    }

    public record ErrorResponse(String errorCode, String message) {}
}

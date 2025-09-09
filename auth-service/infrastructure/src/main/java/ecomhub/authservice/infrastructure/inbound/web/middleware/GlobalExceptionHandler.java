package ecomhub.authservice.infrastructure.inbound.web.middleware;

import ecomhub.authservice.common.dto.response.ApiResponse;
import ecomhub.authservice.common.enums.ErrorCode;
import ecomhub.authservice.common.exception.abstracts.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static ecomhub.authservice.infrastructure.inbound.web.utils.RequestHelper.isApiRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public Object handleHttpException(HttpException e, HttpServletRequest request, Model model) {
        if (isApiRequest(request)) {
            var apiResponse = ApiResponse.error(e.getStatusCode(), e.getErrorCode(), e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(apiResponse);
        } else
            return viewResult(e, model);
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest request, Model model) {
        if (isApiRequest(request)) {
            var apiResponse = ApiResponse.error(500, ErrorCode.INTERNAL_SERVER_ERROR.name(), e.getMessage());
            return ResponseEntity.status(500).body(apiResponse);
        } else
            return viewResult(e, model);
    }

    private Object viewResult(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "login";
    }

}


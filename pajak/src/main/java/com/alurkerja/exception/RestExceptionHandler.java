package com.alurkerja.exception;

import com.alurkerja.core.response.CommonRs;
import com.alurkerja.util.ErrorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

@ControllerAdvice
public class RestExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(RestExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<CommonRs> handleException(Exception ex, WebRequest request) {

        if (ex instanceof ResourceNotFoundException || ex instanceof NoSuchElementException)
            return new ResponseEntity<>(new CommonRs(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                    HttpStatus.NOT_FOUND);
        else if (ex instanceof ResourceForbiddenException)
            return new ResponseEntity<>(new CommonRs(HttpStatus.FORBIDDEN.value(), ex.getMessage()),
                    HttpStatus.FORBIDDEN);
        else if (ex instanceof BadRequestException)
            return new ResponseEntity<>(new CommonRs(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                    HttpStatus.BAD_REQUEST);
        else if (ex instanceof ResourceConflictException)
            return new ResponseEntity<>(new CommonRs(HttpStatus.CONFLICT.value(), ex.getMessage()),
                    HttpStatus.CONFLICT);
        else if (ex instanceof UnauthorizedException)
            return new ResponseEntity<>(new CommonRs(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()),
                    HttpStatus.UNAUTHORIZED);
        else if (ex instanceof FormException) {
            FormException err = (FormException) ex;
            return new ResponseEntity<>(new CommonRs(HttpStatus.BAD_REQUEST.value(), err.getMessage(), err.getErrors()),
                    HttpStatus.BAD_REQUEST);
        } else {
            LOGGER.severe(ErrorUtil.getExceptionStacktrace(ex));

            return new ResponseEntity<>(new CommonRs(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error! please contact your administrator."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.alurkerja.core.controller;


import com.alurkerja.core.exception.AlurKerjaException;
import com.alurkerja.core.response.CommonRs;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class BaseController {

    public ResponseEntity<Object> success(Object data) {
        return new ResponseEntity<>(new CommonRs(HttpStatus.OK.value(), "success", data), HttpStatus.OK);
    }

    public ResponseEntity<Object> error(Object data) {
        return new ResponseEntity<>(new CommonRs(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error", data),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> badRequest(Object data) {
        return new ResponseEntity<>(new CommonRs(HttpStatus.BAD_REQUEST.value(), "bad_request", data),
                HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> notFound(Object data) {
        return new ResponseEntity<>(new CommonRs(HttpStatus.NOT_FOUND.value(), "not_found", data),
                HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> forbidden(Object data) {
        return new ResponseEntity<>(new CommonRs(HttpStatus.FORBIDDEN.value(), "forbidden", data),
                HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<Object> success(Object data, String message) {
        return new ResponseEntity<>(new CommonRs(HttpStatus.OK.value(), message, data), HttpStatus.OK);
    }

    public ResponseEntity<Object> badRequest(Object data, String message) {
        return new ResponseEntity<>(new CommonRs(HttpStatus.BAD_REQUEST.value(), message, data),
                HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> forbidden(Object data, String message) {
        return new ResponseEntity<>(new CommonRs(HttpStatus.FORBIDDEN.value(), message, data),
                HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<Object> notFound(Object data, String message) {
        return new ResponseEntity<>(new CommonRs(HttpStatus.NOT_FOUND.value(), message, data),
                HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> error(HttpStatus httpStatus, Object message) {
        return new ResponseEntity<>(new CommonRs(httpStatus.value(), "error", message), httpStatus);
    }

    public ResponseEntity<Resource> okDownload(String filename, String mediaType, byte[] data) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(data.length)
                .contentType(MediaType.parseMediaType(mediaType))
                .body(new ByteArrayResource(data));
    }

    public Pageable pageFromRequest(int page, int size, String sort, Boolean asc) {
        return ObjectUtils.isEmpty(sort) ?
                PageRequest.of(page, size) :
                PageRequest.of(page, size, Sort.by(getSortBy(sort, asc, true)));
    }

    public Sort.Order getSortBy(String sort, Boolean asc, Boolean ignoreCase) {
        if (Boolean.FALSE.equals(ignoreCase)) {
            return Boolean.TRUE.equals(asc) ? Sort.Order.asc(sort) : Sort.Order.desc(sort);
        }
        return Boolean.TRUE.equals(asc) ? Sort.Order.asc(sort).ignoreCase() : Sort.Order.desc(sort).ignoreCase();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<FieldError> fieldErrorList = ex.getBindingResult().getFieldErrors();
        Iterator var4 = fieldErrorList.iterator();

        List<Map<String, String>> messages = new ArrayList<>();
        while(var4.hasNext()) {
            FieldError field = (FieldError)var4.next();
            Map<String ,  String> validatedItem = new HashMap<>();
            validatedItem.put(field.getField() ,  StringUtils.capitalize(field.getField()) +  " " + field.getDefaultMessage());
            messages.add(validatedItem);
        }


        return this.badRequest(messages, HttpStatus.BAD_REQUEST.toString());

    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Object> httpMessageNotReadableException(HttpMessageNotReadableException ex){
        return badRequest(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex){
        return badRequest(ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> missingSerfletRequestParameterException(MissingServletRequestParameterException ex){
        return badRequest(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException ex){
        return badRequest(ex.getMessage());
    }

    @ExceptionHandler({AlurKerjaException.class})
    public ResponseEntity<Object> alurKerjaExceptionHandler(HttpServletRequest request,
                                                            AlurKerjaException ex){

        String text = "";
        try {
            String lang = request.getAttribute("lang").toString();
            ResourceBundle message = ResourceBundle.getBundle("messages", lang.equals("id") ? Locale.ROOT : Locale.ENGLISH);
            text = message.getString(ex.getMessage());
            if (ex.getValues() != null){
                int i = 0;
                for (String value : ex.getValues()) {
                    text = text.replace("{" + i + "}", value);
                    i++;
                }
            }
        }
        catch (Exception e){
            text = ex.getMessage();
        }


        if (ex.getErrorCode() == 400)
            return badRequest(null, text);
        if (ex.getErrorCode() == 404)
            return notFound(null, text);
        return error(HttpStatus.EXPECTATION_FAILED, text);
    }

}

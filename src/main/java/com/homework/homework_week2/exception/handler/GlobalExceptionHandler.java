package com.homework.homework_week2.exception.handler;

import com.homework.homework_week2.exception.errorCode.CommonErrorCode;
import com.homework.homework_week2.exception.errorCode.CustomErrorCode;
import com.homework.homework_week2.exception.errorCode.ErrorCode;
import com.homework.homework_week2.exception.exception.RestApiException;
import com.homework.homework_week2.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // RuntimeException 처리
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleCustomException(RestApiException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    // IllegalArgumentException 에러 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
        log.warn("handleIllegalArgument", e);
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER, e.getMessage());
    }

    // @Valid 어노테이션으로 넘어오는 에러 처리
    @Override
    public ResponseEntity<Object> handleBindException(
            BindException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.warn("handleIllegalArgument", e);
        return handleExceptionInternal(e, CommonErrorCode.INVALID_PARAMETER);
    }

    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("handleHttpRequestMethodNotSupported", e);
        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    // 로그인 관련 에러 처리
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException e) {
        log.warn("AccessDenied", e);
        return handleExceptionInternal(CustomErrorCode.ERROR_USER_NOT_EXISTS, "로그인이 필요합니다.");
    }

    // 대부분의 에러 처리
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(Exception ex) {
        log.warn("handleAllException", ex);
        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    // RuntimeException과 대부분의 에러 처리 메세지를 보내기 위한 메소드
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    // 코드 가독성을 위해 에러 처리 메세지를 만드는 메소드 분리
    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

    // IllegalArgumentException 에러 처리 메세지를 보내기 위한 메소드
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    // 코드 가독성을 위해 에러 처리 메세지를 만드는 메소드 분리
    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }

    // @Valid 어노테이션으로 넘어오는 에러 처리 메세지를 보내기 위한 메소드
    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(e, errorCode));
    }

    // 코드 가독성을 위해 에러 처리 메세지를 만드는 메소드 분리
    private ErrorResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
        List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .errors(validationErrorList)
                .build();
    }
}

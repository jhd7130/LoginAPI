package me.flab.loginjoinAPI.common;

import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.common.exception.CustomException;
import me.flab.loginjoinAPI.common.exception.ErrorResponse;
import me.flab.loginjoinAPI.data.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

import static me.flab.loginjoinAPI.common.exception.ErrorCode.DUPLICATE_RESOURCE;

@Slf4j
@ResponseBody // 쓰는 이유찾기
@ControllerAdvice //전역에러처리를 위한 Annotation
// @RestControllerAdvice : ResponseBody + ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 발생가능한 Exception을 정의해 놓는다.(기존에 발생 가능한 예외를 .java파일로 만들기
    //사용자정의 에러를 제외한 모든 에러를 태우기
    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleServerError(Exception e) {
        log.error("handleData Exception throw Exception : {}", DUPLICATE_RESOURCE);
        return ErrorResponse.toResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());

    }

}

package me.flab.loginjoinAPI.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    /* Unchecked Exception 처리에 활용  */
    private final ErrorCode errorCode;
}

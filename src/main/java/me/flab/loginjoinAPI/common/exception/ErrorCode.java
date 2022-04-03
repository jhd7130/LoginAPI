package me.flab.loginjoinAPI.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //서버에서 발생할 수 있는 모든 예외처리
    PASSWORD_NOT_FOUND(BAD_REQUEST,"비밀번호가 다릅니다."),
    INVALID_TOKEN(BAD_REQUEST,"토큰이 유효하지 않습니다."),

    MEMBER_NOT_FOUND(NOT_FOUND,"해당 유저 정보를 찾을 수 없습니다."),
    TOKEN_NOT_FOUND(NOT_FOUND,"로그아웃된 사용자 입니다."),
    DUPLICATE_EMAIL(CONFLICT,"이메일이 존재합니다."),
    DUPLICATE_RESOURCE(CONFLICT,"데이터가 이미 존재합니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;

}

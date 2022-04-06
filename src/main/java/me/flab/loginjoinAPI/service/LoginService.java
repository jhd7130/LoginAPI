package me.flab.loginjoinAPI.service;

import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.common.exception.CustomException;
import me.flab.loginjoinAPI.common.exception.ErrorCode;
import me.flab.loginjoinAPI.data.Response;
import me.flab.loginjoinAPI.data.dto.Member;
import me.flab.loginjoinAPI.data.dto.SignInRequest;
import me.flab.loginjoinAPI.mapper.MemberMapper;
import me.flab.loginjoinAPI.security.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.List;

import static me.flab.loginjoinAPI.common.exception.ErrorCode.PASSWORD_NOT_FOUND;

@Slf4j
@Service
public class LoginService {
    private MemberMapper memberMapper;
    private JwtProvider jwtProvider;


    public LoginService(MemberMapper memberMapper,JwtProvider jwtProvider){
        this.memberMapper = memberMapper;
        this.jwtProvider = jwtProvider;
    }

    public int checkLogin(SignInRequest signInRequest) {
        // member서비스에서 필요한 메서드들을 가져와서 사용하여 줄이기 반환값은 response객체 사용하기
        SignInRequest member = memberMapper.loginCheck(signInRequest);
        log.info("LoginService :::: member:{}",member);

        //이메일 중복 체크 && 비밀번호 중복체크까지 완료된 상황(쿼리에서 그렇게 받아서 던져줌

        if(member == null){
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

       // String token = jwtProvider.createToken(email,pw);

        //문제없이 넘어갈 경우 1을 http header에 일단 token을 담는다
        return 1;
    }

    public Response login(SignInRequest signInRequest, HttpServletResponse response){
        //여기로 들어왔다는 이야기는 로그인 페이지에서 로그인을 시도했다는 의미 따라서 토큰 생성
        String token = jwtProvider.createToken(signInRequest.getEmail(), signInRequest.getPw());


        //이메일과 비밀번호 유효성 체크 필요 -- 맞지않으면 바로 예외 발생 시키기
        // 비밀번호 체크랑 아이디체크해서 예외를 따로 발생시키는 것도 구현해보기
        if(!memberMapper.vaildInfo(signInRequest)){
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        //로그인에 아무 문제가 없을 경우 헤더에 담아서 넘겨준다.
        response.setHeader("Authorization",token);
        return Response.builder().status(200).message("token 생성 성공").data(token).build();
    }

}

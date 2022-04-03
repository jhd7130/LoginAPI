package me.flab.loginjoinAPI.service;

import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.common.exception.CustomException;
import me.flab.loginjoinAPI.data.dto.Member;
import me.flab.loginjoinAPI.data.dto.SignInRequest;
import me.flab.loginjoinAPI.mapper.MemberMapper;
import me.flab.loginjoinAPI.security.JwtProvider;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.List;

import static me.flab.loginjoinAPI.common.exception.ErrorCode.MEMBER_NOT_FOUND;
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

    public int checkLogin(String email, String pw, HttpServletResponse rs) {
        SignInRequest member = memberMapper.getMember(email);
        log.info("LoginService :::: member:{}",member);

        //이메일 중복 체크
        if(member == null){
            throw new CustomException(MEMBER_NOT_FOUND);
        }
        //패스워드 체크
        if(!member.getPw().equals(pw)){
            throw new CustomException(PASSWORD_NOT_FOUND);
        }

        String token = jwtProvider.createToken(email,pw);

        return 1;
    }

}

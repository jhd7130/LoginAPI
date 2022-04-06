package me.flab.loginjoinAPI.controller;


import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.data.Response;
import me.flab.loginjoinAPI.data.dto.SignInRequest;
import me.flab.loginjoinAPI.security.JwtProvider;
import me.flab.loginjoinAPI.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class LoginController {

    private LoginService loginService;
    private JwtProvider jwtProvider;

    public LoginController(LoginService loginService, JwtProvider jwtProvider){
        this.loginService = loginService;
        this.jwtProvider = jwtProvider;
    }
    /*로그인페이지 열기 -- 필요 없을 수도? */
    @GetMapping("/loginPage")
    public String loginPage(){  return"로그인페이지";}

    // login Post Request
    // 로그인을 시도에서 걸리는 부분 인터셉터에서 처리되는 부분
    // 성공 메세지와 토큰 반환
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody SignInRequest signin, HttpServletResponse response){
        //담아서 반환토큰을 헤더에 담아서 반환
        // 문제는 토큰을 어디에 담아서 보낼지인데 토큰 정보를 json형식으로 담아서 보내면 프론트에서 담아두는 것인지 == 일단 json형태로 token정보를 반환 4월 6일 수요일
        return new ResponseEntity(loginService.login(signin,response),HttpStatus.OK);
    }


    // 회원가입도 하나의 컨트롤러에서 관리하면 편할 듯하다.
    @PostMapping("/signup")
    public ResponseEntity signup(HttpServletRequest request){
        return new ResponseEntity(HttpStatus.OK);
    }


}

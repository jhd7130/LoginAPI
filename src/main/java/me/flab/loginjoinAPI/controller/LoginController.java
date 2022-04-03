package me.flab.loginjoinAPI.controller;

import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.data.Response;
import me.flab.loginjoinAPI.data.dto.Member;
import me.flab.loginjoinAPI.data.dto.SignInRequest;
import me.flab.loginjoinAPI.security.JwtProvider;
import me.flab.loginjoinAPI.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class LoginController {


    private LoginService loginService;
    private JwtProvider jwtProvider;

    public LoginController(LoginService loginService, JwtProvider jwtProvider){
        this.loginService  = loginService;
        this.jwtProvider = jwtProvider;
    }
    /*
    로그인 페이지 들어오기 전 prehandler로 token 검사
     */
    @GetMapping("/get/login")
    public Response getLoginPage(){
        return Response.builder().message("로그인페이지로 이동").status(200).build();
    }

    @PostMapping("/token")
    public Response getToken(@RequestBody SignInRequest signInRequest, HttpServletResponse hsr){
        String email = signInRequest.getEmail();
        String pw = signInRequest.getPw();

        if(loginService.checkLogin(email,pw,hsr) == 1 ){
           String token = jwtProvider.createToken(email,pw);
        }

        return Response.builder().message("로그인페이지로 이동").status(200).build();
    }
}

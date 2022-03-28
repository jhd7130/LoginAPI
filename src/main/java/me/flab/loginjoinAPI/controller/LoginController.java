package me.flab.loginjoinAPI.controller;

import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.data.Response;
import me.flab.loginjoinAPI.security.JwtProvider;
import me.flab.loginjoinAPI.service.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RestController
public class LoginController {

    private JwtProvider jwtProvider;
    private LoginService loginService;
    private Response response;

    public LoginController(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }


    /*
    로그인 페이지 들어오기 전 prehandler로 token 검사
     */
    @GetMapping("/get/login")
    public Response getLoginPage(){

        return Response.builder().message("Success").status(200).build();
    }


    //로그인시 토큰 없을 때 토큰 발행
    @PostMapping("/post/token")
    public Response getToken(@RequestBody String email, String pw){
        //로그인 정보 체크
        HashMap<String,Object> map = new HashMap<String,Object>();
        if(!loginService.checkLogin(email,pw)){
            //로그인실패 반환 객체 던지기
            return Response.builder().message("Fail").status(400).build();
        }

        map.put("token",jwtProvider.createToken(email,pw));
        return Response.builder().data(map).build();
    }
}

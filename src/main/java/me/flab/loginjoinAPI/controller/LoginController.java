package me.flab.loginjoinAPI.controller;

import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.service.ResponseProviderService;
import me.flab.loginjoinAPI.data.SingleResponse;
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
    private ResponseProviderService responseProviderService;

    public LoginController(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }


    /*
    로그인 페이지 들어오기 전 prehandler로 token 검사
     */
    @GetMapping("/get/login")
    public SingleResponse<String> getLoginPage(){
        return responseProviderService.getSingleResponse("로그인 실패");
    }


    //로그인시 토큰 없을 때 토큰 발행
    @PostMapping("/post/token")
    public SingleResponse<HashMap<String,String>> getToken(@RequestBody String email, String pw){
        //로그인 정보 체크
        HashMap<String,String> map = new HashMap<String,String>();
        if(!loginService.checkLogin(email,pw)){
            //로그인실패 반환 객체 던지기
            return responseProviderService.getSingleResponse(null);
        }

        map.put("token",jwtProvider.createToken(email,pw));
        return responseProviderService.getSingleResponse(map);
    }
}

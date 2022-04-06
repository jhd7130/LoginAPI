package me.flab.loginjoinAPI.handler;


import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.common.exception.CustomException;
import me.flab.loginjoinAPI.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static me.flab.loginjoinAPI.common.exception.ErrorCode.INVALID_TOKEN;


@Slf4j
@ResponseBody
public class HttpHandler implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    //생성자로 의존성 주입할 경우 실패하고 어노테이션(resource, autowired)를 이용해야함

    @Autowired
    private JwtProvider jwtProvider;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String token = request.getHeader(AUTHORIZATION_HEADER);
        log.info("[Prehandler] :::::  Requset Token: {}",token);

        /* 모든 페이지 접속이 인터셉터를 탄다. 토큰이 존재하면 로그인 정보를 가지고 접속이 가능하다.*/
        if(token != null && jwtProvider.validateToken(token)){
            log.info("[Prehandler] :::::token 통과");
            return true;
        }else{
            /*토큰이 유효하지 않을 경우 새로운 로그인 페이지로 가야함.
             exception을 던질지(다른 조치없이 화면에 알릴지 여기서 여기서 바로 처리할지)
             바로 로그인 페이지로 리다이렉션을 시켜줄지 -- 문제는 로그인 정보를 어떻게 리다이렉션과 함께 보낼 것인지 이다.
            이렇게 반환하면 프론트에서 알아서 로그인 페이지 연결*/
            //response.sendRedirect("/loginPage");
            throw new CustomException(INVALID_TOKEN);

        }

    }

}

package me.flab.loginjoinAPI.handler;


import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.common.exception.CustomException;
import me.flab.loginjoinAPI.security.JwtProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static me.flab.loginjoinAPI.common.exception.ErrorCode.INVALID_TOKEN;


@Slf4j
@ResponseBody
@Component
public class HttpHandler implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String token = request.getHeader(AUTHORIZATION_HEADER);
        String uri = request.getRequestURI();
        log.info("Generate Token, Requset URI: {}",uri);

        if(token != null && jwtProvider.validateToken(token)){
            log.info("token 통과");
            return true;
        }else{
            if(uri.contains("/token")){
                log.info("Generate Token, Requset URI: {}",uri);
                return true;
            }
            throw new CustomException(INVALID_TOKEN);
        }

    }




}

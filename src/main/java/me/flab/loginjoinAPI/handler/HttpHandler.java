package me.flab.loginjoinAPI.handler;

import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class HttpHandler implements HandlerInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REDIRECT_URI = "/get/token";

    private JwtProvider jwtProvider;

    public HttpHandler(){};
    public HttpHandler(JwtProvider jwtProvider){
        this.jwtProvider=jwtProvider;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String token = request.getHeader(AUTHORIZATION_HEADER);
        LOGGER.info("[HttpHandler] Request :: Token = {}",token);
        System.out.println("this is interceptor");
        if(token == null){
            response.sendRedirect("/get/token");
            return false;
        }

        if(jwtProvider.validateToken(token)){
            response.sendRedirect("메인페이지주소");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("Interceptor > postHandle");
        // prehandler
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
        log.debug("Interceptor > afterCompletion" );
    }


}

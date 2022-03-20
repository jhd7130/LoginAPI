package me.flab.loginjoinAPI.handler;


import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.security.JwtProvider;
import me.flab.loginjoinAPI.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class HttpHandler implements HandlerInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REDIRECT_URI = "/get/token";

    private JwtProvider jwtProvider;
    private LoginService loginService;

    public HttpHandler(){};
    public HttpHandler(JwtProvider jwtProvider, LoginService loginService){
        this.jwtProvider=jwtProvider;
        this.loginService=loginService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String token = request.getHeader(AUTHORIZATION_HEADER);
        String email = request.getParameter("email");
        String pw = request.getParameter("pw");

        LOGGER.info("[HttpHandler] Request :: Token = {}",token);
        LOGGER.info("[HttpHandler] Request :: Email = {}",email);
        LOGGER.info("[HttpHandler] Request :: Password = {}",pw);


        if(token == null){

           if(email.equals("")){
               response.sendRedirect("/get/loginPage");
               return false;
           }

           /*
           아이디 비밀번호 체크 후 토큰 발행
           토큰 발행후 response헤더에 담아서 반환
           */

            log.info("true and false email={}", email);
            log.info("true and false pw ={}", pw);


           // 이게 왜 null이 나오는지 모르겠다. 좀 더 봐야겠다.
           if(loginService.checkLogin(email,pw)){
              token =  jwtProvider.createToken(email,pw);
              response.setHeader(AUTHORIZATION_HEADER,token);
           }

           //여기서 어떻게 반환객체를 따로 만들어서 반환해주고 싶은데 아직 모르겠다.
            response.sendRedirect("/get/loginPage");
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
        log.info("Interceptor > postHandle");
        // prehandler
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
        log.info("Interceptor > afterCompletion" );
    }


}

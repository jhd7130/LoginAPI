package me.flab.loginjoinAPI.configuration;

import lombok.extern.slf4j.Slf4j;
import me.flab.loginjoinAPI.handler.HttpHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Slf4j
@Configuration
public class CustomConfig extends WebMvcConfigurationSupport {
    /*
    * 인터셉터를 추가할때 new 연산자를 이용하여 추가하면 Spring Container가 빈으로 등록 불가 -- 해당 객체 내부에서는 spring의 의존성 자동 주입이 불가능
    * Configuration에서 사용할 인터셉터를 bean으로 추가 후 해당 메서드를 사용하여 객체를 사용
    * 따라서 hadler클래스는 따로 compnent 어노테이션을 지정할 필요가 없음
    * 인터셉터에서 타 객체를 사용할 일이 생기면 이런 식으로 사용해야함
    * */
    @Bean
    public HttpHandler httpHandler() {
        return new HttpHandler();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(httpHandler())
                .addPathPatterns("/**")
                .excludePathPatterns("/loginPage","/login");
        // 어떤 url로 들어왔을때 handler를 통과 시킬 것인지 어떤 것은 제외할 것인지 등록
    }




}

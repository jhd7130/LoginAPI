package me.flab.loginjoinAPI.configuration;

import me.flab.loginjoinAPI.handler.HttpHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class CustomConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new HttpHandler())
                .addPathPatterns("/get/login")
                .excludePathPatterns("/post/token");
        // 어떤 url로 들어왔을때 handler를 통과 시킬 것인지 어떤 것은 제외할 것인지 등록
    }


}

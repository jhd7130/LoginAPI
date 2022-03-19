package me.flab.loginjoinAPI.configuration;

import me.flab.loginjoinAPI.handler.HttpHandler;
import me.flab.loginjoinAPI.security.JwtProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class CustomConfigurer extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new HttpHandler())
                .addPathPatterns("/get/**")
                .excludePathPatterns("/get/token");
    }


}

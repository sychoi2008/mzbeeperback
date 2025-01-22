package com.example.mzbeeperback.global.config;

import com.example.mzbeeperback.global.jwt.interceptor.JwtTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer { // 스프링이 제공하는 대부분의 기능 이외에 기능을 확장하는 것 = WebMvcOnfigurer를 상속받기

    //@RequiredArgsConstructor가 반드시 필요함
    private final JwtTokenInterceptor jwtTokenInterceptor;

    //api도 path에 넣어줘야 한다.
    private static final String[] EXCLUDE_PATHS = {
      "/mzbeeper", "/mzbeeper/signup", "/mzbeeper/welcome", "/mzbeeper/regist", "/mzbeeper/login", "/mzbeeper/refresh", "/mzbeeper/dict**"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) { // 코스 맵핑을 더 추가하기 
        registry.addMapping("/**")
                .exposedHeaders("*")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }


    // https://velog.io/@sa1341/JWT-%ED%86%A0%ED%81%B0-%EC%9D%B8%EC%A6%9D
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATHS);
    }


}

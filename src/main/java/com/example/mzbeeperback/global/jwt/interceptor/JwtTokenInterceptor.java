package com.example.mzbeeperback.global.jwt.interceptor;

import com.example.mzbeeperback.global.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {
    // 컨트롤러 요청 uri로 접근 허용 유무를 판단함 => HandlerInterceptor class의 역할
    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final String token = request.getHeader("accessToken");

        if(token!=null && jwtService.isValid(token)) {
            System.out.println("Token : "+ token);
            System.out.println("True");
            return true;
        }else {
            return false;
//            throw new UnauthorizedException();
        }
    }
}


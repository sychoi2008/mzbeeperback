package com.example.mzbeeperback.global.jwt.interceptor;

import com.example.mzbeeperback.global.jwt.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {
    // 컨트롤러 요청 uri로 접근 허용 유무를 판단함 => HandlerInterceptor class의 역할
    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null) return false;

        // 정상토큰
        // Bearer 뒤에 있는 JWT 토큰을 parsing 하기
        String accessToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 값만 추출

        try { // 유효한 accessToken과 refreshToken 이라면 이 곳에서 처리가 된다
            return jwtService.isValid(accessToken);
        } catch (ExpiredJwtException e) { // jwt 토큰이 만료되었을 때
            sendErrorResponse(response, "토큰 만료 헤헤");
            return false;
        } catch (Exception e) { // 그 이외의 모든 예외를 처리한다 : ex) 이 서버에서 만든 JWT가 아닌데? 등등
            sendErrorResponse(response, "Invalid Token");
            return false;
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}


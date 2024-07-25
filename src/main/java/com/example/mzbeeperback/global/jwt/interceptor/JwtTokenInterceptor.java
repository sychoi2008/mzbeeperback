package com.example.mzbeeperback.global.jwt.interceptor;

import com.example.mzbeeperback.global.jwt.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {
    // 컨트롤러 요청 uri로 접근 허용 유무를 판단함 => HandlerInterceptor class의 역할
    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

//        final String accessToken = request.getHeader("accessToken");
//        final String refreshToken = request.getHeader("refreshToken");
//
//        System.out.println("request.getHeader() : "+ accessToken);
//        //bearer를 처리해줘야할듯?
        final String authorizationHeader = request.getHeader("Authorization");
        String accessToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 값만 추출
            System.out.println("JwtInterceptor : "+ accessToken);
        }

        try { // 유효한 accessToken과 refreshToken 이라면 이 곳에서 처리가 된다
            if(accessToken!=null && jwtService.isValid(accessToken)) { // 엑서스 토큰이 유효함 -> 거의 대부분의 요청이 jwt가 유효하다면 여기서 걸러질 것
                System.out.println("Token : "+ accessToken);
                System.out.println("True");
                return true;
            }
//            else if(refreshToken != null && jwtService.isRefreshTokenValid(refreshToken)){ // 리프레시 토큰이 유효한지 체크 -> 재발급의 과정
//                String newAcessToken = jwtService.generateNewAccessToken(refreshToken);
//                response.setHeader("accessToken", newAcessToken);
//                return true;
//            }
            else { // accesstoken이 이상할 때 (ex : 토큰이 없을 때)
                sendErrorResponse(response, "Invalid or missing token");
                return false;
            }
        } catch (ExpiredJwtException e) { // jwt 토큰이 만료되었을 때
            sendErrorResponse(response, "Token expired");
            return false;
        } catch (Exception e) {
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


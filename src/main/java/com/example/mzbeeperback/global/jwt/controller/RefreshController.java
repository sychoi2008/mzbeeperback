package com.example.mzbeeperback.global.jwt.controller;

import com.example.mzbeeperback.global.jwt.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RefreshController {

    private final JwtService jwtService;

    @PostMapping("/mzbeeper/refresh")
    public ResponseEntity<String> refreshAccessToken(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        log.info("refreshToken={}", refreshToken);

        if (refreshToken == null || !jwtService.isValid(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token이 만료되었거나 유효하지 않음");
        }

        //새로운 Access Token 발급
        String newAccessToken = jwtService.createToken(
                jwtService.parseUserId(refreshToken),
                jwtService.parseBeepNum(refreshToken),
                600000L
        );

        //새로운 Refresh Token 발급 및 쿠키 재설정
        String newRefreshToken = jwtService.createToken(
                jwtService.parseUserId(refreshToken),
                jwtService.parseBeepNum(refreshToken),
                86400000L
        );

        Cookie refreshTokenCookie = new Cookie("refreshToken", newRefreshToken);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 7일 유지

        response.addCookie(refreshTokenCookie);

        HttpHeaders headers = new HttpHeaders();
        headers.set("accessToken", newAccessToken);

        return ResponseEntity.ok().headers(headers).body("새로운 token 발행 완료");
    }

}

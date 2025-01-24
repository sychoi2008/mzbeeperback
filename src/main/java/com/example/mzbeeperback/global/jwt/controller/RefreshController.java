package com.example.mzbeeperback.global.jwt.controller;

import com.example.mzbeeperback.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RefreshController {

    private final JwtService jwtService;

    @PostMapping("/mzbeeper/refresh")
    public ResponseEntity<String> refreshAccessToken(@RequestHeader("Authorization") String refreshTokenHeader) {
        log.info("refreshToken={}", refreshTokenHeader);
        String oldRefreshToken = refreshTokenHeader.replace("Bearer ", "");
        log.info("parsing refresh={}", oldRefreshToken);

        if(jwtService.isValid(oldRefreshToken)) {
            String newAccessToken = jwtService.createToken(jwtService.parseUserId(oldRefreshToken),
                    jwtService.parseBeepNum(oldRefreshToken), 600000L);
            String newRefreshToken = jwtService.createToken(jwtService.parseUserId(oldRefreshToken),
                    jwtService.parseBeepNum(oldRefreshToken), 86400000L);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("accessToken", newAccessToken);
            responseHeaders.set("refreshToken", newRefreshToken);
            return ResponseEntity.ok().headers(responseHeaders).body("새로운 token들 발행 ");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("refresh token이 만료되거나 유효하지 않음");
        }
    }

}

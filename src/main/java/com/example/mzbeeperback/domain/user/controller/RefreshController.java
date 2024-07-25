package com.example.mzbeeperback.domain.user.controller;

import com.example.mzbeeperback.global.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RefreshController {

    private JwtService jwtService;

    @Autowired
    public RefreshController (JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/mzbeeper/refresh")
    public ResponseEntity<Map<String, String>> refreshAccessToken(@RequestHeader("Authorization") String refreshTokenHeader) {
        String refreshToken = refreshTokenHeader.replace("Bearer ", "");

        if(jwtService.isRefreshTokenValid(refreshToken)) {
            String newAccessToken = jwtService.generateNewAccessToken(refreshToken);
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid refresh token"));
        }
    }

}

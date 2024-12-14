package com.example.mzbeeperback.domain.user.controller;

import com.example.mzbeeperback.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RefreshController {

    private final JwtService jwtService;

    @PostMapping("/mzbeeper/refresh")
    public ResponseEntity<Map<String, String>> refreshAccessToken(@RequestHeader("Authorization") String refreshTokenHeader) {
        String refreshToken = refreshTokenHeader.replace("Bearer ", "");

        if(jwtService.isValid(refreshToken)) {
            String newAccessToken = jwtService.generateNewAccessToken(refreshToken);
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                    .header("location", "/mzbeeper")
                    .body(Collections.singletonMap("error", "Invalid refresh token"));
        }
    }

}

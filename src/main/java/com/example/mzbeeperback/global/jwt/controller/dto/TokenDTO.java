package com.example.mzbeeperback.global.jwt.controller.dto;

import lombok.*;

@Getter
@Setter
@Data
public class TokenDTO {
    String accessToken;
    String refreshToken;

    public TokenDTO(String access, String refresh) {
        accessToken = access;
        refreshToken = refresh;
    }
}

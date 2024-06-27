package com.example.mzbeeperback.global.jwt.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenDTO {
    String accessToken;
    String refreshToken;
}

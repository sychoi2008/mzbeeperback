package com.example.mzbeeperback.global.jwt.service;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtService {

    private final SecretKey secretKey;
    private JwtService(@Value("${jwt.secret}") String secret) {
        // String 으로 받은 키를 객체 타입으로 암호화하기 위한 작업
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8)
                , Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // Token이 유효한지 판별
    public Boolean isValid(String jwt) {
//        byte [] secret = SECRET_KEY.getBytes();
//        Key key = Keys.hmacShaKeyFor(secret);
//            // 파싱하기만 해도 만료되었는지 그리고 이 서버에서 발급한 jwt인지를 확인하고 아니라면 예외를 던진다
//            Jws<Claims> claims = Jwts.parser()
//                    .verifyWith((SecretKey) key) // 서명했던 key
//                    .build() // 파싱한다
//                    .parseSignedClaims(jwt); // 서명 검증
//
//            // 여기까지 내려왔다는 것은 만료되지 않았다는 것
//            return true;
        return !Jwts.parser()
                .verifyWith(secretKey) // 서명 검증(이 서버에서 만든 토큰이니?) -> 아니라면 JwtException 발생
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }


    public String createToken(String userId, int beepNum, Long expiredMs) {

        // https://passionfruit200.tistory.com/463
        return Jwts.builder()
                .claim("user_id", userId)
                .claim("beep_num", beepNum)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();

    }



    // 보내는 유저의 전화번호를 JWT에서 뽑아내는 메소드 필요
    public int parseBeepNum(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .get("beep_num", Integer.class);
    }

    // 보내는 유저의 전화번호를 JWT에서 뽑아내는 메소드 필요
    public String parseUserId(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .get("user_id", String.class);
    }


}

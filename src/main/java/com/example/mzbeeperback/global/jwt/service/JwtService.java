package com.example.mzbeeperback.global.jwt.service;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    @Value("${jwt.secret}")
    private String SECRET_KEY;


    // Token이 유효한지 판별
    public boolean isValid(String jwt) {
        byte [] secret = SECRET_KEY.getBytes();
        Key key = Keys.hmacShaKeyFor(secret);

        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(jwt);
            System.out.println("claims : " + claims);
            System.out.println("Id : " + claims.getPayload().get("id"));
            return true;
        } catch (ExpiredJwtException e) {
            //e.printStackTrace();
//            log.error("Token is expired");
//            return false;
            log.error("Token is expired");
            //throw e;
            return false;
        } catch (JwtException e) {
//            e.printStackTrace();
//            return false;
            log.error("JWTException");
            //throw e;
            return false;
        }

    }


    public String crateAccessToken(String userId, int beepNum) {
        byte [] secret = SECRET_KEY.getBytes();
        Key key = Keys.hmacShaKeyFor(secret);
        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");


        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", userId);
        //삐삐 번호도 담길 것을 고려해보기
        claims.put("beep_num", beepNum);


        Date now = new Date();

        //1시간
        long accessTokenValidMilliSecond = 60 * 60 * 1000L;

        // 1분 -> 원래는 1시간인데 테스트용으로 1분을 가져옴
        //long accessTokenValidMilliSecond = 60 * 1000L;

        // https://passionfruit200.tistory.com/463
        return Jwts.builder()
                .header().empty().add(headerMap).and()
                .claims().empty().add(claims).and()
                .subject(ACCESS_TOKEN_SUBJECT)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + accessTokenValidMilliSecond))
                .signWith(key)
                .compact();

    }


    public String createRefreshToken(String userId, int beepNum) {
        byte [] secret = SECRET_KEY.getBytes();
        Key key = Keys.hmacShaKeyFor(secret);
        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", userId);
        claims.put("beep_num", beepNum);

        Date now = new Date();

        // 14일
        long refreshTokenValidMilliSecond = 14 * 24 * 60 * 60 * 1000L;

        return Jwts.builder()
                .header().empty().add(headerMap).and()
                .claims().empty().add(claims).and()
                .subject(REFRESH_TOKEN_SUBJECT)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + refreshTokenValidMilliSecond))
                .signWith(key)
                .compact();

    }

    // refresh token 받아서 유효한지 확인하는 메소드
    public boolean isRefreshTokenValid(String refreshToken) {
        return isValid(refreshToken);
    }

    // refresh token 인증 완료 후, 새로운 accessToken 발행하는 메소드
    public String generateNewAccessToken(String refreshToken) {
        byte [] secret = SECRET_KEY.getBytes();
        Key key = Keys.hmacShaKeyFor(secret);

        Jws<Claims> claims = Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(refreshToken);

        String id = (String) claims.getPayload().get("id");
        int beepnum = (int) claims.getPayload().get("beep_num");

        String newAccessToken = crateAccessToken(id, beepnum);
        return newAccessToken;

    }

    // 보내는 유저의 전화번호를 JWT에서 뽑아내는 메소드 필요
    public int parseBeepNum(String jwt) {
        byte [] secret = SECRET_KEY.getBytes();
        Key key = Keys.hmacShaKeyFor(secret);
        int numb = 0;
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(jwt);
            System.out.println("parseBeepNum is working: " + claims.getPayload().get("beep_num"));
            numb = (int) claims.getPayload().get("beep_num");
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (JwtException e) {
            e.printStackTrace();
        }
        return numb;
    }


}

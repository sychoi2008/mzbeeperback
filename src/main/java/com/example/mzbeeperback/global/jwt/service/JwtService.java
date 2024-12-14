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
//@RequiredArgsConstructor
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
            // 파싱하기만 해도 만료되었는지 그리고 이 서버에서 발급한 jwt인지를 확인하고 아니라면 예외를 던진다
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith((SecretKey) key) // 서명했던 key
                    .build() // 파싱한다
                    .parseSignedClaims(jwt); // 서명 검증

            // 여기까지 내려왔다는 것은 만료되지 않았다는 것
            return true;
    }


    public String crateAccessToken(String userId, int beepNum) {
        byte [] secret = SECRET_KEY.getBytes();
        Key key = Keys.hmacShaKeyFor(secret);
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");


        Map<String, Object> claims = new HashMap<>();
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

        // 테스트용 1분
        //long refreshTokenValidMilliSecond = 60 * 1000L;

        return Jwts.builder()
                .header().empty().add(headerMap).and()
                .claims().empty().add(claims).and()
                .subject(REFRESH_TOKEN_SUBJECT)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + refreshTokenValidMilliSecond))
                .signWith(key)
                .compact();

    }



    // refresh token 인증 완료 후, 새로운 accessToken 발행하는 메소드
    public String generateNewAccessToken(String refreshToken) {
        byte [] secret = SECRET_KEY.getBytes();
        Key key = Keys.hmacShaKeyFor(secret);

        // refresh token 해체
        Jws<Claims> claims = Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(refreshToken);

        // 거기서 id(로그인 할 때 사용되는 거) 와 beep_num 빼기
        String id = (String) claims.getPayload().get("id");
        int beepNum = (int) claims.getPayload().get("beep_num");

        return crateAccessToken(id, beepNum);

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

package com.example.mzbeeperback.domain.user.controller;

import com.example.mzbeeperback.domain.user.controller.dto.LoginDTO;
import com.example.mzbeeperback.domain.user.controller.dto.MyPageDTO;
import com.example.mzbeeperback.domain.user.controller.dto.SignUpDTO;
import com.example.mzbeeperback.domain.user.service.UserService;
import com.example.mzbeeperback.global.jwt.controller.dto.TokenDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mzbeeper")
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    public void saveUser(@RequestBody SignUpDTO signUpDTO) {
        userService.saveUser(signUpDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {

        TokenDTO tokenDTO = userService.login(loginDTO);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("accessToken", tokenDTO.getAccessToken());
        //responseHeaders.set("refreshToken", tokenDTO.getRefreshToken());

        Cookie refreshTokenCookie = new Cookie("refreshToken", tokenDTO.getRefreshToken());
        log.info("refreshToken = {}", tokenDTO.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); //
        refreshTokenCookie.setPath("/"); //
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7);

        response.addCookie(refreshTokenCookie);
        log.info("Set-Cookie 헤더 추가됨: refreshToken = {}", refreshTokenCookie.getValue());

        return ResponseEntity.ok().headers(responseHeaders).body("login 성공. 토큰 발행");
    }

    // 마이페이지에서 내 정보 가져오기
    @GetMapping("/myinfo")
    public MyPageDTO getMyInfo(@RequestHeader("Authorization") String accessToken) {
        String real_accessToken = accessToken.replace("Bearer ", "");
        log.info("accessToken={}",real_accessToken);

        return userService.findMyInfo(real_accessToken);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", "");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // HTTPS 사용 안 하므로 false
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0); // 즉시 만료

        response.addCookie(refreshTokenCookie); // 쿠키 삭제 적용

        return ResponseEntity.ok("로그아웃 성공");
    }


}

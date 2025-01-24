package com.example.mzbeeperback.domain.user.controller;

import com.example.mzbeeperback.domain.user.controller.dto.LoginDTO;
import com.example.mzbeeperback.domain.user.controller.dto.MyPageDTO;
import com.example.mzbeeperback.domain.user.controller.dto.SignUpDTO;
import com.example.mzbeeperback.domain.user.service.UserService;
import com.example.mzbeeperback.global.jwt.controller.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/mzbeeper/save")
    public void saveUser(@RequestBody SignUpDTO signUpDTO) {
        userService.saveUser(signUpDTO);
    }

    @PostMapping("/mzbeeper/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {

        TokenDTO tokenDTO = userService.login(loginDTO);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("accessToken", tokenDTO.getAccessToken());
        responseHeaders.set("refreshToken", tokenDTO.getRefreshToken());

        return ResponseEntity.ok().headers(responseHeaders).body("login 성공. 토큰 발행");
    }

    // 마이페이지에서 내 정보 가져오기
    @GetMapping("/mzbeeper/myinfo")
    public MyPageDTO getMyInfo(@RequestHeader("Authorization") String accessToken) {
        String real_accessToken = accessToken.replace("Bearer ", "");
        log.info("accessToken={}",real_accessToken);

        return userService.findMyInfo(real_accessToken);
    }


}

package com.example.mzbeeperback.domain.user.controller;

import com.example.mzbeeperback.domain.user.dto.UserLoginDTO;
import com.example.mzbeeperback.domain.user.dto.UserSignUpDTO;
import com.example.mzbeeperback.domain.user.service.LoginService;
import com.example.mzbeeperback.domain.user.service.SignUpService;
import com.example.mzbeeperback.global.jwt.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final SignUpService signUpService;
    private final LoginService loginService;

    @PostMapping("/mzbeeper/regist")
    public void registMB(@RequestBody UserSignUpDTO userSignUpDTO) {
        //service의 메소드로 넘겨준다.
        signUpService.saveUser(userSignUpDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/mzbeeper/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) throws NoSuchAlgorithmException {
        String userId = userLoginDTO.getUserId();
        String userPwd = userLoginDTO.getUserPwd();

        TokenDTO tokenDTO = loginService.tryLogin(userId, userPwd);
        //헤더에 token을 넣어서 전달함.
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("accessToken", tokenDTO.getAccessToken());
        responseHeaders.set("refreshToken", tokenDTO.getRefreshToken());

        return ResponseEntity.ok().headers(responseHeaders).body("Response with header using ResponseEntity");
    }


}

package com.example.mzbeeperback.domain.user.controller;

import com.example.mzbeeperback.domain.user.dto.UserLoginDTO;
import com.example.mzbeeperback.domain.user.dto.UserSignUpDTO;
import com.example.mzbeeperback.domain.user.service.LoginService;
import com.example.mzbeeperback.domain.user.service.SignUpService;
import com.example.mzbeeperback.global.jwt.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
    private SignUpService signUpService;

    private LoginService loginService;



    @Autowired
    public UserController(SignUpService signUpService, LoginService loginService) {
        this.signUpService = signUpService;
        this.loginService = loginService;

    }



    @PostMapping("/mzbeeper/regist")
    public void registMB(@RequestBody UserSignUpDTO userSignUpDTO) {
        //service의 메소드로 넘겨준다.
        signUpService.saveUser(userSignUpDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    //@ResponseBody
    @PostMapping("/mzbeeper/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) throws NoSuchAlgorithmException {
        String userId = userLoginDTO.getUserId();
        String userPwd = userLoginDTO.getUserPwd();
        //return loginService.tryLogin(userId, userPwd);
        //return tokenDTO;
        TokenDTO tokenDTO = loginService.tryLogin(userId, userPwd);
        //헤더에 token을 넣어서 전달함.
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("accessToken", tokenDTO.getAccessToken());
        responseHeaders.set("refreshToken", tokenDTO.getRefreshToken());

        return ResponseEntity.ok().headers(responseHeaders).body("Response with header using ResponseEntity");
    }

//    @PostMapping("/mzbeeper/send/msg")
//    public void testMethod(@RequestHeader String accessToken) {
//
//
//    }

    // 프론트엔드로부터 token을 전달 받아 claims 속 삐삐번호를 추출하고 그것을 토대로 삐삐를 확인할 때 가져오기



}

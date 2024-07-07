package com.example.mzbeeperback.domain.user.service;

import com.example.mzbeeperback.domain.user.dto.UserPwdSaltDTO;
import com.example.mzbeeperback.domain.user.entity.UserEntity;
import com.example.mzbeeperback.domain.user.repository.UserRepository;
import com.example.mzbeeperback.global.jwt.dto.TokenDTO;
import com.example.mzbeeperback.global.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class LoginService {
    private UserRepository userRepository;
    private JwtService jwtService;

    @Autowired
    public LoginService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public TokenDTO tryLogin(String userId, String userPwd) throws NoSuchAlgorithmException {
        TokenDTO tokenDTO = null;
        // db에서 유저 아이디로 정보를 모두 가져온다.
        UserEntity userLogin = userRepository.loginUser(userId);
        // 그 중에서 해시값으로 된 비밀번호와 salt를 사용한다.
        String hash_pwd_dto = userLogin.getHash_pwd();
        String salt_dto = userLogin.getSalt();

        //로그인한 사람의 삐삐 번호를 가져온다
        int beep_num = userLogin.getBeep_num();
        System.out.println(beep_num);

        UserPwdSaltDTO userPwdSaltDTO = new UserPwdSaltDTO(hash_pwd_dto, salt_dto);

        // 사용자가 입력한 비밀번호와 db에서 가져온 salt를 섞는다.
        String mixPwdAndSaltPwd = userPwd + salt_dto;

        // sha256을 처리하여 사용자가 입력한 비밀번호의 해쉬값을 뽑아낸다.
        String checkPwd = encrypt(mixPwdAndSaltPwd);

        // 뽑아낸 해쉬값이 db에서 가져온 해쉬값과 같다면 토큰을 발행한다.
        if (hash_pwd_dto.equals(checkPwd)) {
            System.out.println("same");
            return tokenDTO = new TokenDTO(jwtService.crateAccessToken(userId, beep_num), jwtService.createRefreshToken(userId, beep_num));
        } else {
            System.out.println("not same");
            // 같지 않았을 떄 팝업 메세지를 띄우던지 해야 함.
        }

        return tokenDTO;
    }

    public String encrypt(String pwd) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(pwd.getBytes());

        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

}


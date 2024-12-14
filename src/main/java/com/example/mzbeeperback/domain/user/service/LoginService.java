package com.example.mzbeeperback.domain.user.service;

import com.example.mzbeeperback.domain.user.entity.UserEntity;
import com.example.mzbeeperback.domain.user.repository.UserRepository;
import com.example.mzbeeperback.global.jwt.dto.TokenDTO;
import com.example.mzbeeperback.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public TokenDTO tryLogin(String userId, String userPwd) throws NoSuchAlgorithmException {
        TokenDTO tokenDTO = null;
        // db에서 유저 아이디로 정보를 모두 가져와 hash된 pwd, salt, 삐삐 번호를 추출한다
        UserEntity userLogin = userRepository.loginUser(userId);
        String hash_pwd_dto = userLogin.getHash_pwd();
        String salt_dto = userLogin.getSalt();
        int beep_num = userLogin.getBeep_num();

        // 사용자가 입력한 비밀번호와 db에서 가져온 salt를 섞고 sha-256을 돌려서 비밀번호의 해쉬값을 만들어본다
        String mixPwdAndSaltPwd = userPwd + salt_dto;
        String checkPwd = encrypt(mixPwdAndSaltPwd);

        // 뽑아낸 해쉬값이 db에서 가져온 해쉬값과 같다면 토큰을 발행한다.
        if (hash_pwd_dto.equals(checkPwd)) {
            System.out.println("same");
            return new TokenDTO(jwtService.crateAccessToken(userId, beep_num), jwtService.createRefreshToken(userId, beep_num));
        } else {
            System.out.println("not same");
            // 같지 않았을 떄 팝업 메세지를 띄우던지 해야 함.
        }

        return tokenDTO;
    }

    private String encrypt(String pwd) throws NoSuchAlgorithmException {
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


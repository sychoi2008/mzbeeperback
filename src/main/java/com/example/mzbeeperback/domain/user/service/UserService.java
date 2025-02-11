package com.example.mzbeeperback.domain.user.service;

import com.example.mzbeeperback.domain.user.controller.dto.MyPageDTO;
import com.example.mzbeeperback.domain.user.controller.dto.SignUpDTO;
import com.example.mzbeeperback.domain.user.controller.dto.LoginDTO;
import com.example.mzbeeperback.domain.user.entity.User;
import com.example.mzbeeperback.domain.user.repository.UserRepository;

import com.example.mzbeeperback.global.exception.IllegalLoginException;
import com.example.mzbeeperback.global.jwt.controller.dto.TokenDTO;
import com.example.mzbeeperback.global.jwt.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    // 회원가입
    public void saveUser(SignUpDTO signUpDTO) {
        while(true) {
            int beep_num = (int)(Math.random() * (99999 - 10000 + 1)) + 10000;
            log.info("번호 생성={}", beep_num);
            if(userRepository.existsByBeeperNum(beep_num)) continue;
            else {
                User user = new User(signUpDTO, beep_num);
                userRepository.save(user);
                log.info("저장완료");
                break;
            }
        }
    }

    public TokenDTO login(LoginDTO loginDTO)  {
        // db에서 유저 아이디로 정보를 모두 가져와 hash된 pwd, salt, 삐삐 번호를 추출한다
        User user = userRepository.findByUserId(loginDTO.getUserId());


        if(user == null) {
            throw new IllegalStateException("회원이 없습니다");
        }

        // 사용자가 입력한 비밀번호와 db에서 가져온 salt를 섞기
        String mixPwdAndSaltPwd = loginDTO.getUserPwd() + user.getSalt();


        // sha-256을 돌려서 비밀번호의 해쉬값을 만들어본다
        String checkPwd = encrypt(mixPwdAndSaltPwd);


        // salt와 유저가 입력한 비밀번호를 섞은 값과 db에서 가져온 해쉬 비밀번호와 같다면 토큰을 발행한다.
        if (user.getUserPwd().equals(checkPwd)) {
            log.info("로그인 성공");

            // 실험
            // 10분 : 600000L, 하루 : 86400000L

            String access = jwtService.createToken(user.getUserId(), user.getBeeperNum(), 600000L);
            String refresh = jwtService.createToken(user.getUserId(), user.getBeeperNum(), 86400000L);

            return new TokenDTO(access, refresh);
        } else {
            throw new IllegalLoginException("아이디나 비밀번호가 틀렸습니다");
        }

    }

    // 마이페이지에서 내 이름과 삐삐 번호를 보여주는 메서드
    public MyPageDTO findMyInfo(String accessToken) {
        // JWT에서 아이디를 추출
        String findUserId = jwtService.parseUserId(accessToken);
        // 아이디로 내 객체 가져오기
        User findUser = userRepository.findByUserId(findUserId);

        return new MyPageDTO(findUser.getName(), findUser.getBeeperNum());
    }



    private String encrypt(String pwd) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) { // 체크 예외 -> 런타임 에러로 수정
            throw new IllegalArgumentException(e);
        }
        md.update(pwd.getBytes(StandardCharsets.UTF_8));

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

package com.example.mzbeeperback.domain.user.service;

import com.example.mzbeeperback.domain.user.dto.UserSignUpDTO;
import com.example.mzbeeperback.domain.user.entity.UserEntity;
import com.example.mzbeeperback.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class SignUpService {
    private UserRepository userRepository;

    @Autowired
    public SignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void saveUser(UserSignUpDTO userSignUpDTO) {

        //while문으로 중복계속 체크하여 중복이면 다시 랜덤번호를 생성하게 한다. 중복이 없다면 break로 빠져나올 수 있도록 한다.
        while(true) {
            //랜덤번호 생성
            int beep_num = (int)(Math.random() * (99999 - 10000 + 1)) + 10000;
            if(userRepository.checkNum(beep_num)!=0) continue;
            else {
                //dto에서 데이터를 꺼낸다
                String name = userSignUpDTO.getName();
                String id = userSignUpDTO.getId();
                String hash_pwd = userSignUpDTO.getHash_pwd();
                String salt = userSignUpDTO.getSalt();
                LocalDateTime dateTime = userSignUpDTO.getCreate_date();
                // entity로 바꾼다
                UserEntity userEntity = new UserEntity(beep_num, name, id, hash_pwd, salt, dateTime);
                //repository 메소드를 사용하여 db에 저장한다.
                userRepository.insertUser(userEntity);
                break;
            }
        }



    }
}

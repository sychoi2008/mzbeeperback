package com.example.mzbeeperback.domain.beeper.service;

import com.example.mzbeeperback.domain.beeper.repository.BeeperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BeeperService {
    private final BeeperRepository beeperRepository;


    // jwt토큰을 파싱한 나의 삐삐 번호를 파라미터 값으로 받는다
    public String getMsg(int beeperNum) {
        return beeperRepository.getMyBeeperMsg(beeperNum);
    }
}


package com.example.mzbeeperback.domain.beeper.service;

import com.example.mzbeeperback.domain.beeper.repository.BeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeeperService {
    private BeeperRepository beeperRepository;

    @Autowired
    public BeeperService(BeeperRepository beeperRepository) {
        this.beeperRepository = beeperRepository;
    }

    // jwt토큰을 파싱한 나의 삐삐 번호를 파라미터 값으로 받는다
    public int getMsg(int beeperNum) {
        return beeperRepository.getMyBeeperMsg(beeperNum);
    }
}


package com.example.mzbeeperback.domain.mypage.service;

import com.example.mzbeeperback.domain.mypage.dto.MyPageDTO;
import com.example.mzbeeperback.domain.mypage.dto.MySendDTO;
import com.example.mzbeeperback.domain.mypage.entity.MyPageEntity;
import com.example.mzbeeperback.domain.mypage.entity.MySendEntity;
import com.example.mzbeeperback.domain.mypage.repository.MyPageRepository;
import com.example.mzbeeperback.domain.mypage.repository.MySendRepository;
import com.example.mzbeeperback.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageRepository myPageRepository;
    private final JwtService jwtService;
    private final MySendRepository mySendRepository;

    public MyPageDTO getMyInfo(String accessToken) {
        int beepNum = jwtService.parseBeepNum(accessToken);

        MyPageEntity myPageEntity = myPageRepository.getInfo(beepNum);

        return new MyPageDTO(myPageEntity.getBeep_num(), myPageEntity.getName());
    }

    public List<MySendDTO> getMySend(String accessToken) {
        int beepNum = jwtService.parseBeepNum(accessToken);

        List<MySendEntity> mySendEntities = mySendRepository.getMySendMsg(beepNum);
        List<MySendDTO> mySendDTOS = new ArrayList<>();

        if(!mySendEntities.isEmpty()) {
            for(int i=0; i<mySendEntities.size(); i++) {
                MySendEntity mse = mySendEntities.get(i);
                mySendDTOS.add(new MySendDTO(mse.getMsg_id(), mse.getMsg_date(), mse.getReader_num(), mse.getMsg()));
            }

        } else {
            System.out.println("No data here");
        }

        return mySendDTOS;

    }

}

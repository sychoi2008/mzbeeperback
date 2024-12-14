package com.example.mzbeeperback.domain.msg.service;

import com.example.mzbeeperback.domain.msg.dto.MsgWriteDTO;
import com.example.mzbeeperback.domain.msg.entity.MsgEntity;
import com.example.mzbeeperback.domain.msg.repository.MsgRepository;
import com.example.mzbeeperback.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MsgService {
    private final MsgRepository msgRepository;
    private final JwtService jwtService;

    public void saveMsg(String accessToken, MsgWriteDTO msgWriteDTO) {
        String msgContent = msgWriteDTO.getMsg();
        // jwt에서 보내는 사람 번호 추출하기
        //int writer = msgWriteDTO.getWriteNum();
        int writer = jwtService.parseBeepNum(accessToken);
        int reader = msgWriteDTO.getReaderNum();
        LocalDateTime sendTime = msgWriteDTO.getSend_date();

        MsgEntity msgEntity = new MsgEntity(msgContent, writer, reader, sendTime);
        msgRepository.insertMsg(msgEntity);

        System.out.println("msg is stored in DB");
    }
}

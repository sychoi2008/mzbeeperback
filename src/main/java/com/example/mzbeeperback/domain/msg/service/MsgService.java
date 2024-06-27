package com.example.mzbeeperback.domain.msg.service;

import com.example.mzbeeperback.domain.msg.dto.MsgWriteDTO;
import com.example.mzbeeperback.domain.msg.entity.MsgEntity;
import com.example.mzbeeperback.domain.msg.repository.MsgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MsgService {
    private MsgRepository msgRepository;

    @Autowired
    public MsgService(MsgRepository msgRepository) {
        this.msgRepository = msgRepository;
    }

    public void saveMsg(MsgWriteDTO msgWriteDTO) {
        int msgContent = msgWriteDTO.getMsg();
        int writer = msgWriteDTO.getWriteNum();
        int reader = msgWriteDTO.getReaderNum();
        LocalDateTime sendTime = msgWriteDTO.getSend_date();

        MsgEntity msgEntity = new MsgEntity(msgContent, writer, reader, sendTime);
        msgRepository.insertMsg(msgEntity);

        System.out.println("메세지를 DB에 저장 완료");
    }
}

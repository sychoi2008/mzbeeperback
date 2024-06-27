package com.example.mzbeeperback.domain.msg.controller;

import com.example.mzbeeperback.domain.msg.dto.MsgWriteDTO;
import com.example.mzbeeperback.domain.msg.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MsgController {
    private MsgService msgService;

    @Autowired
    public MsgController(MsgService msgService) {
        this.msgService = msgService;
    }

    @PostMapping("/mzbeeper/send/msg")
    public void sendMsg(@RequestHeader String accessToken, @RequestBody MsgWriteDTO msgWriteDTO) {

        // if문으로 일단 accessToken을 확인한다. 맞으면 service 메소드 실행, 아니라면 refreshtoken을 활용하거나 다시 발급
        msgService.saveMsg(msgWriteDTO);
    }
}

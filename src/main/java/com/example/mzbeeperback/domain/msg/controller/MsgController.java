package com.example.mzbeeperback.domain.msg.controller;

import com.example.mzbeeperback.domain.msg.dto.MsgWriteDTO;
import com.example.mzbeeperback.domain.msg.service.MsgService;
import com.example.mzbeeperback.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class MsgController {
    private final MsgService msgService;
    private final JwtService jwtService;

    @PostMapping("/mzbeeper/send/msg")
    public void sendMsg(@RequestHeader("Authorization") String accessToken, @RequestBody MsgWriteDTO msgWriteDTO) {

        // if문으로 일단 accessToken을 확인한다.(아마도 isValid겠지??) 맞으면 service 메소드 실행, 아니라면 refreshtoken을 활용하거나 다시 발급
        //msgService.saveMsg(msgWriteDTO);
        String real_accessToken = accessToken.replace("Bearer ", "");
        System.out.println("MsgController : " + real_accessToken);

        if(jwtService.isValid(real_accessToken)) {
            msgService.saveMsg(real_accessToken, msgWriteDTO);
        } else {
            // 다시 로그인하라고 권장해야 하나...
            System.out.println("accessToken error");
        }

        // 2024.07.06
        // jwt를 받아서 분해하여 보내는 사람의 전화번호를 추출한다(스프링 부트 안에서 해결할 일)
    }
}

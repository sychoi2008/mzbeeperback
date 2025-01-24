package com.example.mzbeeperback.domain.msg.controller;

import com.example.mzbeeperback.domain.msg.service.MsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mzbeeper")
public class MsgController {
    private final MsgService msgService;

    @PostMapping("/msg")
    public ResponseEntity<String> sendMsg(@RequestHeader("Authorization") String accessToken, @RequestBody MsgDTO msgDTO) {
        // JWT 토큰 자르기
        String parsedToken = accessToken.replace("Bearer ", "");
        msgService.saveMsg(parsedToken, msgDTO);
        return new ResponseEntity<>("메세지 보내기 성공", HttpStatus.CREATED);
    }


    // 내가 보낸 모든 메세지 조회하기
    @GetMapping("/myinfo/mysentlist")
    public List<MySentMsgDTO> mySendMsg(@RequestHeader("Authorization") String accessToken) {
        String parsedAccessToken = accessToken.replace("Bearer ", "");
        return msgService.getMySend(parsedAccessToken);
    }

    // 버튼 눌러서 최신 삐삐 메세지 1개를 프론트에게 반환하기
    @GetMapping("/mymsg")
    public String getBeeperMsg(@RequestHeader("Authorization") String accessToken) {
        String parsedAccessToken = accessToken.replace("Bearer ", "");
        return msgService.getMsg(parsedAccessToken);
    }


}

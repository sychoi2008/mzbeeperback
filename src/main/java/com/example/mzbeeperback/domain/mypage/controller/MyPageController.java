package com.example.mzbeeperback.domain.mypage.controller;

import com.example.mzbeeperback.domain.mypage.dto.MyPageDTO;
import com.example.mzbeeperback.domain.mypage.dto.MySendDTO;
import com.example.mzbeeperback.domain.mypage.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MyPageController {
    private MyPageService myPageService;


    @Autowired
    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }
    @GetMapping("/mzbeeper/myinfo")
    public MyPageDTO getNameAndNum(@RequestHeader("Authorization") String accessToken) {
        String real_accessToken = accessToken.replace("Bearer ", "");
        System.out.println("MyPageController : " + real_accessToken);

        return myPageService.getMyInfo(real_accessToken);
    }

    // 내가 보낸 삐삐 메세지 확인
    @GetMapping("/mzbeeper/mypage/sendmsglist")
    public List<MySendDTO> mySendMsg(@RequestHeader("Authorization") String accessToken) {
        String real_accessToken = accessToken.replace("Bearer ", "");
        System.out.println("MyPageController : " + real_accessToken);

        return myPageService.getMySend(real_accessToken);
    }

    // 내가 받은 삐삐 메세지 확인
}

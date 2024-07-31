package com.example.mzbeeperback.domain.beeper.controller;


import com.example.mzbeeperback.domain.beeper.dto.BeeperDTO;
import com.example.mzbeeperback.domain.beeper.service.BeeperService;
import com.example.mzbeeperback.global.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BeeperController {

    private BeeperService beeperService;
    private JwtService jwtService;

    @Autowired
    public BeeperController(BeeperService beeperService, JwtService jwtService) {
        this.beeperService = beeperService;
        this.jwtService = jwtService;
    }

    // 버튼 눌러서 최신 삐삐 메세지를 프론트에게 반환하기
    @GetMapping("/mybeeper/getmymsg")
    public int getBeeperMsg(@RequestHeader("Authorization") String accessToken) {
        // jwt토큰을 통해 나의 삐삐 번호를 파싱한다 -> 후에 repository에서 값을 가져올 때 사용할 것
        String real_accessToken = accessToken.replace("Bearer ", "");
        System.out.println("beeperController : " + real_accessToken);

        int my_num = jwtService.parseBeepNum(real_accessToken);

        return beeperService.getMsg(my_num);
    }
}

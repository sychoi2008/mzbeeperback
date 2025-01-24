package com.example.mzbeeperback.domain.msg.service;

import com.example.mzbeeperback.domain.msg.controller.MsgDTO;
import com.example.mzbeeperback.domain.msg.controller.MySentMsgDTO;
import com.example.mzbeeperback.domain.msg.entity.Message;
import com.example.mzbeeperback.domain.msg.repository.MsgRepository;
import com.example.mzbeeperback.domain.user.entity.User;
import com.example.mzbeeperback.domain.user.repository.UserRepository;
import com.example.mzbeeperback.global.jwt.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MsgService {
    private final MsgRepository msgRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void saveMsg(String accessToken, MsgDTO msgDTO) {
        // jwt에서 메세지 보내는 사람(나) 삐삐 번호 추출하기
        int senderNum = jwtService.parseBeepNum(accessToken);

        // dto에 담긴 받는 사람의 번호로 받는 사람 객체 가져오기
        User receiver = userRepository.findByBeeperNum(msgDTO.getReaderNum());
        // 추출해서 가져온 삐삐 번호로 내 객체 가져오기
        User sender = userRepository.findByBeeperNum(senderNum);


        String content = msgDTO.getMsg();
        LocalDateTime sendTime = msgDTO.getSend_date();

        Message message = new Message(receiver, sender, content, sendTime);
        msgRepository.save(message);

    }

    public List<MySentMsgDTO> getMySend(String accessToken) {
        int beepNum = jwtService.parseBeepNum(accessToken);

        // 내 정보 객체 가져오기
        User findUser = userRepository.findByBeeperNum(beepNum);

        // 객체로 쿼리 메서드를 만들었지만, 연관 관계 매핑이 되어 있어서 자동으로 User 객체의 PK로 쿼리 메서드를 만듦
        List<Message> allMsg = msgRepository.findAllBySender(findUser);

        List<MySentMsgDTO> sentList = new ArrayList<>();

        for (Message message : allMsg) {
            sentList.add(new MySentMsgDTO(message));
        }

        return sentList;

    }

    public String getMsg(String accessToken) {
        int beepNum = jwtService.parseBeepNum(accessToken);

        // 내 정보 객체 가져오기
        User findUser = userRepository.findByBeeperNum(beepNum);

        Message msg = msgRepository.findTop1ByReceiverOrderByMsgDate(findUser);

        return msg.getContent();

    }


}

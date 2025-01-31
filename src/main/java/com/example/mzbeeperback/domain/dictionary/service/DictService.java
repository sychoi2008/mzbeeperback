package com.example.mzbeeperback.domain.dictionary.service;

import com.example.mzbeeperback.domain.dictionary.controller.dto.AddDictDTO;
import com.example.mzbeeperback.domain.dictionary.controller.dto.DictDTO;
import com.example.mzbeeperback.domain.dictionary.controller.dto.LikedDTO;
import com.example.mzbeeperback.domain.dictionary.entity.Dictionary;
import com.example.mzbeeperback.domain.dictionary.entity.Likey;
import com.example.mzbeeperback.domain.dictionary.repository.DictRepository;
import com.example.mzbeeperback.domain.dictionary.repository.LikeyRepository;
import com.example.mzbeeperback.domain.user.entity.User;
import com.example.mzbeeperback.domain.user.repository.UserRepository;
import com.example.mzbeeperback.global.jwt.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DictService {
    private final DictRepository dictRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final LikeyRepository likeyRepository;

    public List<DictDTO> searchMsgCode(String accessToken, String code) {
        int beepNum = jwtService.parseBeepNum(accessToken);
        User findUser = userRepository.findByBeeperNum(beepNum);

        // 내가 좋아요를 누른 목록 가져오기
        List<Dictionary> myLikedCodes = likeyRepository.findLikedDictionariesByUser(findUser);

        // 검색한 결과
        List<Dictionary> result = dictRepository.findByDictMsgContaining(code);

        List<DictDTO> findDict = new ArrayList<>();

        for (Dictionary dict : result) {
            // 내가 좋아요를 누른 암호 목록 중에 검색한 결과를 하나씩 가져와서 id를 비교하기
            Boolean res = myLikedCodes.stream()
                    .anyMatch(likedCode -> likedCode.getId().equals(dict.getId()));
            DictDTO newDTO = new DictDTO(dict, res);
            findDict.add(newDTO);
        }

        return findDict;

    }

    public void addLiked(String accessToken, LikedDTO likedDTO) {
        int beepNum = jwtService.parseBeepNum(accessToken);
        User findUser = userRepository.findByBeeperNum(beepNum);


        Dictionary findDict = dictRepository.findByDictMsg(likedDTO.getMsg());
        log.info("findDict = {}", findDict);

        likeyRepository.save(new Likey(findUser, findDict));
    }

    public void removeLiked(String accessToken, String deletedMsg) {
        int beepNum = jwtService.parseBeepNum(accessToken);
        User findUser = userRepository.findByBeeperNum(beepNum);

        Dictionary findDict = dictRepository.findByDictMsg(deletedMsg);

        likeyRepository.deleteByLikedPersonAndSecretCode(findUser, findDict);
    }

    public void addDictMsg(String accessToken, AddDictDTO addDictDTO) {
        // jwt 토큰에서 삐삐 번호 추출 -> 내 정보 가져오기
        int beepNum = jwtService.parseBeepNum(accessToken);
        User findUser = userRepository.findByBeeperNum(beepNum);
        // 엔티티 만들기
        Dictionary dictionary = new Dictionary(addDictDTO, findUser);
        // db에 저장
        dictRepository.save(dictionary);
    }
}

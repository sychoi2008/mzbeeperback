package com.example.mzbeeperback.domain.dictionary.controller;

import com.example.mzbeeperback.domain.dictionary.controller.dto.AddDictDTO;
import com.example.mzbeeperback.domain.dictionary.controller.dto.DictDTO;
import com.example.mzbeeperback.domain.dictionary.controller.dto.LikedDTO;
import com.example.mzbeeperback.domain.dictionary.service.DictService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mzbeeper/dict")
@Slf4j
public class DictController {
    private final DictService dictService;

    @GetMapping
    public List<DictDTO> findBeeperMsg(@RequestHeader("Authorization") String accessToken, @RequestParam String search)  {
        return dictService.searchMsgCode(getParsedToken(accessToken), search);

    }

    // 좋아요 누르기
    @PostMapping("/like")
    public ResponseEntity<String> addLikedMsg(@RequestHeader("Authorization") String accessToken, @RequestBody LikedDTO likedDTO) {
        log.info("likedDTO.msg = {}", likedDTO.getMsg());
        dictService.addLiked(getParsedToken(accessToken), likedDTO);
        return ResponseEntity.ok("좋아요 누름");
    }

    // 좋아요 취소
    @DeleteMapping("/like")
    public ResponseEntity<String> removeLikedMsg(@RequestHeader("Authorization") String accessToken, @RequestParam String deletedMsg) {
        dictService.removeLiked(getParsedToken(accessToken), deletedMsg);
        return ResponseEntity.ok("좋아요 취소");
    }

    // 사전 등록
    @PostMapping("/add")
    public void addDict(@RequestHeader("Authorization") String accessToken, @RequestBody AddDictDTO addDictDTO) {
        log.info("addDTO.msg = {}", addDictDTO.getMsg());
        dictService.addDictMsg(getParsedToken(accessToken), addDictDTO);
    }

    private static String getParsedToken(String accessToken) {
        return accessToken.replace("Bearer ", "");
    }
}

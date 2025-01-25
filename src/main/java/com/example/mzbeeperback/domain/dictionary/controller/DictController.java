package com.example.mzbeeperback.domain.dictionary.controller;

import com.example.mzbeeperback.domain.dictionary.controller.dto.DictDTO;
import com.example.mzbeeperback.domain.dictionary.service.DictService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class DictController {
    private final DictService dictService;

    @GetMapping("/mzbeeper/dict")
    public DictDTO findBeeperMsg(@RequestParam String search, HttpServletResponse response) throws IOException {
        DictDTO dictDTO = dictService.searchMSG(search);
        if(dictDTO == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Value parameter is missing or null.");
        }
        return dictDTO;
    }
}

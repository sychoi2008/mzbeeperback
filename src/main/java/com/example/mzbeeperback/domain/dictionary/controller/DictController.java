package com.example.mzbeeperback.domain.dictionary.controller;

import com.example.mzbeeperback.domain.dictionary.dto.DictDTO;
import com.example.mzbeeperback.domain.dictionary.service.DictService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class DictController {
    private DictService dictService;

    @Autowired
    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    @GetMapping("/mzbeeper/dict")
    public DictDTO findBeeperMsg(@RequestParam String search, HttpServletResponse response) throws IOException {
        DictDTO dictDTO = dictService.searchMSG(search);
        if(dictDTO == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Value parameter is missing or null.");
        }
        return dictDTO;
    }
}

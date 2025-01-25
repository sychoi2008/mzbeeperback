package com.example.mzbeeperback.domain.dictionary.service;

import com.example.mzbeeperback.domain.dictionary.controller.dto.DictDTO;
import com.example.mzbeeperback.domain.dictionary.entity.DictEntity;
import com.example.mzbeeperback.domain.dictionary.repository.DictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DictService {
    private final DictRepository dictRepository;

    public DictDTO searchMSG(String msg_num) {
        try {
            DictEntity dictEntity = dictRepository.getMsgMean(msg_num);
            return new DictDTO(dictEntity.getDict_msg(), dictEntity.getDict_meant());
        } catch (Exception e) {
            return null;
        }

    }
}

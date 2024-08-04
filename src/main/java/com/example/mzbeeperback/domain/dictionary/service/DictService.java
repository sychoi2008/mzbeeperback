package com.example.mzbeeperback.domain.dictionary.service;

import com.example.mzbeeperback.domain.dictionary.dto.DictDTO;
import com.example.mzbeeperback.domain.dictionary.entity.DictEntity;
import com.example.mzbeeperback.domain.dictionary.repository.DictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictService {
    private DictRepository dictRepository;

    @Autowired
    public DictService(DictRepository dictRepository) {
        this.dictRepository = dictRepository;
    }

    public DictDTO searchMSG(String msg_num) {
        try {
            DictEntity dictEntity = dictRepository.getMsgMean(msg_num);
            return new DictDTO(dictEntity.getDict_msg(), dictEntity.getDict_meant());
        } catch (Exception e) {
            return null;
        }

    }
}

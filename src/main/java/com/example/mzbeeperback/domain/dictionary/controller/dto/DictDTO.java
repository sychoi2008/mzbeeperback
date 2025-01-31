package com.example.mzbeeperback.domain.dictionary.controller.dto;

import com.example.mzbeeperback.domain.dictionary.entity.Dictionary;
import lombok.*;

@Getter
@Setter
public class DictDTO {
    String dict_msg;
    String dict_meant;
    Boolean liked;

    public DictDTO(Dictionary dictionary, Boolean res) {
        this.dict_msg = dictionary.getDictMsg();
        this.dict_meant = dictionary.getDictMeant();
        this.liked = res;
    }


}

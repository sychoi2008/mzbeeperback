package com.example.mzbeeperback.domain.msg.controller;

import com.example.mzbeeperback.domain.msg.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MySentMsgDTO {

    private Long msgId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime create_date;

    private int readerNum;

    String msg;

    public MySentMsgDTO(Message msg) {
        this.msgId = msg.getId();
        this.create_date = msg.getMsgDate();
        this.readerNum = msg.getReceiver().getBeeperNum();
        this.msg = msg.getContent();
    }
}

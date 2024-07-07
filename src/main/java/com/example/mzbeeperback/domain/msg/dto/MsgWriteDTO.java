package com.example.mzbeeperback.domain.msg.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MsgWriteDTO {
    int msg;
    //int writerNum;
    int readerNum;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime send_date;
}

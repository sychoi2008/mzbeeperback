package com.example.mzbeeperback.domain.msg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Table(name = "beepermsg")
@Entity
public class MsgEntity {

    public MsgEntity(int msg, int writer_num, int reader_num, LocalDateTime msg_date) {
        this.msg = msg;
        this.writer_num = writer_num;
        this.reader_num = reader_num;
        this.msg_date = msg_date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int msg_id;

    int msg;

    int writer_num;

    int reader_num;

    LocalDateTime msg_date;
}

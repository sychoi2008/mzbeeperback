package com.example.mzbeeperback.domain.mypage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "beepermsg")
@Entity
public class MySendEntity {
    @Id
    int msg_id;
    String msg;
    int reader_num;
    LocalDateTime msg_date;
}

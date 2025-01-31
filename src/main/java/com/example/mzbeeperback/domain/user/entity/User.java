package com.example.mzbeeperback.domain.user.entity;

import com.example.mzbeeperback.domain.dictionary.entity.Likey;
import com.example.mzbeeperback.domain.msg.entity.Message;
import com.example.mzbeeperback.domain.user.controller.dto.SignUpDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_pwd")
    private String userPwd;

    @Column(name = "name")
    private String name;

    private String salt;

    @Column(name = "beeper_num")
    private int beeperNum;

    @Column(name = "create_date")
    private LocalDateTime createDate;


    public User(SignUpDTO dto, int number) {
        userId = dto.getId();
        userPwd = dto.getHash_pwd();
        name = dto.getName();
        salt = dto.getSalt();
        beeperNum = number;
        createDate = dto.getCreate_date();
    }


}

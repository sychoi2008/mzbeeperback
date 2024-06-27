package com.example.mzbeeperback.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity
public class UserEntity {
    @Id
    int beep_num;

    String name;

    String id;

    String hash_pwd;

    String salt;

    LocalDateTime create_date;

//    public UserEntity(String eName, String eId, String eHash, String eSalt, LocalDateTime eDate) {
//        this.name = eName;
//        this.id = eId;
//        this.hash_pwd = eHash;
//        this.salt = eSalt;
//        this.create_date = eDate;
//    }
}

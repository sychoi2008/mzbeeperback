package com.example.mzbeeperback.domain.dictionary.entity;

import com.example.mzbeeperback.domain.dictionary.controller.dto.LikedDTO;
import com.example.mzbeeperback.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Likey {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liked_person_id")
    private User likedPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dict_id")
    private Dictionary secretCode;

    public Likey(User user, Dictionary dict) {
        this.likedPerson = user;
        this.secretCode = dict;
    }
}

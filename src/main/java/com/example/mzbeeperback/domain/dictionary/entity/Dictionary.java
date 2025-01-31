package com.example.mzbeeperback.domain.dictionary.entity;

import com.example.mzbeeperback.domain.dictionary.controller.dto.AddDictDTO;
import com.example.mzbeeperback.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dict_msg")
    String dictMsg;

    @Column(name = "dict_meant")
    String dictMeant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    public Dictionary(AddDictDTO addDictDTO, User user) {
        this.dictMsg = addDictDTO.getMsg();
        this.dictMeant = addDictDTO.getMeaning();
        this.writer = user;
    }

}

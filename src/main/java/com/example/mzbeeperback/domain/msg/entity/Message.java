package com.example.mzbeeperback.domain.msg.entity;

import com.example.mzbeeperback.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    User receiver;

    String content;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    User sender;

    @Column(name = "msg_date")
    LocalDateTime msgDate;

    public Message(User receiver, User sender, String content, LocalDateTime dateTime) {
        this.receiver = receiver;
        this.content = content;
        this.sender = sender;
        this.msgDate = dateTime;
    }
}

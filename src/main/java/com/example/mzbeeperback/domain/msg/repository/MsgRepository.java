package com.example.mzbeeperback.domain.msg.repository;

import com.example.mzbeeperback.domain.msg.entity.Message;
import com.example.mzbeeperback.domain.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MsgRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllBySender(User user);

    Message findTop1ByReceiverOrderByMsgDate(User user);

}

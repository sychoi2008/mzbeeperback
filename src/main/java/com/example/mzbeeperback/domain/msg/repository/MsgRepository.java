package com.example.mzbeeperback.domain.msg.repository;

import com.example.mzbeeperback.domain.msg.entity.MsgEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MsgRepository extends JpaRepository<MsgEntity, Integer> {

    @Query(value = "INSERT INTO beepermsg values(:#{#msg.msg_date}, :#{#msg.msg}, :#{#msg.writer_num}, " +
            ":#{#msg.reader_num})",nativeQuery = true)
    @Transactional
    @Modifying
    // 메세지 전송
    void insertMsg(@Param("msg") MsgEntity msgEntity);

}

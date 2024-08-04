package com.example.mzbeeperback.domain.mypage.repository;

import com.example.mzbeeperback.domain.mypage.entity.MySendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MySendRepository extends JpaRepository<MySendEntity, Integer> {

    @Query(value = "select msg_id, msg_date, msg, reader_num from beepermsg where writer_num = :writerNum order by msg_date DESC", nativeQuery = true)
    List<MySendEntity> getMySendMsg(@Param("writerNum") int writerNum);

}

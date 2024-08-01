package com.example.mzbeeperback.domain.beeper.repository;

import com.example.mzbeeperback.domain.msg.entity.MsgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BeeperRepository extends JpaRepository<MsgEntity, Integer> {

    @Query(value = "select msg from beepermsg where reader_num = :beeperNum order by msg_date desc limit 1",nativeQuery = true)
    String getMyBeeperMsg(@Param("beeperNum")int beeperNum);
}

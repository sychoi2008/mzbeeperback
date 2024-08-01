package com.example.mzbeeperback.domain.dictionary.repository;

import com.example.mzbeeperback.domain.dictionary.entity.DictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DictRepository extends JpaRepository<DictEntity,Integer> {

    @Query(value = "SELECT * FROM beeperdict where dict_msg = :dictNum" ,nativeQuery = true)
    DictEntity getMsgMean(@Param("dictNum")String dictNum);
}

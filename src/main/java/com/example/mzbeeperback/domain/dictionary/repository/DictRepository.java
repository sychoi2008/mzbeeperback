package com.example.mzbeeperback.domain.dictionary.repository;

import com.example.mzbeeperback.domain.dictionary.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DictRepository extends JpaRepository<Dictionary,Integer> {

    // 검색
//    @Query(value = "SELECT * FROM beeperdict where dict_msg = :dictNum" ,nativeQuery = true)
//    Dictionary getMsgMean(@Param("dictNum")String dictNum);

    List<Dictionary> findByDictMsgContaining(String code);

    Dictionary findByDictMsg(String msg);


}

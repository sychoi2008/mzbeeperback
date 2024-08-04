package com.example.mzbeeperback.domain.mypage.repository;

import com.example.mzbeeperback.domain.mypage.entity.MyPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyPageRepository extends JpaRepository<MyPageEntity, Integer> {

    @Query(value = "SELECT beep_num, name FROM user where beep_num = :beep_num", nativeQuery = true)
    MyPageEntity getInfo(@Param("beep_num") int beep_num);

}

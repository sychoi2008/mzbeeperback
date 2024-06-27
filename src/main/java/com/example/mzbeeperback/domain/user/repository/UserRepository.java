package com.example.mzbeeperback.domain.user.repository;

import com.example.mzbeeperback.domain.user.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Transactional
    @Query(value = "SELECT COUNT(*) FROM user " +
            "WHERE beep_num = :beepNum",nativeQuery = true)
    int checkNum(@Param("beepNum") int num);


    @Query(value = "INSERT INTO user values(:#{#userInfo.beep_num}, :#{#userInfo.name},:#{#userInfo.id}," +
            ":#{#userInfo.hash_pwd}, :#{#userInfo.salt}, :#{#userInfo.create_date})", nativeQuery = true)
    @Transactional
    @Modifying
    void insertUser(@Param("userInfo") UserEntity userEntity);

    // 로그인
    @Query(value = "SELECT * FROM user where id = :userId",nativeQuery = true)
    UserEntity loginUser(@Param("userId") String id);


}

package com.example.mzbeeperback.domain.dictionary.repository;

import com.example.mzbeeperback.domain.dictionary.entity.Dictionary;
import com.example.mzbeeperback.domain.dictionary.entity.Likey;
import com.example.mzbeeperback.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeyRepository extends JpaRepository<Likey, Long> {

    // 내가 좋아요를 누른 삐삐 암호만 가져오기
    @Query("SELECT l.secretCode FROM Likey l WHERE l.likedPerson = :user")
    List<Dictionary> findLikedDictionariesByUser(@Param("user") User user);

    // 좋아요 취소
    void deleteByLikedPersonAndSecretCode(User user, Dictionary dict);

}

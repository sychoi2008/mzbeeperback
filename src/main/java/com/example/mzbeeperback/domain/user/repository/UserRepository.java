package com.example.mzbeeperback.domain.user.repository;

import com.example.mzbeeperback.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    // 삐삐번호 생성시 중복된 번호인지 체크하는 메서드
    boolean existsByBeeperNum(int num);

    // 로그인시 유저가 적은 아이디로 회원 객체 가져오기
    User findByUserId(String userId);

    // 전화번호로 사람 검색하기 -> msg 패키지에서 사용
    User findByBeeperNum(int num);


}

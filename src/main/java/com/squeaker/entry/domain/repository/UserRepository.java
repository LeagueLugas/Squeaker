package com.squeaker.entry.domain.repository;

import com.squeaker.entry.domain.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserIdAndUserPw(String userId, String userPw);
    User findByUuidAndUserRefreshToken(int uuid, String refreshToken);
    boolean existsByUserId(String userId);
    User findByUserId(String userId);
    User findByUuid(Integer uuid);
    List<User> findAllByUserIdContainingOrUserNameContaining(String userId, String userName);
}

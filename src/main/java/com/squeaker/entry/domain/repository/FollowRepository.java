package com.squeaker.entry.domain.repository;

import com.squeaker.entry.domain.entitys.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Object> {
    List<Follow> findByFollower(Integer uuid);
    List<Follow> findByFollowerOrFollowing(Integer uuid, Integer uuid2);
    Follow findByFollowerAndFollowing(Integer uuid, Integer uuid2);
}

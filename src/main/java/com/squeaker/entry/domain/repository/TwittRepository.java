package com.squeaker.entry.domain.repository;

import com.squeaker.entry.domain.entitys.Twitt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TwittRepository extends JpaRepository<Twitt, Integer> {
    List<Twitt> findByTwittUidOrderByTwittDateDesc(Integer uuid);
    List<Twitt> findByTwittUidInOrderByTwittDateDesc(List<Integer> uuid);
    Twitt findByTwittId(Integer twittId);
}

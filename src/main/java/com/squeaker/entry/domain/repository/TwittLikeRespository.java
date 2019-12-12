package com.squeaker.entry.domain.repository;

import com.squeaker.entry.domain.entitys.TwittLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwittLikeRespository extends JpaRepository<TwittLike, Integer> {
    TwittLike findByTwittIdAndUuid(Integer twittId, Integer uuid);
    TwittLike findByTwittId(Integer twittId);
    Integer countAllByTwittId(Integer twittId);
}

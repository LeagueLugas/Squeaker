package com.squeaker.entry.domain.repository;

import com.squeaker.entry.domain.entitys.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByTwittId(Integer twitId);
}

package com.squeaker.entry.domain.entitys;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Follow {

    @Id
    private Integer follower;

    @Column
    private Integer following;

    @Builder
    public Follow(Integer follower, Integer following) {
        this.follower = follower;
        this.following = following;
    }
}

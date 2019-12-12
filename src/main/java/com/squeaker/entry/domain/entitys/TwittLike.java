package com.squeaker.entry.domain.entitys;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class TwittLike {

    @Id
    @Column
    private Integer twittId;

    private Integer uuid;

    @Builder
    public TwittLike(Integer twittId, Integer uuid) {
        this.twittId = twittId;
        this.uuid = uuid;
    }
}

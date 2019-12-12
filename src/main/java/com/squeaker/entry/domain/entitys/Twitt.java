package com.squeaker.entry.domain.entitys;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Twitt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer twittId;

    @Column
    private Integer twittUid;

    @Column
    private Long twittDate;

    @Column
    private String twittContent;

    @Builder
    public Twitt(Integer twittUid, Long twittDate, String twittContent) {
        this.twittUid = twittUid;
        this.twittDate = twittDate;
        this.twittContent = twittContent;
    }
}

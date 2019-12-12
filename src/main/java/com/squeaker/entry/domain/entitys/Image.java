package com.squeaker.entry.domain.entitys;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iuid;

    @Column
    private Integer twittId;

    @Column
    private String imageName;

    @Builder
    public Image(Integer twittId, String imageName) {
        this.twittId = twittId;
        this.imageName = imageName;
    }
}

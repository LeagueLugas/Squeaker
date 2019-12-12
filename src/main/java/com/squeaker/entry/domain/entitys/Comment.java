package com.squeaker.entry.domain.entitys;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column
    private Integer commentUid;

    private Integer commentTwitId;

    private String comment;

    private Long commentDate;

    @Builder
    public Comment(Integer commentUid, Integer commentTwitId, String comment, Long commentDate) {
        this.commentUid = commentUid;
        this.commentTwitId = commentTwitId;
        this.comment = comment;
        this.commentDate = commentDate;
    }
}

package com.squeaker.entry.domain.payload.response;

import com.squeaker.entry.domain.entitys.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TwittResponse {
    private Integer twittId;
    private Integer twittUid;
    private String twittContent;
    private Long twittDate;
    private Integer twittLike;
    private List<String> twittImage;
    private List<Comment> comments;
    private Integer likeCount;
    private boolean isLike;
    private boolean deleteAble;

    @Builder
    public TwittResponse(Integer twittId, Integer twittUid, String twittContent, Long twittDate, Integer twittLike, List<String> twittImage, List<Comment> comments, Integer likeCount, boolean isLike, boolean deleteAble) {
        this.twittId = twittId;
        this.twittUid = twittUid;
        this.twittContent = twittContent;
        this.twittDate = twittDate;
        this.twittLike = twittLike;
        this.twittImage = twittImage;
        this.comments = comments;
        this.likeCount = likeCount;
        this.isLike = isLike;
        this.deleteAble = deleteAble;
    }
}

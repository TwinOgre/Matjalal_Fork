package com.proj.Matjalal.domain.review.DTO;

import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.domain.review.entity.Review;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    @ManyToOne
    private Member author;


    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.author = review.getAuthor();
        this.createdDate = review.getCreatedDate();
        this.modifiedDate = review.getModifiedDate();
    }
}

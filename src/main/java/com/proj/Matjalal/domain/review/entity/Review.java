package com.proj.Matjalal.domain.review.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {
    private String content;

    @ManyToOne
    private Member author;

    @ManyToOne
    @JsonIgnore
    private Article article;
}

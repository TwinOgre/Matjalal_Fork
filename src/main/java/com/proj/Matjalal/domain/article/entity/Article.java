package com.proj.Matjalal.domain.article.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proj.Matjalal.domain.imageData.entity.ImageData;
import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.domain.review.entity.Review;
import com.proj.Matjalal.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Article extends BaseEntity {
    private String brand;
    private String subject;
    private String content;
    @ManyToOne
    private Member author;
    @ManyToMany
    private List<Ingredient> ingredients = new ArrayList<>();
    @OneToMany
    @JsonIgnore
    private List<Review> reviews;

// 다수의 첨부 파일을 가질 수 있음
    @JsonIgnore
    @OneToOne(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private ImageData imageData;
}

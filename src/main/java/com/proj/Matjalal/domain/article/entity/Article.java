package com.proj.Matjalal.domain.article.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.domain.review.entity.Review;
import com.proj.Matjalal.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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


}

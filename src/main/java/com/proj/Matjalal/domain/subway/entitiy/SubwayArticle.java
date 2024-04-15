package com.proj.Matjalal.domain.subway.entitiy;

import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SubwayArticle extends BaseEntity {

    private String subject;
    private String content;
    @ManyToOne
    private Member author;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "subwayArticle_ingredient",
            joinColumns = @JoinColumn(name = "subwayArticle_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();
}

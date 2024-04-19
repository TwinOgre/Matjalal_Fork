package com.proj.Matjalal.domain.article.DTO;

import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
import com.proj.Matjalal.domain.member.entity.Member;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ArticleDTO {
    private Long id;
    private String brand;
    private String subject;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    @ManyToOne
    private Member author;
    @ManyToMany
    private List<Ingredient> ingredients = new ArrayList<>();

    public ArticleDTO(Article article) {
        this.id = article.getId();
        this.brand = article.getBrand();
        this.subject = article.getSubject();
        this.content = article.getContent();
        this.author = article.getAuthor();
        this.ingredients = article.getIngredients();
        this.createdDate = article.getCreatedDate();
        this.modifiedDate = article.getModifiedDate();
    }
}

package com.proj.Matjalal.domain.article.repository;

import com.proj.Matjalal.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByBrand(String brand);
}

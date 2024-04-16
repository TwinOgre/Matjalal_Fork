package com.proj.Matjalal.domain.article.repository;

import com.proj.Matjalal.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}

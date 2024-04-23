package com.proj.Matjalal.domain.imageData.repository;

import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.imageData.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
    Optional<ImageData> findByArticle(Article article);
}

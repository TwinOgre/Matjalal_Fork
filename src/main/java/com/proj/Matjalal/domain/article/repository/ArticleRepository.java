package com.proj.Matjalal.domain.article.repository;

import com.proj.Matjalal.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByBrand(String brand);

    // 네이티브쿼리 브랜드 이름을 파라미터로 받아서 랜덤 게시물의 id 만 Long 타입으로 추출!
    @Query(value = "SELECT a.id FROM article AS a WHERE brand = :brand ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Long findRandomArticleByBrand(@Param("brand") String brand);

    @Query(value = "SELECT * FROM article WHERE brand = :brand AND content LIKE CONCAT('%', :keyword, '%')", nativeQuery = true)
    List<Article> findByBrandAndKeyword(@Param("brand") String brand,@Param("keyword") String keyword);

    Optional<Article> getArticleBySubjectAndContent(String articleName, String articleContent);
}

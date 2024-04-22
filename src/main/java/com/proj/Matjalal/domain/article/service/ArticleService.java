package com.proj.Matjalal.domain.article.service;

import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.article.repository.ArticleRepository;
import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> getAll() {
        return this.articleRepository.findAll();
    }

    public Optional<Article> getArticle(Long id) {
        return this.articleRepository.findById(id);
    }

    @Transactional
    public RsData<Article> create(Member author, String subject, String content, List<Ingredient> ingredients, String brand) {
        Article article = Article.builder()
                .author(author)
                .subject(subject)
                .content(content)
                .ingredients(ingredients)
                .brand(brand)
                .build();
        this.articleRepository.save(article);

        return RsData.of("S-3", "등록 성공", article);
    }

    @Transactional
    public RsData<Article> update(Article article, String subject, String content, List<Ingredient> ingredients) {
        Article updatedArticle = article.toBuilder()
                .subject(subject)
                .content(content)
                .ingredients(ingredients)
                .build();
        this.articleRepository.save(updatedArticle);
        return RsData.of("S-4", "%d 번 게시글이 수정되었습니다.".formatted(updatedArticle.getId()), updatedArticle);
    }

    public List<Article> getAllByBrand(String brand) {
        List<Article> articles = this.articleRepository.findAllByBrand(brand);

        return articles;
    }

    @Getter
    @AllArgsConstructor
    public static class DeleteArticle {
        private final Article article;
    }

    @Transactional
    public RsData<Article> delete(Long id) {
        Optional<Article> og = getArticle(id);
        if (og.isEmpty()) {
            return RsData.of("F-3", "%d 번 게시물은 존재하지 않습니다.".formatted(id), null);
        }

        try {
            this.articleRepository.delete(og.get());
        } catch (Exception e) {
            return RsData.of("F-4", "%d 번 게시물 삭제 실패".formatted(og.get().getId()), null);
        }
        return RsData.of("S-5", "%d 번 게시물이 삭제되었습니다.".formatted(og.get().getId()), null);
    }



    public List<Article> searchArticle(String brand, String keyword) {
        return this.articleRepository.findByBrandAndKeyword(brand,keyword);
    }

    public Long findRandomByBrand(String brand) {
        return this.articleRepository.findRandomArticleByBrand(brand);
    }
}

package com.proj.Matjalal.domain.article.service;


import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.article.repository.ArticleRepository;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService{
    private final ArticleRepository articleRepository;

    public Optional<Article> getArticle(Long id) {
        return this.articleRepository.findById(id);

    }

    public List<Article> getList() {
        return this.articleRepository.findAll();
    }

    @Transactional
    public RsData<Article> create(Member member, String title, String content) {
//        if(title ==null || content == null){
//            return RsData.of(
//                    "F-3",
//                    "게시물 등록에 실패했습니다."
//            );
//        }
        Article article;
        try {
            if(title ==null || content == null){
                throw new Exception("가입코드가 틀립니다");
            }
            article = Article.builder()
                    .author(member)
                    .title(title)
                    .content(content)
                    .build();
            this.articleRepository.save(article);
        }catch (Exception e){
            return RsData.of(
                    "F-3",
                    "게시물 등록에 실패했습니다."
            );
        }



        return RsData.of(
                "S-3",
                "게시물이 등록되었습니다.",
                article
        );
    }
    @Transactional
    public RsData<Article> modify(Article article, String title, String content) {
        Article article1 = article.toBuilder()
                .title(title)
                .content(content)
                .build();
        this.articleRepository.save(article1);
        return RsData.of(
                "S-4",
                "%d번 게시글이 수정 되었습니다.".formatted(article.getId()),
                article1
        );
    }

    @Transactional
    public RsData<Article> deleteByArticle(Article article) {
        try {
            this.articleRepository.delete(article);
        }catch (Exception e){
            return RsData.of(
                    "F-5",
                    "%d번 게시글 삭제가 실패했습니다.".formatted(article.getId()),
                    null
            );
        }
        return RsData.of(
                "S-5",
                "%d번 게시글이 삭제 되었습니다.".formatted(article.getId()),
                null
        );
    }

    public Optional<Article> findById(Long id) {
        return this.articleRepository.findById(id);
    }
}

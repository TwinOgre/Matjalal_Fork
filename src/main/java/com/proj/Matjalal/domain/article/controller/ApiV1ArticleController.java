package com.proj.Matjalal.domain.article.controller;


import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.article.service.ArticleService;
import com.proj.Matjalal.global.RsData.RsData;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {
    private final ArticleService articleService;

    @AllArgsConstructor
    @Getter
    public static class ArticlesResponse {
        private final List<Article> articles;
    }

    @GetMapping("")
    public RsData<ArticlesResponse> getArticles() {
        List<Article> articles = this.articleService.getList();


        return RsData.of("S-1", "성공", new ArticlesResponse(articles));
    }

    @AllArgsConstructor
    @Getter
    public static class ArticleResponse {
        private final Article article;
    }

    @GetMapping("/{id}")
    public RsData<ArticleResponse> getArticle(@PathVariable("id") Long id) {
        return this.articleService.getArticle(id).map(article -> RsData.of(
                "S-1",
                "성공",
                new ArticleResponse(article)
        )).orElseGet(() -> RsData.of(
                "F-1",
                "%d 번 게시물은 존재하지 않습니다.".formatted(id),
                null
        ));
    }

    @Data
    public static class WriteRequest {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
    }

    @AllArgsConstructor
    @Getter
    public static class WriteResponse {
        private final Article article;
    }


    @PostMapping("")
    public RsData<WriteResponse> write(@RequestBody WriteRequest writeRequest) {
        RsData<Article> writeRs = this.articleService.create(null, writeRequest.getTitle(), writeRequest.getContent());
        if (writeRs.isFail()) return (RsData) writeRs;

        return RsData.of(
                writeRs.getResultCode(),
                writeRs.getMsg(),
                new WriteResponse(writeRs.getData())
        );
    }

    @Data
    public static class ModifyRequest {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
    }

    @AllArgsConstructor
    @Getter
    public static class ModifyResponse {
        private final Article article;
    }

    @PatchMapping("/{id}")
    public RsData<ModifyResponse> modifyArticle(@PathVariable("id") Long id, @Valid @RequestBody ModifyRequest modifyRequest) {
        Optional<Article> optionalArticle = this.articleService.findById(id);
        if (optionalArticle.isEmpty()) {
            return RsData.of(
                    "F-4",
                    "%s번 게시물은 존재하지 않습니다.".formatted(id),
                    null
            );
        }
        // 회원 권한 체크 canModify();

        RsData<Article> articleRsData = this.articleService.modify(optionalArticle.get(), modifyRequest.getTitle(), modifyRequest.getContent());

        return RsData.of(
                articleRsData.getResultCode(),
                articleRsData.getMsg(),
                new ModifyResponse(articleRsData.getData())
        );
    }

    @AllArgsConstructor
    @Getter
    public static class DeleteResponse {
        private final Article article;
    }

    @DeleteMapping("/{id}")
    public RsData<DeleteResponse> deleteArticle(@PathVariable("id") Long id) {
        Optional<Article> optionalArticle = this.articleService.findById(id);
        if (optionalArticle.isEmpty()) {
            return RsData.of(
                    "F-5",
                    "%d번 게시글이 없어 삭제 실패했습니다.".formatted(id),
                    null
            );
        }
        RsData<Article> deletedRsData = this.articleService.deleteByArticle(optionalArticle.get());
        return RsData.of(
                deletedRsData.getResultCode(),
                deletedRsData.getMsg(),
                new DeleteResponse(deletedRsData.getData())
        );
    }

}

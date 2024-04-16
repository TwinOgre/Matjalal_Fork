package com.proj.Matjalal.domain.article.controller;

import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.article.service.ArticleService;
import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.global.RsData.RsData;
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


    //다건 조회 DTO
    @Getter
    @AllArgsConstructor
    public static class ArticlesResponse {
        private final List<Article> articles;
    }

    //다건 조회
    @GetMapping("")
    private RsData<ArticlesResponse> getArticles() {
        List<Article> articles = this.articleService.getAll();

        return RsData.of("S-1", "성공", new ArticlesResponse(articles));
    }

    //단건 조회 DTO
    @Getter
    @AllArgsConstructor
    public static class ArticleResponse {
        private final Article article;
    }

    //단건 조회
    @GetMapping("/{id}")
    private RsData<ArticleResponse> getArticle(@PathVariable(value = "id") Long id) {
        return this.articleService.getArticle(id).map(article -> RsData.of(
                "S-2",
                "성공",
                new ArticleResponse(article)
        )).orElseGet(() -> RsData.of(
                "F-1"
                , "%d 번 게시물은 존재하지 않습니다.".formatted(id),
                null
        ));
    }

    //게시물 생성 요청 DTO
    @Data
    public static class CreateRequest {
        @NotBlank
        private String subject;
        @NotBlank
        private String content;
        @NotBlank
        private List<Ingredient> ingredients;
        @NotBlank
        private Member author;
        @NotBlank
        private String brand;
    }

    //게시물 생성 완료 응답 DTO
    @Getter
    @AllArgsConstructor
    public static class CreateResponse {
        private final Article article;
    }

    //단건 게시물 생성
    @PostMapping("")
    public RsData<CreateResponse> createArticle(@RequestBody CreateRequest CreateRequest) {
        RsData<Article> createRs = this.articleService.create(null, CreateRequest.getSubject(),
                CreateRequest.getContent(), CreateRequest.getIngredients(), CreateRequest.getBrand());
        if (createRs.isFail()) {
            return (RsData) createRs;
        }
        return RsData.of(createRs.getResultCode(), createRs.getMsg(), new CreateResponse(createRs.getData()));
    }

    //게시물 수정 요청 DTO
    @Data
    public static class UpdateRequest {
        @NotBlank
        private String subject;
        @NotBlank
        private String content;
    }

    //게시물 수정 완료 DTO
    @Getter
    @AllArgsConstructor
    public static class UpdateResponse {
        private final Article article;
    }

    //단건 게시물 수정
    @PatchMapping("/{id}")
    public RsData<UpdateResponse> updateArticle(@PathVariable(value = "id") Long id,
                                                @Valid @RequestBody UpdateRequest updateRequest) {
        Optional<Article> og = this.articleService.getArticle(id);
        if (og.isEmpty()) {
            return RsData.of("F-2", "%d 번 게시물은 존재하지 않습니다.".formatted(id), null);
        }

        // 수정할 회원의 권한 확인 필요
        RsData<Article> updateRs = this.articleService.update(og.get(), updateRequest.getSubject(),
                updateRequest.getContent());
        return RsData.of(updateRs.getResultCode(), updateRs.getMsg(), new UpdateResponse(updateRs.getData()));
    }

    //단건 게시물 삭제 응답 DTO
    @Getter
    @AllArgsConstructor
    public static class DeleteResponse {
        private final Article article;
    }

    //단건 게시물 삭제
    @DeleteMapping("/{id}")
    public RsData<DeleteResponse> deleteArticle(@PathVariable(value = "id") Long id) {

        RsData<Article> deleteRs = this.articleService.delete(id);

        return RsData.of(deleteRs.getResultCode(), deleteRs.getMsg(), new DeleteResponse(deleteRs.getData()));
    }
}

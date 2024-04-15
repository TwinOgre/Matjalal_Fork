package com.proj.Matjalal.domain.gongcha.controller;

import com.proj.Matjalal.domain.gongcha.entity.GongchaArticle;
import com.proj.Matjalal.domain.gongcha.service.GongchaArticleService;
import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
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
@RequestMapping("/api/v1/gongchaArticles")
public class ApiV1GongchaArticleController {
    private final GongchaArticleService gongchaArticleService;


    //다건 조회 DTO
    @Getter
    @AllArgsConstructor
    public static class ArticlesResponse {
        private final List<GongchaArticle> Articles;
    }

    //다건 조회
    @GetMapping("")
    private RsData<ArticlesResponse> getArticles() {
        List<GongchaArticle> Articles = this.gongchaArticleService.getAll();

        return RsData.of("S-1", "성공", new ArticlesResponse(Articles));
    }

    //단건 조회 DTO
    @Getter
    @AllArgsConstructor
    public static class ArticleResponse {
        private final GongchaArticle Article;
    }

    //단건 조회
    @GetMapping("/{id}")
    private RsData<ArticleResponse> getArticle(@PathVariable(value = "id") Long id) {
        return this.gongchaArticleService.getArticle(id).map(gongchaArticle -> RsData.of(
                "S-2",
                "성공",
                new ArticleResponse(gongchaArticle)
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
    }

    //게시물 생성 완료 응답 DTO
    @Getter
    @AllArgsConstructor
    public static class CreateResponse {
        private final GongchaArticle Article;
    }

    //단건 게시물 생성
    @PostMapping("")
    public RsData<CreateResponse> createArticle(@RequestBody CreateRequest CreateRequest) {
        RsData<GongchaArticle> createRs = this.gongchaArticleService.create(null, CreateRequest.getSubject(),
                CreateRequest.getContent(), CreateRequest.ingredients);
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
        private final GongchaArticle article;
    }

    //단건 게시물 수정
    @PatchMapping("/{id}")
    public RsData<UpdateResponse> updateArticle(@PathVariable(value = "id") Long id,
                                                @Valid @RequestBody UpdateRequest updateRequest) {
        Optional<GongchaArticle> og = this.gongchaArticleService.getArticle(id);
        if (og.isEmpty()) {
            return RsData.of("F-2", "%d 번 게시물은 존재하지 않습니다.".formatted(id), null);
        }

        // 수정할 회원의 권한 확인 필요
        RsData<GongchaArticle> updateRs = this.gongchaArticleService.update(og.get(), updateRequest.getSubject(),
                updateRequest.getContent());
        return RsData.of(updateRs.getResultCode(), updateRs.getMsg(), new UpdateResponse(updateRs.getData()));
    }

    //단건 게시물 삭제 응답 DTO
    @Getter
    @AllArgsConstructor
    public static class DeleteResponse {
        private final GongchaArticle article;
    }

    //단건 게시물 삭제
    @DeleteMapping("/{id}")
    public RsData<DeleteResponse> deleteArticle(@PathVariable(value = "id") Long id) {

        RsData<GongchaArticle> deleteRs = this.gongchaArticleService.delete(id);

        return RsData.of(deleteRs.getResultCode(), deleteRs.getMsg(), new DeleteResponse(deleteRs.getData()));
    }
}

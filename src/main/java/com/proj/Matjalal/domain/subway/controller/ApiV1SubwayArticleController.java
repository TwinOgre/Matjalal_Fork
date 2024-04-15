package com.proj.Matjalal.domain.subway.controller;


import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
import com.proj.Matjalal.domain.subway.entitiy.SubwayArticle;
import com.proj.Matjalal.domain.subway.service.SubwayArticleService;
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
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subwayArticles")
public class ApiV1SubwayArticleController {
    private final SubwayArticleService subwayArticleService;

    @AllArgsConstructor
    @Getter
    public static class SubwayArticlesResponse {
        private final List<SubwayArticle> SubwayArticles;
    }

    @GetMapping("")
    public RsData<SubwayArticlesResponse> getArticles() {
        List<SubwayArticle> subwayArticles = this.subwayArticleService.getList();


        return RsData.of("S-1", "성공", new SubwayArticlesResponse(subwayArticles));
    }

    @AllArgsConstructor
    @Getter
    public static class SubwayArticleResponse {
        private final SubwayArticle subwayArticle;
    }

    @GetMapping("/{id}")
    public RsData<SubwayArticleResponse> getArticle(@PathVariable("id") Long id) {
        return this.subwayArticleService.getArticle(id).map(subwayArticle -> RsData.of(
                "S-1",
                "성공",
                new SubwayArticleResponse(subwayArticle)
        )).orElseGet(() -> RsData.of(
                "F-1",
                "%d 번 게시물은 존재하지 않습니다.".formatted(id),
                null
        ));
    }

    @Data
    public static class WriteRequest {
        @NotBlank
        private String subject;
        @NotBlank
        private String content;
        @NotBlank
        private List<Ingredient> ingredients;
    }

    @AllArgsConstructor
    @Getter
    public static class WriteResponse {
        private final SubwayArticle subwayArticle;
    }


    @PostMapping("")
    public RsData<WriteResponse> write(@RequestBody WriteRequest writeRequest) {
        RsData<SubwayArticle> writeRs = this.subwayArticleService.create(null, writeRequest.getSubject(), writeRequest.getContent(), writeRequest.getIngredients());
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
        private String subject;
        @NotBlank
        private String content;
        @NotBlank
        private List<Ingredient> ingredients;
    }

    @AllArgsConstructor
    @Getter
    public static class ModifyResponse {
        private final SubwayArticle subwayArticle;
    }

    @PatchMapping("/{id}")
    public RsData<ModifyResponse> modifySubwayArticle(@PathVariable("id") Long id, @Valid @RequestBody ModifyRequest modifyRequest) {
        Optional<SubwayArticle> optionalSubwayArticle = this.subwayArticleService.findById(id);
        if (optionalSubwayArticle.isEmpty()) {
            return RsData.of(
                    "F-4",
                    "%s번 게시물은 존재하지 않습니다.".formatted(id),
                    null
            );
        }
        // 회원 권한 체크 canModify() 필요;

        RsData<SubwayArticle> subwayArticleRsData = this.subwayArticleService.modify(optionalSubwayArticle.get(), modifyRequest.getSubject(), modifyRequest.getContent(), modifyRequest.getIngredients());

        return RsData.of(
                subwayArticleRsData.getResultCode(),
                subwayArticleRsData.getMsg(),
                new ModifyResponse(subwayArticleRsData.getData())
        );
    }

    @AllArgsConstructor
    @Getter
    public static class DeleteResponse {
        private final SubwayArticle subwayArticle;
    }

    @DeleteMapping("/{id}")
    public RsData<DeleteResponse> deleteArticle(@PathVariable("id") Long id) {
        Optional<SubwayArticle> optionalSubwayArticle = this.subwayArticleService.findById(id);
        if (optionalSubwayArticle.isEmpty()) {
            return RsData.of(
                    "F-5",
                    "%d번 게시글이 없어 삭제 실패했습니다.".formatted(id),
                    null
            );
        }
        RsData<SubwayArticle> deletedRsData = this.subwayArticleService.deleteBySubwayArticle(optionalSubwayArticle.get());
        return RsData.of(
                deletedRsData.getResultCode(),
                deletedRsData.getMsg(),
                new DeleteResponse(deletedRsData.getData())
        );
    }
}

package com.proj.Matjalal.domain.article.controller;

import com.proj.Matjalal.domain.article.DTO.ArticleDTO;
import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.article.service.ArticleService;
import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.domain.member.service.MemberService;
import com.proj.Matjalal.domain.review.entity.Review;
import com.proj.Matjalal.domain.review.service.ReviewService;
import com.proj.Matjalal.global.RsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
@Tag(name = "게시글", description = "게시글 관련 API")
public class ApiV1ArticleController {
    private final ArticleService articleService;
    private final ReviewService reviewService;
    private final MemberService memberService;


    //다건 조회 DTO
    @Getter
    @AllArgsConstructor
    public static class ArticlesResponse {
        private final List<ArticleDTO> articleDTOs;
    }

    //다건 조회
    @GetMapping("")
    @Operation(summary = "게시글 다건 조회", description = "서브웨이, 공차 게시글 전부 조회")
    public RsData<ArticlesResponse> getArticles() {
        List<Article> articles = this.articleService.getAll();
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (Article article : articles) {
            articleDTOS.add(new ArticleDTO(article));
        }

        return RsData.of("S-1", "성공", new ArticlesResponse(articleDTOS));
    }

    // 브랜드별 게시물 다건 조회 DTO
    @Getter
    @AllArgsConstructor
    public static class BrandArticlesResponse {
        private final List<ArticleDTO> articles;
    }


    //브랜드별 게시물 다건 조회
    @GetMapping("/{brand}/brands")
    @Operation(summary = "브랜드별 게시글 다건 조회", description = "게시글 브랜드별 리스트 조회: subway => 서브웨이, gongcha => 공차.")
    public RsData<BrandArticlesResponse> getArticlesByBrand(@Parameter(description = "다건 조회할 게시글의 브랜드", example = "subway") @PathVariable(value = "brand") String brand) {
        List<Article> articles = this.articleService.getAllByBrand(brand);
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (Article article : articles) {
            articleDTOS.add(new ArticleDTO(article));
        }

        return RsData.of("S-6", "성공", new BrandArticlesResponse(articleDTOS));
    }

    //브랜드별 랜덤 게시물 단건 조회
    @GetMapping("/{brand}/random")
    @Operation(summary = "브랜드별 게시글 단건 조회", description = "게시글 브랜드별 단건 랜덤 조회: subway => 서브웨이 게시글 중 하나 랜덤 조회, gongcha => 공차 게시글 중 하나 랜덤 조회. ")
    public RsData<Long> getRandomArticleByBrand(@Parameter(description = "단건 랜덤 조회할 게시글의 브랜드", example = "subway") @PathVariable(value = "brand") String brand) {
        Long id = this.articleService.findRandomByBrand(brand);

        return RsData.of("S-7", "%s 랜덤 추출 성공".formatted(brand), id);
    }

    //단건 조회 DTO
    @Getter
    @AllArgsConstructor
    public static class ArticleResponse {
        private final ArticleDTO articleDTO;
    }

    //단건 조회
    @GetMapping("/{id}")
    @Operation(summary = "게시글 단건 조회", description = "게시글 단건 조회: {id} 게시글 하나 조회. ")
    public RsData<ArticleResponse> getArticle(@Parameter(description = "조회할 게시글의 아이디", example = "article_id") @PathVariable(value = "id") Long id) {
        return this.articleService.getArticle(id).map(article -> RsData.of(
                "S-2",
                "성공",
                new ArticleResponse(new ArticleDTO(article))
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
    @Operation(summary = "게시글 등록", description = "게시글 등록: 제목, 내용, 재료리스트, 회원, 브랜드 필요. ")
    public RsData<CreateResponse> createArticle(@RequestBody CreateRequest createRequest) {
        RsData<Article> createRs = this.articleService.create(createRequest.getAuthor(), createRequest.getSubject(),
                createRequest.content, createRequest.getIngredients(), createRequest.brand);
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

        private List<Ingredient> ingredients;
    }

    //게시물 수정 완료 DTO
    @Getter
    @AllArgsConstructor
    public static class UpdateResponse {
        private final Article article;
    }

    //단건 게시물 수정
    @PatchMapping("/{id}")
    @Operation(summary = "게시글 수정", description = "게시글 수정: 제목, 내용, 재료리스트. ")
    public RsData<UpdateResponse> updateArticle(@Parameter(description = "수정할 게시글의 아이디", example = "article_id") @PathVariable(value = "id") Long id,
                                                @Valid @RequestBody UpdateRequest updateRequest) {
        Optional<Article> og = this.articleService.getArticle(id);
        if (og.isEmpty()) {
            return RsData.of("F-2", "%d 번 게시물은 존재하지 않습니다.".formatted(id), null);
        }

        // 수정할 회원의 권한 확인 필요
        RsData<Article> updateRs = this.articleService.update(og.get(), updateRequest.getSubject(),
                updateRequest.getContent(), updateRequest.getIngredients());
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
    @Operation(summary = "게시글 삭제", description = "{id} 게시글 삭제")
    public RsData<DeleteResponse> deleteArticle(@Parameter(description = "삭제할 게시글의 아이디", example = "article_id") @PathVariable(value = "id") Long id) {
        List<Review> reviews = this.reviewService.findAllByArticleId(id);
        for (Review review : reviews) {
            this.reviewService.delete(review.getId());
        }
        RsData<Article> deleteRs = this.articleService.delete(id);

        return RsData.of(deleteRs.getResultCode(), deleteRs.getMsg(), new DeleteResponse(deleteRs.getData()));
    }

    // 게시물 검색 요청 DTO (검색어로)


    @Data
    public static class ArticleSearchRequest {
        private String brand;
        private String keyword;
    }

    @Getter
    @AllArgsConstructor
    public static class ArticleSearchResponse {
        private final List<Article> articles;
    }

    @GetMapping("/search")
    @Operation(summary = "게시글 검색 다건 조회", description = "키워드에 맞는 게시글 검색하여 해당 조건에 맞는 게시글들 조회.")
    public RsData<ArticleSearchResponse> searchArticleByKeyword(@Parameter(description = "검색할 게시글의 브랜드", example = "subway") @RequestParam(value = "brand") String brand,
                                                                @Parameter(description = "검색할 게시글의 키워드", example = "keyword") @RequestParam(value = "keyword") String keyword) {
        List<Article> articleList = this.articleService.searchArticle(brand, keyword);

        return RsData.of("S-6", "성공", new ArticleSearchResponse(articleList));
    }
}

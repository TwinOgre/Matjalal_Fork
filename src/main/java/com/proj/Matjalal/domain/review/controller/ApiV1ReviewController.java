package com.proj.Matjalal.domain.review.controller;

import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.ingredient.DTO.IngredientDTO;
import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
import com.proj.Matjalal.domain.review.DTO.ReviewDTO;
import com.proj.Matjalal.domain.review.entity.Review;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.domain.review.service.ReviewService;
import com.proj.Matjalal.global.RsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ApiV1ReviewController {
    private final ReviewService reviewService;

    @Getter
    @AllArgsConstructor
    public static class ReviewsResponse {
        private final List<ReviewDTO> reviews;
    }

    //다건 조회
    @GetMapping("")
    public RsData<ReviewsResponse> getReviews() {
        List<Review> reviews = this.reviewService.getAll();
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for (Review review : reviews) {
            reviewDTOS.add(new ReviewDTO(review));
        }

        return RsData.of("S-1", "성공", new ReviewsResponse(reviewDTOS));
    }


    //단건 조회 DTO
    @Getter
    @AllArgsConstructor
    public static class ReviewResponse {
        private final ReviewDTO reviewDTO;
    }

    //단건 조회
    @GetMapping("/{id}")
    public RsData<ReviewResponse> getReview(@PathVariable(value = "id") Long id) {
        return this.reviewService.getReview(id).map(review -> RsData.of(
                "S-2",
                "성공",
                new ReviewResponse(new ReviewDTO(review))
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
        private String content;
        @NotBlank
        private Member author;
        @NotBlank
        private Article article;
    }

    //게시물 생성 완료 응답 DTO
    @Getter
    @AllArgsConstructor
    public static class CreateResponse {
        private final ReviewDTO reviewDTO;
    }

    //단건 게시물 생성
    @PostMapping("")
    public RsData<CreateResponse> createReview(@RequestBody CreateRequest createRequest) {
        RsData<Review> createRs = this.reviewService.create(createRequest.getAuthor(), createRequest.getArticle(),
                createRequest.getContent());
        if (createRs.isFail()) {
            return (RsData) createRs;
        }
        return RsData.of(createRs.getResultCode(), createRs.getMsg(), new CreateResponse(new ReviewDTO(createRs.getData())));
    }

    //게시물 수정 요청 DTO
    @Data
    public static class UpdateRequest {
        @NotBlank
        private String content;
    }

    //게시물 수정 완료 DTO
    @Getter
    @AllArgsConstructor
    public static class UpdateResponse {
        private final ReviewDTO reviewDTO;
    }

    //단건 게시물 수정
    @PatchMapping("/{id}")
    public RsData<UpdateResponse> updatereview(@PathVariable(value = "id") Long id,
                                               @Valid @RequestBody UpdateRequest updateRequest) {
        Optional<Review> optionalReview = this.reviewService.getReview(id);
        if (optionalReview.isEmpty()) {
            return RsData.of("F-2", "%d 번 게시물은 존재하지 않습니다.".formatted(id), null);
        }

        // 수정할 회원의 권한 확인 필요
        RsData<Review> updateRs = this.reviewService.update(optionalReview.get(),
                updateRequest.getContent());
        return RsData.of(updateRs.getResultCode(), updateRs.getMsg(), new UpdateResponse(new ReviewDTO(updateRs.getData())));
    }

    //단건 게시물 삭제 응답 DTO
    @Getter
    @AllArgsConstructor
    public static class DeleteResponse {
        private final ReviewDTO reviewDTO;
    }

    //단건 게시물 삭제
    @DeleteMapping("/{id}")
    public RsData<DeleteResponse> deleteReview(@PathVariable(value = "id") Long id) {

        RsData<Review> deleteRs = this.reviewService.delete(id);

        return RsData.of(deleteRs.getResultCode(), deleteRs.getMsg(), new DeleteResponse(null));
    }

}

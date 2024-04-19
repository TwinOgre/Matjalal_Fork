package com.proj.Matjalal.domain.review.service;

import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.domain.review.entity.Review;
import com.proj.Matjalal.domain.review.repository.ReviewRepository;
import com.proj.Matjalal.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getAll() {
        return this.reviewRepository.findAll();
    }

    public Optional<Review> getReview(Long id) {
        return this.reviewRepository.findById(id);
    }

    @Transactional
    public RsData<Review> create(Member author, Article article, String content) {
        if(author == null){
            return RsData.of(
                    "F-3M",
                    "로그인해주세요",
                    null
            );
        } if(article == null){
            return RsData.of(
                    "F-3A",
                    "잘못된 접근입니다.",
                    null
            );
        }
        Review review = Review.builder()
                .author(author)
                .content(content)
                .article(article)
                .build();
        this.reviewRepository.save(review);

        return RsData.of("S-3", "등록 성공", review);
    }

    @Transactional
    public RsData<Review> update(Review review, String content) {
        Review updatedReview = review.toBuilder()
                .content(content)
                .build();
        this.reviewRepository.save(updatedReview);
        return RsData.of("S-4", "%d 번 게시글이 수정되었습니다.".formatted(updatedReview.getId()), updatedReview);
    }


    @Transactional
    public RsData<Review> delete(Long id) {
        Optional<Review> optionalReview = getReview(id);
        if (optionalReview.isEmpty()) {
            return RsData.of("F-3", "%d 번 게시물은 존재하지 않습니다.".formatted(id), null);
        }

        try {
            this.reviewRepository.delete(optionalReview.get());
        } catch (Exception e) {
            return RsData.of("F-4", "%d 번 게시물 삭제 실패".formatted(optionalReview.get().getId()), null);
        }
        return RsData.of("S-5", "%d 번 게시물이 삭제되었습니다.".formatted(optionalReview.get().getId()), null);
    }
}

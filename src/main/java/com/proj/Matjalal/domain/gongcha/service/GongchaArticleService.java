package com.proj.Matjalal.domain.gongcha.service;

import com.proj.Matjalal.domain.gongcha.entity.GongchaArticle;
import com.proj.Matjalal.domain.gongcha.repository.GongchaArticleRepository;
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
public class GongchaArticleService {
    private final GongchaArticleRepository gongchaArticleRepository;

    public List<GongchaArticle> getAll() {
        return this.gongchaArticleRepository.findAll();
    }

    public Optional<GongchaArticle> getArticle(Long id) {
        return this.gongchaArticleRepository.findById(id);
    }

    @Transactional
    public RsData<GongchaArticle> create(Member author, String subject, String content, List<Ingredient> ingredients) {
        GongchaArticle gongchaArticle = GongchaArticle.builder()
                .author(author)
                .subject(subject)
                .content(content)
                .ingredients(ingredients)
                .build();
        this.gongchaArticleRepository.save(gongchaArticle);

        return RsData.of("S-3", "등록 성공", gongchaArticle);
    }

    @Transactional
    public RsData<GongchaArticle> update(GongchaArticle gongchaArticle, String subject, String content) {
        GongchaArticle updatedArticle = gongchaArticle.toBuilder()
                .subject(subject)
                .content(content)
                .build();
        this.gongchaArticleRepository.save(updatedArticle);
        return RsData.of("S-4", "%d 번 게시글이 수정되었습니다.".formatted(updatedArticle.getId()), updatedArticle);
    }

    @Getter
    @AllArgsConstructor
    public static class DeleteArticle {
        private final GongchaArticle article;
    }

    @Transactional
    public RsData<GongchaArticle> delete(Long id) {
        Optional<GongchaArticle> og = getArticle(id);
        if (og.isEmpty()) {
            return RsData.of("F-3", "%d 번 게시물은 존재하지 않습니다.".formatted(id), null);
        }

        try {
            this.gongchaArticleRepository.delete(og.get());
        } catch (Exception e) {
            return RsData.of("F-4", "%d 번 게시물 삭제 실패".formatted(og.get().getId()), null);
        }
        return RsData.of("S-5", "%d 번 게시물이 삭제되었습니다.".formatted(og.get().getId()), og.get());
    }
}

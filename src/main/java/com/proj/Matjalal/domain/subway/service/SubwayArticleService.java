package com.proj.Matjalal.domain.subway.service;

import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.domain.subway.entitiy.SubwayArticle;
import com.proj.Matjalal.domain.subway.repository.SubwayRepository;
import com.proj.Matjalal.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubwayArticleService {
    private final SubwayRepository subwayRepository;

    public List<SubwayArticle> getList() {
        return this.subwayRepository.findAll();
    }

    public Optional<SubwayArticle> getArticle(Long id) {
        return this.subwayRepository.findById(id);
    }

    public RsData<SubwayArticle> create(Member member, String subject, String content, List<Ingredient> ingredients) {
        SubwayArticle subwayArticle = SubwayArticle.builder()
                .subject(subject)
                .content(content)
                .author(member)
                .ingredients(ingredients)
                .build();

        this.subwayRepository.save(subwayArticle);

        return RsData.of(
                "S-3",
                "subway게시글 등록 성공",
                 subwayArticle
        );

    }

    public Optional<SubwayArticle> findById(Long id) {
        return this.subwayRepository.findById(id);
    }

    public RsData<SubwayArticle> modify(SubwayArticle subwayArticle, String subject, String content, List<Ingredient> ingredients) {
        SubwayArticle subwayArticle1 = subwayArticle.toBuilder()
                .subject(subject)
                .content(content)
                .ingredients(ingredients)
                .build();
        this.subwayRepository.save(subwayArticle1);

        return RsData.of(
                "S-4",
                "subway 게시글 수정 성공",
                subwayArticle1
        );

    }

    public RsData<SubwayArticle> deleteBySubwayArticle(SubwayArticle subwayArticle) {
        this.subwayRepository.delete(subwayArticle);

        return RsData.of(
                "S-5",
                "subway 게시글 삭제 성공",
                null
        );
    }
}

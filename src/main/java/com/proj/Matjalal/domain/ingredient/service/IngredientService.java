package com.proj.Matjalal.domain.ingredient.service;

import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
import com.proj.Matjalal.domain.ingredient.repository.IngredientRepository;
import com.proj.Matjalal.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public List<Ingredient> getList() {
        return this.ingredientRepository.findAll();
    }

    @Transactional
    public RsData<Ingredient> create(String name, String type){
        Ingredient ingredient;

        try {
            if(name ==null || type == null){
                throw new Exception("이름 또는 타입이 없습니다.");
            }
            ingredient = Ingredient.builder()
                    .name(name)
                    .type(type)
                    .build();
            this.ingredientRepository.save(ingredient);
        }catch (Exception e){
            return RsData.of(
                    "FI-3",
                    "재료 등록에 실패했습니다.",
                    null
            );
        }
        return RsData.of(
                "SI-3",
                "재료 생성에 성공했습니다.",
                ingredient
        );
    }



    public Optional<Ingredient> getIngredient(Long id) {
        return this.ingredientRepository.findById(id);
    }

    public Optional<Ingredient> findById(Long id) {
        return this.ingredientRepository.findById(id);
    }

    @Transactional
    public RsData<Ingredient> modify(Ingredient ingredient, String name, String type) {
        Ingredient ingredient1 = ingredient.toBuilder()
                .name(name)
                .type(type)
                .build();
        this.ingredientRepository.save(ingredient1);
        return RsData.of(
                "SI-4",
                "%d번 재료가 수정 되었습니다.".formatted(ingredient.getId()),
                ingredient1
        );
    }

    @Transactional
    public RsData<Ingredient> deleteByIngredient(Ingredient ingredient) {
        try {
            this.ingredientRepository.delete(ingredient);
        }catch (Exception e){
            return RsData.of(
                    "FI-5",
                    "%d번 게시글 삭제가 실패했습니다.".formatted(ingredient.getId()),
                    null
            );
        }
        return RsData.of(
                "SI-5",
                "%d번 게시글이 삭제 되었습니다.".formatted(ingredient.getId()),
                null
        );

    }

    public List<Ingredient> getListByType(String type) {
        return this.ingredientRepository.findByTypeContaining(type);
    }
}

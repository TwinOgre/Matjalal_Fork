package com.proj.Matjalal.domain.ingredient.controller;

import com.proj.Matjalal.domain.article.DTO.ArticleDTO;
import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.ingredient.DTO.IngredientDTO;
import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
import com.proj.Matjalal.domain.ingredient.service.IngredientService;
import com.proj.Matjalal.global.RsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/ingredients")
@Tag(name = "재료", description = "재료 관련 API")
public class ApiV1IngredientController {
    private final IngredientService ingredientService;

    @Data
    public static class IngredientsResponse {
        private final List<IngredientDTO> ingredients;
    }

    // 다건 요청
    @GetMapping("")
    @Operation(summary = "재료 다건 조회", description = "모든 재료 다건 조회.")
    public RsData<IngredientsResponse> getIngredients() {
        List<Ingredient> ingredients = this.ingredientService.getList();
        List<IngredientDTO> ingredientDTOS = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientDTOS.add(new IngredientDTO(ingredient));
        }


        return RsData.of(
                "SI-1",
                "다건 요청 성공",
                new IngredientsResponse(ingredientDTOS)
        );
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "재료 타입별 다건 조회", description = "타입에 맞는 재료들 다건 조회: e.g. {bread} => bread 타입의 재료들 전부 조회.")
    public RsData<IngredientsResponse> getIngredientsByType(@Parameter(description = "다건 조회할 재료의 타입", example = "bread") @PathVariable("type") String type) {
        List<Ingredient> ingredients = this.ingredientService.getListByType(type);
        List<IngredientDTO> ingredientDTOS = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientDTOS.add(new IngredientDTO(ingredient));
        }
        return RsData.of(
                "SI-2",
                "타입별 다건 요청 성공",
                new IngredientsResponse(ingredientDTOS)
        );

    }

    @AllArgsConstructor
    @Getter
    public static class IngredientResponse {
        private final IngredientDTO ingredientDTO;
    }

    @GetMapping("/{id}")
    @Operation(summary = "재료 단건 조회", description = "{id} 재료 단건 조회")
    public RsData<IngredientResponse> getIngredient(@Parameter(description = "조회할 재료의 아이디", example = "ingredient_id") @PathVariable("id") Long id) {
        return this.ingredientService.getIngredient(id).map(ingredient -> RsData.of(
                "SI-2",
                "성공",
                new IngredientResponse(new IngredientDTO(ingredient))
        )).orElseGet(() -> RsData.of(
                "FI-2",
                "%d 번 재료는 존재하지 않습니다.".formatted(id),
                null
        ));
    }

    @Data
    public static class CreateRequest {
        @NotBlank
        private String name;
        @NotBlank
        private String type;
    }

    @AllArgsConstructor
    @Getter
    public static class CreateResponce {
        private final IngredientDTO ingredientDTO;
    }


    @PostMapping("")
    @Operation(summary = "재료 등록", description = "재료 등록: 재료이름(name), 재료타입(type) 필요")
    public RsData<CreateResponce> create(@RequestBody CreateRequest createRequest) {
        RsData<Ingredient> createRS = this.ingredientService.create(createRequest.getName(), createRequest.getType());
        if (createRS.isFail()) return (RsData) createRS;

        return RsData.of(
                createRS.getResultCode(),
                createRS.getMsg(),
                new CreateResponce(new IngredientDTO(createRS.getData()))
        );
    }

    @Data
    public static class ModifyRequest {
        @NotBlank
        private String name;
        @NotBlank
        private String type;
    }

    @AllArgsConstructor
    @Getter
    public static class ModifyResponce {
        private final IngredientDTO ingredientDTO;
    }

    @PatchMapping("/{id}")
    @Operation(summary = "재료 수정", description = "재료 수정: 재료이름(name), 재료타입(type)")
    public RsData<ModifyResponce> modifyIngredient(@Parameter(description = "수정할 재료의 아이디", example = "ingredient_id") @PathVariable("id") Long id, @Valid @RequestBody ModifyRequest modifyRequest) {
        Optional<Ingredient> optionalIngredient = this.ingredientService.findById(id);
        if (optionalIngredient.isEmpty()) {
            return RsData.of(
                    "FI-4",
                    "%s번 재료는 존재하지 않습니다.".formatted(id),
                    null
            );
        }

        RsData<Ingredient> ingredientRsData = this.ingredientService.modify(optionalIngredient.get(), modifyRequest.getName(), modifyRequest.getType());

        return RsData.of(
                ingredientRsData.getResultCode(),
                ingredientRsData.getMsg(),
                new ModifyResponce(new IngredientDTO(ingredientRsData.getData()))
        );
    }

    @AllArgsConstructor
    @Getter
    public static class DeleteResponce {
        private final IngredientDTO ingredientDTO;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "게시글 삭제", description = "{id} 게시글 삭제")
    public RsData<DeleteResponce> deleteIngredient(@Parameter(description = "삭제할 재료의 아이디", example = "ingredient_id") @PathVariable("id") Long id) {
        Optional<Ingredient> optionalIngredient = this.ingredientService.findById(id);
        if (optionalIngredient.isEmpty()) {
            return RsData.of(
                    "FI-5",
                    "%d번 재료가 없어 삭제 실패 했습니다.".formatted(id),
                    null
            );
        }
        RsData<Ingredient> deletedRsData = this.ingredientService.deleteByIngredient(optionalIngredient.get());
        return RsData.of(
                deletedRsData.getResultCode(),
                deletedRsData.getMsg(),
                new DeleteResponce(null)
        );
    }
}

package com.proj.Matjalal.domain.ingredient.DTO;

import com.proj.Matjalal.domain.ingredient.entity.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class IngredientDTO {
    private Long id;
    private String name;
    private String type;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public IngredientDTO(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.type = ingredient.getType();
        this.createdDate = ingredient.getCreatedDate();
        this.modifiedDate = ingredient.getModifiedDate();
    }
}

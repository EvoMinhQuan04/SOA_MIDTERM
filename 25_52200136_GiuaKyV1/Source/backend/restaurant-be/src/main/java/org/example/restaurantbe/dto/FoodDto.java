package org.example.restaurantbe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.restaurantbe.model.Food;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodDto {
    private Long id;

    private String name;

    private String category;

    private Long price;

    private String image;

    private Integer status;

    public static FoodDto from(Food food) {
        return new ModelMapper().map(food, FoodDto.class);
    }

    public Food toDomain() {
        return new ModelMapper().map(this, Food.class);
    }
}

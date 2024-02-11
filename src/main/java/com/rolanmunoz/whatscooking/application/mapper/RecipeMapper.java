package com.rolanmunoz.whatscooking.application.mapper;

import com.rolanmunoz.whatscooking.application.dto.RecipeDTO;
import com.rolanmunoz.whatscooking.domain.entity.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RecipeMapper extends EntityMapper<RecipeDTO, Recipe> {
    @Mapping(source = "user.id", target = "userId")
    RecipeDTO toDto(Recipe entity);
}

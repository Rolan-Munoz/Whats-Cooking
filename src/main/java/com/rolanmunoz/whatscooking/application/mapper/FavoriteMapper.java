package com.rolanmunoz.whatscooking.application.mapper;

import com.rolanmunoz.whatscooking.application.dto.FavoriteDTO;
import com.rolanmunoz.whatscooking.domain.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, RecipeMapper.class})
public interface FavoriteMapper extends EntityMapper<FavoriteDTO, Favorite> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "recipe.id", target = "recipeId")
    @Override
    FavoriteDTO toDto(Favorite favorite);


    @Override
    Favorite toEntity(FavoriteDTO favoriteDTO);
}

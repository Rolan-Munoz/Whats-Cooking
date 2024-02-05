package com.rolanmunoz.whatscooking.application.mapper;

import com.rolanmunoz.whatscooking.application.dto.CommentDTO;
import com.rolanmunoz.whatscooking.domain.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, RecipeMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment>{

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "recipe.id", target = "recipeId")
    @Override
    CommentDTO toDto(Comment comment);

    // No necesitas mapear userId y recipeId en toEntity porque
    // ya los estás manejando en tu servicio
    @Override
    Comment toEntity(CommentDTO commentDTO);
}

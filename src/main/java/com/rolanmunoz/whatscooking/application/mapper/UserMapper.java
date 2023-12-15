package com.rolanmunoz.whatscooking.application.mapper;

import com.rolanmunoz.whatscooking.application.dto.UserDTO;
import com.rolanmunoz.whatscooking.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User> {
    @Mapping(source = "roles", target = "roles")
    @Mapping(source = "recipes", target = "recipes")
    UserDTO toDto(User user);

    @Mapping(source = "roles", target = "roles")
    @Mapping(source = "recipes", target = "recipes")
    User toEntity(UserDTO userDTO);




    List<UserDTO> toDto(List<User> users);

    List<User> toEntity(List<UserDTO> userDTOs);
}

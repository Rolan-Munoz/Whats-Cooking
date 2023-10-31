package com.rolanmunoz.whatscooking.application.mapper;

import com.rolanmunoz.whatscooking.application.dto.UserDTO;
import com.rolanmunoz.whatscooking.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {

}

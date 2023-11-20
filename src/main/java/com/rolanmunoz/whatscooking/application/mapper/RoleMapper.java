package com.rolanmunoz.whatscooking.application.mapper;

import com.rolanmunoz.whatscooking.application.dto.RoleDTO;
import com.rolanmunoz.whatscooking.domain.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
}

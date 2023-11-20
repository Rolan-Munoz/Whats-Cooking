package com.rolanmunoz.whatscooking.application.service;

import com.rolanmunoz.whatscooking.application.dto.RoleDTO;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleDTO> getAllRoles();
    Optional<RoleDTO> getRoleById(Long idRole);
    RoleDTO saveRole(RoleDTO role);
    void deleteRole(Long idRole);
    Optional<RoleDTO> findByName(String name);
}

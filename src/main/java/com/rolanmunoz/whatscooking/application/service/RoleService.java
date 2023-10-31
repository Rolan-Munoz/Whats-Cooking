package com.rolanmunoz.whatscooking.application.service;

import com.rolanmunoz.whatscooking.domain.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();
    Optional<Role> getRoleById(Long idRole);
    Role saveRole(Role role);
    void deleteRole(Long idRole);
}

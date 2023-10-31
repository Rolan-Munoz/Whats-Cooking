package com.rolanmunoz.whatscooking.application.service.impl;

import com.rolanmunoz.whatscooking.application.service.RoleService;
import com.rolanmunoz.whatscooking.domain.entity.Role;
import com.rolanmunoz.whatscooking.domain.persistence.RolePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RolePersistence rolePersistence;

    @Autowired
    public RoleServiceImpl(RolePersistence rolePersistence) {
        this.rolePersistence = rolePersistence;
    }

    @Override
    public List<Role> getAllRoles() {
        return this.rolePersistence.getAllRoles();
    }

    @Override
    public Optional<Role> getRoleById(Long idRole) {
        return this.rolePersistence.getRoleById(idRole);
    }

    @Override
    public Role saveRole(Role role) {
        return this.rolePersistence.saveRole(role);
    }

    @Override
    public void deleteRole(Long idRole) {
        this.rolePersistence.deleteRole(idRole);
    }
}

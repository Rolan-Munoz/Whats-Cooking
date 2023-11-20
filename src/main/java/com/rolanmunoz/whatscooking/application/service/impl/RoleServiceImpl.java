package com.rolanmunoz.whatscooking.application.service.impl;

import com.rolanmunoz.whatscooking.application.dto.RoleDTO;
import com.rolanmunoz.whatscooking.application.mapper.RoleMapper;
import com.rolanmunoz.whatscooking.application.service.RoleService;
import com.rolanmunoz.whatscooking.domain.entity.Role;
import com.rolanmunoz.whatscooking.domain.persistence.RolePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RolePersistence rolePersistence;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RolePersistence rolePersistence, RoleMapper roleMapper) {
        this.rolePersistence = rolePersistence;
        this.roleMapper = roleMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = this.rolePersistence.getAllRoles();
        return roles.stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<RoleDTO> getRoleById(Long idRole) {
        Optional<Role> role = this.rolePersistence.getRoleById(idRole);
        return role.map(roleMapper::toDto);
    }

    @Transactional
    @Override
    public RoleDTO saveRole(RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        Role savedRole = this.rolePersistence.saveRole(role);
        return roleMapper.toDto(savedRole);
    }
    @Transactional
    @Override
    public void deleteRole(Long idRole) {
        this.rolePersistence.deleteRole(idRole);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<RoleDTO> findByName(String name) {
        Optional<Role> role = rolePersistence.findByName(name);
        return role.map(roleMapper::toDto);
    }
}

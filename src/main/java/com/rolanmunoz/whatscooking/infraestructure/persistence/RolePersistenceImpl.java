package com.rolanmunoz.whatscooking.infraestructure.persistence;

import com.rolanmunoz.whatscooking.domain.entity.Role;
import com.rolanmunoz.whatscooking.domain.persistence.RolePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RolePersistenceImpl implements RolePersistence {

    private final RoleRepository roleRepository;

    @Autowired
    public RolePersistenceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    public Optional<Role> getRoleById(Long idRole) {
        return this.roleRepository.findById(idRole);
    }

    @Override
    public Role saveRole(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long idRole) {
        this.roleRepository.deleteById(idRole);
    }
}

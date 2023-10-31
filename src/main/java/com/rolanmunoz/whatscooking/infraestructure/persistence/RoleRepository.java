package com.rolanmunoz.whatscooking.infraestructure.persistence;

import com.rolanmunoz.whatscooking.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

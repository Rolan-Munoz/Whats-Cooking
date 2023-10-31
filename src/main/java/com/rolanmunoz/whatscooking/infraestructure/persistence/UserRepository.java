package com.rolanmunoz.whatscooking.infraestructure.persistence;

import com.rolanmunoz.whatscooking.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

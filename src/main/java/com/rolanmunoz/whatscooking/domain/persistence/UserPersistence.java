package com.rolanmunoz.whatscooking.domain.persistence;
import com.rolanmunoz.whatscooking.domain.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserPersistence {
    List<User> getAllUsers();
    Optional<User> getUserById(Long idUser);
    Optional<User> getByName(String name);
    User saveUser(User user);
    void deleteUser(Long idUser);
    User updateUser(User user);
    boolean existsByName(String name);
    Optional<User> getByEmail(String email);

    boolean existsByEmail(String email);



}

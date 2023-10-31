package com.rolanmunoz.whatscooking.infraestructure.persistence;

import com.rolanmunoz.whatscooking.domain.entity.User;
import com.rolanmunoz.whatscooking.domain.persistence.UserPersistence;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserPersistenceImpl implements UserPersistence {

    private final UserRepository userRepository;

    @Autowired
    public UserPersistenceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long idUser) {
        return this.userRepository.findById(idUser);
    }

    @Override
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(Long idUser) {
        this.userRepository.deleteById(idUser);
    }

    @Override
    public User updateUser(User user) {
        if(this.userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        } else {
            throw new EntityNotFoundException("User not found with id " + user.getId());
        }
    }
}

package com.rolanmunoz.whatscooking.application.service.impl;
import com.rolanmunoz.whatscooking.application.dto.UserDTO;
import com.rolanmunoz.whatscooking.application.mapper.UserMapper;
import com.rolanmunoz.whatscooking.application.service.UserService;
import com.rolanmunoz.whatscooking.domain.entity.Role;
import com.rolanmunoz.whatscooking.domain.entity.User;
import com.rolanmunoz.whatscooking.domain.persistence.RolePersistence;
import com.rolanmunoz.whatscooking.domain.persistence.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserPersistence userPersistence;
    private final UserMapper userMapper;
    private final RolePersistence rolePersistence;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserPersistence userPersistence, UserMapper userMapper, RolePersistence rolePersistence, PasswordEncoder passwordEncoder) {
        this.userPersistence = userPersistence;
        this.userMapper = userMapper;
        this.rolePersistence = rolePersistence;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = this.userPersistence.getAllUsers();
        return userMapper.toDto(users);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserDTO> getUserById(Long idUser) {
        return this.userPersistence.getUserById(idUser).map(userMapper::toDto);
    }

    @Transactional
    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        if (userPersistence.existsByName(userDTO.getEmail())) {
            throw new RuntimeException( "El usuario ya existe en nuestra base de datos");
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userDTO.getPhoto() != null) {
            user.setPhoto(userDTO.getPhoto());
        }
        Role role = rolePersistence.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));
        User savedUser = userPersistence.saveUser(user);
        return userMapper.toDto(savedUser);
    }


    @Transactional
    @Override
    public void deleteUser(Long idUser) {
        User user = userPersistence.getUserById(idUser).orElseThrow(() -> new NoSuchElementException("User not found"));
        user.getRoles().clear();
        userPersistence.saveUser(user);
        this.userPersistence.deleteUser(idUser);
    }

    @Transactional
    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User existingUser = this.userPersistence.getUserById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (userDTO.getName() != null) {
            existingUser.setName(userDTO.getName());
        }
        if (userDTO.getEmail() != null) {
            existingUser.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPassword() != null) {
            existingUser.setPassword(userDTO.getPassword());
        }
        if (userDTO.getPhoto() != null) {
            existingUser.setPhoto(userDTO.getPhoto());
        }
        User updatedUser = this.userPersistence.saveUser(existingUser);
        return this.userMapper.toDto(updatedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserDTO> getByEmail(String email) {
        return this.userPersistence.getByEmail(email).map(userMapper::toDto);
    }
}

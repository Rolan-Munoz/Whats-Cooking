package com.rolanmunoz.whatscooking.application.service.impl;
import com.rolanmunoz.whatscooking.application.dto.UserDTO;
import com.rolanmunoz.whatscooking.application.mapper.UserMapper;
import com.rolanmunoz.whatscooking.application.service.UserService;
import com.rolanmunoz.whatscooking.domain.entity.User;
import com.rolanmunoz.whatscooking.domain.persistence.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserPersistence userPersistence;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserPersistence userPersistence, UserMapper userMapper) {
        this.userPersistence = userPersistence;
        this.userMapper = userMapper;
    }


    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = this.userPersistence.getAllUsers();
        return userMapper.toDto(users);
    }

    @Override
    public Optional<UserDTO> getUserById(Long idUser) {
        return this.userPersistence.getUserById(idUser).map(userMapper::toDto);
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = this.userPersistence.saveUser(this.userMapper.toEntity(userDTO));
        return userMapper.toDto(user);
    }

    @Override
    public void deleteUser(Long idUser) {
        this.userPersistence.deleteUser(idUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User existingUser = this.userPersistence.getUserById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setPhoto(userDTO.getPhoto());
        User updatedUser = this.userPersistence.saveUser(existingUser);
        return this.userMapper.toDto(updatedUser);
    }
}

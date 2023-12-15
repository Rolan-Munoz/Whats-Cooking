package com.rolanmunoz.whatscooking.application.service;
import com.rolanmunoz.whatscooking.application.dto.UserDTO;
import java.util.List;
import java.util.Optional;


public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(Long idUser);
    UserDTO saveUser(UserDTO userDTO);
    void deleteUser(Long idUser);
    UserDTO updateUser(UserDTO userDTO);

    Optional<UserDTO> getByEmail(String email);

}

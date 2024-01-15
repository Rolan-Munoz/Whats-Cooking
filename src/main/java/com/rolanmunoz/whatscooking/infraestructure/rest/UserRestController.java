package com.rolanmunoz.whatscooking.infraestructure.rest;

import com.rolanmunoz.whatscooking.application.dto.UserDTO;
import com.rolanmunoz.whatscooking.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserRestController {

    private final UserService userService;
    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/admin/users", produces = "application/json")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}", produces = "application/json")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id, Principal principal) {
        Optional<UserDTO> user = this.userService.getUserById(id);
        if (user.isPresent()) {
            UserDTO userDTO = user.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            // Comprueba si el usuario que hace la solicitud es el mismo que el usuario al que se está accediendo
            if (principal != null && principal.getName().equals(userDTO.getEmail()) || ((Collection<?>) authorities).contains(new SimpleGrantedAuthority("ADMIN"))) {
                return new ResponseEntity<>(userDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PutMapping(value = "/user/users/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<UserDTO> optionalExistingUser = this.userService.getUserById(id);
        if (optionalExistingUser.isPresent()) {
            userDTO.setId(id);
            UserDTO updatedUser = this.userService.updateUser(userDTO);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, Principal principal) {
        Optional<UserDTO> userDTO = this.userService.getUserById(id);
        if (userDTO.isPresent()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            // Comprueba si el usuario que hace la solicitud es el mismo que el usuario al que se está accediendo
            if (principal.getName().equals(userDTO.get().getEmail()) || authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
                this.userService.deleteUser(id); // llamada al método modificado
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}



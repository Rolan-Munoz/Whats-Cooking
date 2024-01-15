package com.rolanmunoz.whatscooking.infraestructure.rest;

import com.rolanmunoz.whatscooking.application.dto.DtoAuthResponse;
import com.rolanmunoz.whatscooking.application.dto.UserDTO;
import com.rolanmunoz.whatscooking.application.service.UserService;
import com.rolanmunoz.whatscooking.config.JWT.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthRestController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthRestController(AuthenticationManager authenticationManager,
                               UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            userService.saveUser(userDTO);
            return new ResponseEntity<>("Registro completado con éxito", HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDTO.getEmail(), userDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);
            return new ResponseEntity<>(new DtoAuthResponse(token), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        }
    }


}

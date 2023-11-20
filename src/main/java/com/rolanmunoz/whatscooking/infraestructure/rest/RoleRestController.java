package com.rolanmunoz.whatscooking.infraestructure.rest;

import com.rolanmunoz.whatscooking.application.dto.RoleDTO;
import com.rolanmunoz.whatscooking.application.service.RoleService;
import com.rolanmunoz.whatscooking.domain.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RoleRestController {

    private final RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin/roles", produces = "application/json")
    ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = this.roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/roles", produces = "application/json", consumes = "application/json")
    ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO role) {
        role = this.roleService.saveRole(role);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @GetMapping(value = "/admin/roles/{name}", produces = "application/json")
    public ResponseEntity<RoleDTO> getRoleByName(@PathVariable String name) {
        Optional<RoleDTO> role = this.roleService.findByName(name);
        if (role.isPresent()) {
            return new ResponseEntity<>(role.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/admin/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        this.roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}

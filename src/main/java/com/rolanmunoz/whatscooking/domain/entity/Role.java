package com.rolanmunoz.whatscooking.domain.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_role;


    @Column(length = 20, nullable = false)
    private String name;



    public Role() {
    }

    public Long getId() {
        return id_role;
    }

    public void setId(Long id) {
        this.id_role = id_role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

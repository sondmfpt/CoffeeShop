package com.swp.coffeeshop.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    int id;

    @Column(name = "role_name")
    String roleName;

}

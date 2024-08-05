package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class Contacts {

    @Id
    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;

    @ManyToOne
    @JoinColumn(name="username", referencedColumnName = "username")
    private Users user;

    @OneToMany
    private List<Address> addresses;

}

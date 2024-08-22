package com.practice.authorization.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class User extends BaseModel{
    private String email;
    private String saltedPassword;

    @OneToMany(mappedBy = "user")
    List<User_Role> user_roles;

    @OneToMany(mappedBy = "user")
    List<Session> session;
}

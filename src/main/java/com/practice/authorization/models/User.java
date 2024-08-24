package com.practice.authorization.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@JsonDeserialize(as= User.class)
public class User extends BaseModel{
    private String email;
    private String saltedPassword;


    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    List<Session> session;
}

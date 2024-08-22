package com.practice.authorization.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Role extends BaseModel{
    private String name;
    @OneToMany(mappedBy = "role")
    List<User_Role> userList;
}

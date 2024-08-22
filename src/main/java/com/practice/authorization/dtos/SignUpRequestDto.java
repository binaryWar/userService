package com.practice.authorization.dtos;

import com.practice.authorization.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String email;
    private String password;
    private String roles;

    public User toUser(){
        User newUser = new User();
        newUser.setEmail(this.email);
        newUser.setSaltedPassword(this.password);
        return newUser;
    }
}

package com.practice.authorization.controllers;

import com.practice.authorization.dtos.LoginRequestDto;
import com.practice.authorization.dtos.LoginResponseDto;
import com.practice.authorization.dtos.ResponseStatus;
import com.practice.authorization.dtos.SignUpRequestDto;
import com.practice.authorization.models.User;
import com.practice.authorization.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }
    @PostMapping("/sign_up")
    public ResponseStatus signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        try{
            authService.userSignUp(signUpRequestDto.toUser(), signUpRequestDto.getRoles());
            return ResponseStatus.SUCCESS;
        }catch(Exception e){
            return ResponseStatus.FAILURE;
        }
    }
    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){

        return null;
    }
}

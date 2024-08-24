package com.practice.authorization.controllers;

import com.practice.authorization.dtos.LoginRequestDto;
import com.practice.authorization.dtos.LoginResponseDto;
import com.practice.authorization.dtos.ResponseStatus;
import com.practice.authorization.dtos.SignUpRequestDto;
import com.practice.authorization.exceptions.UserNotFoundException;
import com.practice.authorization.models.User;
import com.practice.authorization.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
            User newUser = authService.userSignUp(signUpRequestDto.getEmail(),signUpRequestDto.getPassword());
            return ResponseStatus.SUCCESS;
        }catch(Exception e){
            return ResponseStatus.FAILURE;
        }
    }
    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        try{
            String token = authService.userLogin(loginRequestDto.getEmail(),loginRequestDto.getPassword());
            loginResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
            MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
            headers.set("AUTH_TOKEN",token);
            return new ResponseEntity<>(loginResponseDto,headers, HttpStatus.OK);
        }catch(UserNotFoundException ufe){
            loginResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            loginResponseDto.setErrorMsg(ufe.getMessage());
            return new ResponseEntity<>(loginResponseDto,null,HttpStatus.CONFLICT);
        }catch (Exception e){
            loginResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            return new ResponseEntity<>(loginResponseDto,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

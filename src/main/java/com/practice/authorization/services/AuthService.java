package com.practice.authorization.services;

import com.practice.authorization.exceptions.UserAlreadyExistException;
import com.practice.authorization.exceptions.UserNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.practice.authorization.models.User;
import com.practice.authorization.repositories.RoleRepository;
import com.practice.authorization.repositories.SessionRepository;
import com.practice.authorization.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, SessionRepository sessionRepository,BCryptPasswordEncoder bCryptPasswordEncoder ){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User userSignUp(String email,String password) throws UserAlreadyExistException {
        Optional<User>userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent())
            throw new UserAlreadyExistException("User Already exists with given email id");
        User newUser = new User();
        newUser.setEmail(email);

        newUser.setSaltedPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(newUser);
    }
    public String userLogin(String email,String password) throws UserNotFoundException{
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) throw new UserNotFoundException("No user exists with email is : " + email);
        User user = userOptional.get();
        String enc_Password = user.getSaltedPassword();
        boolean matches = bCryptPasswordEncoder.matches(password,enc_Password);
        if(matches == false) throw new UserNotFoundException("Password is Wrong for current Email id");

        return "token";
    }
}

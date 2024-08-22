package com.practice.authorization.services;

import com.practice.authorization.models.Role;
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

    public AuthService(UserRepository userRepository){

    }

    public User userSignUp(User user, String role){

        List<Role> roles = roleRepository.findRoleByName(role);
        Role newUserRole;
//
//        if(roleOptional.isEmpty()){
//            Role newRole = new Role();
//            newRole.setName(role);
//            newUserRole = roleRepository.save(newRole);
//        }else{
//            newUserRole = roleOptional.get();
//        }
//        user.getUser_roles().add(newUserRole);

        return userRepository.save(user);
    }
}

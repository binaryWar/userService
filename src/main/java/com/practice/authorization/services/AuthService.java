package com.practice.authorization.services;

import com.practice.authorization.exceptions.UserAlreadyExistException;
import com.practice.authorization.exceptions.UserNotFoundException;
import com.practice.authorization.models.Session;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.practice.authorization.models.User;
import com.practice.authorization.repositories.RoleRepository;
import com.practice.authorization.repositories.SessionRepository;
import com.practice.authorization.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
//    private SecretKey key = Keys.hmacShaKeyFor(
//            "namanisveryveryveryveryveryveryverycool"
//                    .getBytes(StandardCharsets.UTF_8));
    SecretKey key = Jwts.SIG.HS256.key().build();
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

        String token = createJwtToken(user.getId(),new ArrayList<>(),user.getEmail());

        Session session = new Session();
        session.setToken(token);
        session.setDeviceInfo("laptop");
        session.setExp_at(get30DaysHead());
        session.setIpAddress("127.0.0.1");
        session.setStatus("success");
        session.setUser(user);

        return token;
    }
    private String createJwtToken(Long userId,List<String> roles,String email){
        Map<String,Object> jwtData = new HashMap<>();
        jwtData.put("user_id",userId);
        jwtData.put("email",email);
        jwtData.put("roles",roles);


        Date datePlus30Days = get30DaysHead();

        String token = Jwts.builder().
                claims(jwtData)
                .expiration(datePlus30Days)
                .issuer("satyam singh")
                .issuedAt(new Date())
                .signWith(key)
                .compact();
        return token;
    }

    public boolean validateJwtToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            Date expiryTime = claims.getPayload().getExpiration();
            Long userId = (Long)claims.getPayload().get("user_id");
         return true;
        }catch (Exception e){
            return false;
        }
    }
    private Date get30DaysHead(){
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date datePlus30Days = calendar.getTime();
        return datePlus30Days;
    }

    public boolean logOut(Long userId,Long sessionId){
//        Optional<User> userOptional = userRepository.findById(id);
        Optional<Session> sessionOptional =  sessionRepository.findByUserIdAndId(userId, sessionId);
        if(sessionOptional.isEmpty()) return false;
        Session session = sessionOptional.get();
        session.setStatus("inActive");
        sessionRepository.save(session);
        return true;
    }
}

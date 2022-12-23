package com.example.group2.cuporrow.entities.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.group2.cuporrow.utils.ApplicationPasswordEncoder;
import com.example.group2.cuporrow.utils.TokenGenerator;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    private final ApplicationPasswordEncoder encoder = new ApplicationPasswordEncoder();

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    public User register(String name, String account, String password) {
        if (userRepo.findFirstByAccount(account).isEmpty()) {
            User newUser = new User(name, account, encoder.encode(password));
            saveUser(newUser);
            return newUser;
        }
        return null;
    }

    public User validByTokenAndGetUser(String account, String token) {
        Optional<User> optUserInDB = userRepo.findFirstByAccount(account);
        if (optUserInDB.isPresent()) {
            User userInDB = optUserInDB.get();
            if (userInDB.getToken().equals(token)) {
                return userInDB;
            }
        }
        return null;
    }

    public User validAndGetUser(String account, String password) {
        Optional<User> optUserInDB = userRepo.findFirstByAccount(account);
        if (optUserInDB.isPresent()) {
            User userInDB = optUserInDB.get();
            if (encoder.matches(password, userInDB.getPassword())) {
                return userInDB;
            }
        }
        return null;
    }

    public String loginAndGetToken(String account, String password) {
        User user = validAndGetUser(account, password);
        if (user != null) {
            String token = TokenGenerator.generate();
            user.setToken(token);
            saveUser(user);
            return token;
        }
        return null;
    }
}

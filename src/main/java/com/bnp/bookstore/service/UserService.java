package com.bnp.bookstore.service;

import com.bnp.bookstore.model.User;
import com.bnp.bookstore.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.mindrot.jbcrypt.BCrypt;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public void registerUser(String username,String password) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
    	User user = new User();
    	user.setUsername(username);
    	String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    	user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public boolean loginUser(String username,String password) {
        Optional<User> foundUser = userRepository.findByusername(username);
        
        if(foundUser.isPresent()) {
        	User matchUser = foundUser.get();
//        	return foundUser !=null && password.equals(matchUser.getPassword());
        	return BCrypt.checkpw(password, matchUser.getPassword());
        }
        return false;
    }
}

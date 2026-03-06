package com.example.User.service;

import com.example.User.JWT.JwtService;
import com.example.User.dto.LoginDto;
import com.example.User.entity.User;
import com.example.User.exception.UserNotFoundException;
import com.example.User.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtService jwtService;

    public User createUser(User user) {
        // Encrypt the password before saving the user
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserRole("ROLE_USER");
        return userRepository.save(user);
    }



    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id)));

    }

    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setEmail(userDetails.getEmail());

            // Encrypt the new password before updating it
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                user.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
            }
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


        //  jwt login
    public String verifyLogin(LoginDto user) {
        Optional<User> opUser = userRepository.findByUsername(user.getUsername());
        if(opUser.isPresent()){
            User user1 = opUser.get();
            if( bCryptPasswordEncoder.matches(user.getPassword(), user1.getPassword())){
               return jwtService.generateToken(user1);
            }

        }

        return null;
    }


}

package com.sibghat.vape_shop.services.user.behaviours;


import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.IDisableUserBehaviour;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DisableUser implements IDisableUserBehaviour {

    private final UserRepository userRepository;

    public DisableUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<HttpStatus> disableUser(String username) {
        if(userRepository.existsByUsername(username)){
            userRepository.disableUser(username, LocalDateTime.now());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

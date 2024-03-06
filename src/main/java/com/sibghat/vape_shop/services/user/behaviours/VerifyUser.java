package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.dtos.user.VerifyUserDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.IVerifyUserBehaviour;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class VerifyUser implements IVerifyUserBehaviour {
    private final UserRepository userRepository;

    public VerifyUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<HttpStatus> verifyUser(String verification_code) {
        Optional<VerifyUserDto> user = userRepository.getUserByVerificationCode(verification_code);
        if(user.isPresent()){
            if(user.get().getVerificationCodeValidTill() > Instant.now().getEpochSecond()){
                userRepository.enableUser(user.get().getUsername());
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                userRepository.deleteVerificationCode(user.get().getUsername());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

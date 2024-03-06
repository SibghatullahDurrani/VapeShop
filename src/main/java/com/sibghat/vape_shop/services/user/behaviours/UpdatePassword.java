package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.dtos.user.UpdatePasswordDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.IUpdatePasswordBehaviour;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdatePassword implements IUpdatePasswordBehaviour {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UpdatePassword(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<HttpStatus> updatePassword(
            String username,
            UpdatePasswordDto updatePasswordDto
    ) {
        Optional<String> previousEncodedPassword = userRepository.getPassword(username);
        if(previousEncodedPassword.isPresent()){
            if(passwordEncoder.matches(
                    updatePasswordDto.getPreviousPassword(),
                    previousEncodedPassword.get()
            )){
                userRepository.updatePassword(updatePasswordDto.getNewPassword(),username);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

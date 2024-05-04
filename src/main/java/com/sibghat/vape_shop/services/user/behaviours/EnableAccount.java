package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.IEnableAccountBehaviour;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.ISendVerificationEmailBehaviour;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnableAccount implements IEnableAccountBehaviour {

    private final UserRepository userRepository;
    private final ISendVerificationEmailBehaviour sendVerificationEmail;

    public EnableAccount(UserRepository userRepository, SendVerificationEmail sendVerificationEmail) {
        this.userRepository = userRepository;
        this.sendVerificationEmail = sendVerificationEmail;
    }

    @Override
    public ResponseEntity<HttpStatus> enableAccount(String username) throws MessagingException, UnsupportedEncodingException {
        if(!userRepository.existsByUsername(username)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            String emailVerificationURL = "http://127.0.0.1:4200/verifyemail/";
            String verificationCode = UUID.randomUUID().toString();
            Long verificationCodeValidTill = Instant.now().getEpochSecond() + 600;

            userRepository.addVerificationCode(verificationCode,verificationCodeValidTill,username);
            Optional<GetUserDto> user = userRepository.getUserByUsername(username);
            sendVerificationEmail.sendVerificationEmail(user.get(),emailVerificationURL);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}

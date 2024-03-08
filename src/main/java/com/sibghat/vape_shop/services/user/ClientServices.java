package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.services.user.behaviours.*;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.IEnableAccountBehaviour;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.IVerifyUserBehaviour;
import com.sibghat.vape_shop.services.user.interfaces.IClientServices;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class ClientServices extends UserServices implements IClientServices {

    private final IVerifyUserBehaviour verifyUserBehaviour;
    private final IEnableAccountBehaviour enableAccountBehaviour;

    public ClientServices(
            final AddClient addClient,
            final GetUser getUser,
            final UpdateUser updateUser,
            final UpdatePassword updatePassword,
            final DisableUser disableUser,
            IVerifyUserBehaviour verifyUserBehaviour,
            IEnableAccountBehaviour enableAccountBehaviour
    ) {
        super(
                addClient,
                getUser,
                updateUser,
                updatePassword,
                disableUser
        );
        this.verifyUserBehaviour = verifyUserBehaviour;
        this.enableAccountBehaviour = enableAccountBehaviour;
    }
    public ResponseEntity<HttpStatus> verifyUser(String verification_code){
        return verifyUserBehaviour.verifyUser(verification_code);
    }

    public ResponseEntity<HttpStatus> enableAccount(String username) throws MessagingException, UnsupportedEncodingException {
        return enableAccountBehaviour.enableAccount(username);
    }
}

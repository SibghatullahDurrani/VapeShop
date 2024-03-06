package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.services.user.behaviours.*;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.IVerifyUserBehaviour;
import com.sibghat.vape_shop.services.user.interfaces.IClientServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientServices extends UserServices implements IClientServices {

    private final IVerifyUserBehaviour verifyUserBehaviour;

    public ClientServices(
            final AddClient addClient,
            final GetUser getUser,
            final UpdateUser updateUser,
            final UpdatePassword updatePassword,
            final DisableUser disableUser,
            IVerifyUserBehaviour verifyUserBehaviour
    ) {
        super(
                addClient,
                getUser,
                updateUser,
                updatePassword,
                disableUser
        );
        this.verifyUserBehaviour = verifyUserBehaviour;


    }
    public ResponseEntity<HttpStatus> verifyUser(String verification_code){
        return verifyUserBehaviour.verifyUser(verification_code);
    }
}

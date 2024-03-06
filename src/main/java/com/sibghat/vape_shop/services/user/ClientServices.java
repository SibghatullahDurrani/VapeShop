package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.services.user.behaviours.*;
import com.sibghat.vape_shop.services.user.interfaces.IClientServices;
import org.springframework.stereotype.Service;

@Service
public class ClientServices extends UserServices implements IClientServices {

    public ClientServices(
            final AddClient addClient,
            final VerifyUser verifyUser,
            final GetUser getUser,
            final UpdateUser updateUser,
            final UpdatePassword updatePassword
            ) {
        super(
                addClient,
                verifyUser,
                getUser,
                updateUser,
                updatePassword
        );
    }
}

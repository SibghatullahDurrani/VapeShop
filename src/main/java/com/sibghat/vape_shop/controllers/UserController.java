package com.sibghat.vape_shop.controllers;

import com.sibghat.vape_shop.dtos.user.AddClientDto;
import com.sibghat.vape_shop.services.user.IUserServices;
import com.sibghat.vape_shop.services.user.UserServices;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final IUserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/users")
    public AddClientDto addClient(@Valid @RequestBody AddClientDto addClientDto){
        return userServices.addClient(addClientDto);

    }

}

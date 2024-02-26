package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.dtos.user.AddClientDto;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.mappers.user.AddClientMapper;
import com.sibghat.vape_shop.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements IUserServices {

    private final UserRepository userRepository;
    private final AddClientMapper addClientMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServices(UserRepository userRepository, AddClientMapper addClientMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.addClientMapper = addClientMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AddClientDto addClient(AddClientDto addClientDto) {
        User user = addClientMapper.mapTo(addClientDto);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return addClientMapper.mapFrom(userRepository.save(user));
    }
}

package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.IGetAllUsersBehaviour;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetAllUsers implements IGetAllUsersBehaviour {

    private final UserRepository userRepository;

    public GetAllUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Page<@Valid GetUserByAdminDto>> getAllUsers(int page, int size, String role, String username) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<GetUserByAdminDto> usersPage;
        if(username.isEmpty()){
            usersPage = userRepository.getAllUsers(role, pageRequest);
        }else{
            usersPage = userRepository.getUsersBySearch(username, role, pageRequest);
        }
        return new ResponseEntity<>(usersPage, HttpStatus.OK);
    }
}

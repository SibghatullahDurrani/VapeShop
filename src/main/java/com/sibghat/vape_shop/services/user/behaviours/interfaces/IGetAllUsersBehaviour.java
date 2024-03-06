package com.sibghat.vape_shop.services.user.behaviours.interfaces;

import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface IGetAllUsersBehaviour {

    ResponseEntity<Page<GetUserByAdminDto>> getAllUsers(
            int page,
            int size,
            String role,
            String username
    );
}

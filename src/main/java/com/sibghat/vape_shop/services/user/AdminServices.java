package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.services.user.behaviours.*;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.IGetAllUsersBehaviour;
import com.sibghat.vape_shop.services.user.interfaces.IAdminServices;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServices extends UserServices implements IAdminServices {

    private final IGetAllUsersBehaviour getAllUsersBehaviour;

    public AdminServices(
            final AddAdmin addAdmin,
            final GetUser getUser,
            final UpdateUser updateUser,
            final UpdatePassword updatePassword,
            final DisableUser disableUser,
            IGetAllUsersBehaviour getAllUsersBehaviour
    ) {
        super(
                addAdmin,
                getUser,
                updateUser,
                updatePassword,
                disableUser
        );

        this.getAllUsersBehaviour = getAllUsersBehaviour;
    }

    @Override
    public ResponseEntity<Page<GetUserByAdminDto>> getAllUsers(
            int page,
            int size,
            String role,
            String username
    ) {
        return getAllUsersBehaviour.getAllUsers(page,size,role,username);
    }

}

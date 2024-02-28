package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.dtos.user.AddUserDto;

public interface IAdminUserServices {

    AddUserDto addAdmin(AddUserDto adminToAdd,String createdBy);


}

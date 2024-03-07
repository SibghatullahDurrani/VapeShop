package com.sibghat.vape_shop.services.address;

import com.sibghat.vape_shop.services.address.behaviours.AddAddress;
import com.sibghat.vape_shop.services.address.interfaces.IAdminAddressServices;

public class AdminAddressServices extends AddressServices implements IAdminAddressServices {

    public AdminAddressServices(final AddAddress addAddress){
        super(addAddress);
    }
}

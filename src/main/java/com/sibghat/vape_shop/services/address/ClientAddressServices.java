package com.sibghat.vape_shop.services.address;

import com.sibghat.vape_shop.services.address.behaviours.AddAddress;
import com.sibghat.vape_shop.services.address.interfaces.IClientAddressServices;

public class ClientAddressServices extends AddressServices implements IClientAddressServices {


    public ClientAddressServices(final AddAddress addAddress){
        super(addAddress);
    }

}

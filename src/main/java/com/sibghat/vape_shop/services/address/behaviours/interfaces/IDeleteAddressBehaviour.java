package com.sibghat.vape_shop.services.address.behaviours.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IDeleteAddressBehaviour {

    ResponseEntity<HttpStatus> deleteAddress(String username,Long addressId);

}

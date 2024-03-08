package com.sibghat.vape_shop.controllers.address.interfaces;

import com.sibghat.vape_shop.dtos.address.AddAddressDto;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IAddressController {

    @PostMapping("/address/{username}")
    @PreAuthorize("""
    hasAnyRole("CLIENT", "ADMIN") and
    #username == authentication.name
""")
    ResponseEntity<GetAddressDto> addAddress(@PathVariable String username, @Valid @RequestBody AddAddressDto addressToAdd);

    @GetMapping("/address/{username}")
    @PreAuthorize("""
    hasAnyRole("CLIENT", "ADMIN") and
    #username == authentication.name
""")
    ResponseEntity<List<GetAddressDto>> getAddress(@PathVariable String username);


}

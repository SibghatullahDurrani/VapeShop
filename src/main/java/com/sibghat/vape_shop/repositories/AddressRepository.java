package com.sibghat.vape_shop.repositories;

import com.sibghat.vape_shop.domains.Address;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {

    int countAddressByUserId(Long id);

    @Query("""
    SELECT new com.sibghat.vape_shop.dtos.address.GetAddressDto(
    a.id, a.street, a.city, a.state, a.country, a.zip
    ) FROM Address a WHERE a.user.username = ?1
""")
    List<@Valid GetAddressDto> getAddresses(String username);
}

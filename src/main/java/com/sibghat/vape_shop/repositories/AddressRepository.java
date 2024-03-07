package com.sibghat.vape_shop.repositories;

import com.sibghat.vape_shop.domains.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {

    int countAddressByUserId(Long id);
}

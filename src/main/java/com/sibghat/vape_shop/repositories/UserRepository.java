package com.sibghat.vape_shop.repositories;

import com.sibghat.vape_shop.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByContactNumber(String contactNumber);

    Optional<User> findByUsername(String username);
}

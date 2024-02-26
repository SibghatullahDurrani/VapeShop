package com.sibghat.vape_shop.repositories;

import com.sibghat.vape_shop.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

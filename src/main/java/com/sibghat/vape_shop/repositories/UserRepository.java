package com.sibghat.vape_shop.repositories;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.GetAdminDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByContactNumber(String contactNumber);


    @Query("""
    SELECT new com.sibghat.vape_shop.dtos.user.GetUserDto(
    u.username, u.email, u.contactNumber)
    FROM User u WHERE u.username = ?1
""")
    Optional<User> findUserByUsername(String username);

    @Query("""
    SELECT new com.sibghat.vape_shop.dtos.user.GetAdminDto(
    u.username, u.email, u.contactNumber, u.enabled,
    u.createdAt, u.lastModifiedAt, u.createdBy,u.lastModifiedBy)
    FROM User u WHERE u.username = ?1
""")
    Optional<GetAdminDto> findAdminByUsername(String username);

    @Query(
            value = """
    SELECT new com.sibghat.vape_shop.dtos.user.GetAdminDto(
    u.username, u.email, u.contactNumber, u.enabled,
    u.createdAt, u.lastModifiedAt, u.createdBy, u.lastModifiedBy)
    FROM User u WHERE u.role = "ROLE_ADMIN"
""",
    countQuery = """
    SELECT count(u) FROM User u WHERE u.role = "ROLE_ADMIN"
""")
    Page<GetAdminDto> findAllAdmins(Pageable pageable);
}

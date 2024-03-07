package com.sibghat.vape_shop.repositories;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.VerifyUserDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByContactNumber(String contactNumber);

    @Query("""
    SELECT u.id FROM User u Where u.username = ?1
""")
    Optional<Long> getUserIdByUsername(String username);

    @Query("""
    SELECT new com.sibghat.vape_shop.dtos.user.GetUserDto(
    u.username, u.firstName, u.lastName,
    u.email, u.contactNumber, u.verificationCode)
    FROM User u WHERE u.username = ?1
""")
    Optional<GetUserDto> getUserByUsername(String username);

    @Query("""
    SELECT new com.sibghat.vape_shop.dtos.user.VerifyUserDto(
    u.username, u.verificationCodeValidTill)
    FROM User u WHERE u.verificationCode = ?1
""")
    Optional<VerifyUserDto> getUserByVerificationCode(String verification_code);

    @Query("""
    UPDATE User u set u.enabled = true, u.verificationCode = null,
    u.verificationCodeValidTill = null
    WHERE u.username = ?1
""")
    @Modifying
    @Transactional
    void enableUser(String username);

    Optional<User> findUserByUsername(String username);

    @Query("""
    SELECT new com.sibghat.vape_shop.dtos.user.GetUserByAdminDto(
    u.username, u.firstName, u.lastName, u.email, u.contactNumber,
    u.enabled, u.createdAt, u.lastModifiedAt, u.createdBy,u.lastModifiedBy)
    FROM User u WHERE u.username = ?1 AND u.role = "ROLE_ADMIN"
""")
    Optional<GetUserByAdminDto> getAdminByUsername(String username);

    @Query(
            value = """
    SELECT new com.sibghat.vape_shop.dtos.user.GetUserByAdminDto(
    u.username, u.firstName, u.lastName, u.email, u.contactNumber,
    u.enabled, u.createdAt, u.lastModifiedAt, u.createdBy,u.lastModifiedBy)
    FROM User u WHERE u.role = ?1
""",
    countQuery = """
    SELECT count(u) FROM User u WHERE u.role = ?1
""")
    Page<GetUserByAdminDto> getAllUsers(String role, Pageable pageable);
    @Query(value = """
    SELECT new com.sibghat.vape_shop.dtos.user.GetUserByAdminDto(
    u.username, u.firstName, u.lastName, u.email, u.contactNumber,
    u.enabled, u.createdAt, u.lastModifiedAt, u.createdBy,u.lastModifiedBy)
    FROM User u WHERE u.username LIKE %?1% AND u.role = ?2
""",
    countQuery = """
    SELECT count(u) FROM User u WHERE u.username LIKE %?1% AND u.role = ?2
""")
    Page<GetUserByAdminDto> getUsersBySearch(String username, String role,Pageable pageable);

    @Query("""
    UPDATE User u set u.password = ?1 WHERE u.username = ?2
""")
    @Modifying
    @Transactional
    void updatePassword(String password,String username);

    @Query("""
    SELECT u.password FROM User u WHERE u.username = ?1
""")
    Optional<String> getPassword(String username);


    @Query("""
    UPDATE User u set u.verificationCode = null, u.verificationCodeValidTill = null
    WHERE u.username = ?1
""")
    @Modifying
    @Transactional
    void deleteVerificationCode(String username);

    @Query("""
    UPDATE User u set
    u.enabled = false, u.lastModifiedBy = ?1, u.lastModifiedAt = ?2
    WHERE u.username = ?1
""")
    @Modifying
    @Transactional
    void disableUser(String username, LocalDateTime lastModifiedAt);
}

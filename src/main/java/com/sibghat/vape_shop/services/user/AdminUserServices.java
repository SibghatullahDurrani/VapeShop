package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetAdminDto;
import com.sibghat.vape_shop.mappers.user.AddUserMapper;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.conditionEvaluators.IUserRelatedConditionEvaluators;
import com.sibghat.vape_shop.services.conditionEvaluators.UserRelatedConditionEvaluators;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminUserServices implements IAdminUserServices{

    private final UserRepository userRepository;
    private final AddUserMapper addUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final IUserRelatedConditionEvaluators userRelatedConditionEvaluators;

    public AdminUserServices(
            UserRepository userRepository,
            AddUserMapper addUserMapper,
            PasswordEncoder passwordEncoder,
            UserRelatedConditionEvaluators userRelatedConditionEvaluators) {
        this.userRepository = userRepository;
        this.addUserMapper = addUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRelatedConditionEvaluators = userRelatedConditionEvaluators;
    }

    @Override
    public AddUserDto addAdmin(AddUserDto adminToAdd, String CreatedBy) {
        userRelatedConditionEvaluators.checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser(adminToAdd);
        User user = addUserMapper.mapTo(adminToAdd);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setCreatedBy(CreatedBy);
        user.setRole("ROLE_ADMIN");
        return addUserMapper.mapFrom(userRepository.save(user));
    }

    @Override
    public ResponseEntity<GetAdminDto> getAdmin(String username) {
        Optional<GetAdminDto> user = userRepository.findAdminByUsername(username);

        return user.map(getAdminDto -> new ResponseEntity<>(getAdminDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<Page<GetAdminDto>> getAllUsers(int page, int size, String role) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<GetAdminDto> adminsPage = userRepository.findAllAdmins(role,pageRequest);
        return new ResponseEntity<>(adminsPage, HttpStatus.OK);
    }
}

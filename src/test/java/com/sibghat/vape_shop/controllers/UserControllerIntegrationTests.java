package com.sibghat.vape_shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.services.user.IUserServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Objects;




@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final IUserServices userServices;
    private final ObjectMapper objectMapper;


    TestDataUtil testDataUtil = new TestDataUtil();

    @Autowired
    public UserControllerIntegrationTests(MockMvc mockMvc, IUserServices userServices) {
        this.mockMvc = mockMvc;
        this.userServices = userServices;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void testThatAddUserReturnsHTTP201CreatedWithCorrectRequestBody() throws Exception {
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isCreated());
    }

    @Test
    void testThatAddUserReturnsHTTP400BadRequestAndCorrectResponseBodyWithNoUsername() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setUsername(null);
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.username").value("must not be blank"));
    }

    @Test
    void testThatAddUserReturnsHTTP400BadRequestAndCorrectResponseBodyWithNoFirstName() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setFirstName(null);
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.firstName").value("must not be blank"));
    }

    @Test
    void testThatAddUserReturnsHTTP400BadRequestAndCorrectResponseBodyWithNoLastName() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setLastName(null);
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.lastName").value("must not be blank"));
    }

    @Test
    void testThatAddUserReturnsHTTP400BadRequestAndCorrectResponseBodyWithNoEmail() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setEmail(null);
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.email").value("must not be blank"));
    }

    @Test
    void testThatAddUserReturnsHTTP400BadRequestAndCorrectResponseBodyWithIncorrectEmail() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setEmail("abc");
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.email").value("must be a well-formed email address"));
    }

    @Test
    void testThatAddUserReturnsHTTP400BadRequestAndCorrectResponseBodyWithNoPassword() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setPassword(null);
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.password").value("must not be blank"));
    }

    @Test
    void testThatAddUserReturnsHTTP400BadRequestAndCorrectResponseBodyWithNoContactNumber() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setContactNumber(null);
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.contactNumber").value("must not be blank"));
    }

    @Test
    void testThatAddUserReturnsHTTP400BadRequestAndCorrectResponseBodyWithNoRequestBody() throws Exception{
        AddUserDto userToAdd = AddUserDto.builder().build();
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.username").value("must not be blank")
        ).andExpect(jsonPath("$.firstName").value("must not be blank")
        ).andExpect(jsonPath("$.lastName").value("must not be blank")
        ).andExpect(jsonPath("$.email").value("must not be blank")
        ).andExpect(jsonPath("$.password").value("must not be blank")
        ).andExpect(jsonPath("$.contactNumber").value("must not be blank"));
    }



    @Test
    void testThatVerifyAccountReturnsHTTP200OkWithCorrectFlow() throws Exception {
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        ResponseEntity<GetUserDto> user = userServices.addUser(userToAdd);
        mockMvc.perform(patch("/users/verify/"+ Objects.requireNonNull(user.getBody()).getVerificationCode())
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "USER")
    void testThatGetUserReturnsHTTP200OkWithCorrectUserCredentials() throws Exception {
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userServices.addUser(userToAdd);
        mockMvc.perform(get("/users/aqrar"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    void testThatGetUserReturnsHTTP403ForbiddenWithIncorrectRole() throws Exception {
        mockMvc.perform(get("/users/aqrar"))
                .andExpect(status().isForbidden());
    }

}

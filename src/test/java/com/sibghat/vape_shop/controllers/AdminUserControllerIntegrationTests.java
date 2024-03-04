package com.sibghat.vape_shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.services.user.IAdminUserServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AdminUserControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final IAdminUserServices adminUserServices;
    private final ObjectMapper objectMapper;
    private final TestDataUtil testDataUtil = new TestDataUtil();

    @Autowired
    public AdminUserControllerIntegrationTests(
            MockMvc mockMvc,
            IAdminUserServices adminUserServices) {
        this.mockMvc = mockMvc;
        this.adminUserServices = adminUserServices;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void addAdmin_Returns200Ok_WithCorrectData() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.username").value(userToAdd.getUsername())
        ).andExpect(jsonPath("$.firstName").value(userToAdd.getFirstName())
        ).andExpect(jsonPath("$.lastName").value(userToAdd.getLastName())
        ).andExpect(jsonPath("$.email").value(userToAdd.getEmail())
        ).andExpect(jsonPath("$.contactNumber").value(userToAdd.getContactNumber()));
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void addAdmin_Returns400BadRequest_WithAllRequiredFieldsBlank() throws Exception{
        AddUserDto userToAdd = AddUserDto.builder().build();
        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.username").value("must not be blank")
        ).andExpect(jsonPath("$.firstName").value("must not be blank")
        ).andExpect(jsonPath("$.lastName").value("must not be blank")
        ).andExpect(jsonPath("$.email").value("must not be blank")
        ).andExpect(jsonPath("$.contactNumber").value("must not be blank")
        ).andExpect(jsonPath("$.password").value("must not be blank"));
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void addAdmin_Returns400BadRequest_WithBlankUsername() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setUsername(null);

        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.username").value("must not be blank"));
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void addAdmin_Returns400BadRequest_WithBlankFirstName() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setFirstName(null);

        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.firstName").value("must not be blank"));
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void addAdmin_Returns400BadRequest_WithBlankLastName() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setLastName(null);

        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.lastName").value("must not be blank"));
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void addAdmin_Returns400BadRequest_WithBlankEmail() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setEmail(null);

        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.email").value("must not be blank"));
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void addAdmin_Returns400BadRequest_WithInvalidEmail() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setEmail("xyz");

        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.email").value("must be a well-formed email address"));
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void addAdmin_Returns400BadRequest_WithBlankPassword() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setPassword(null);

        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.password").value("must not be blank"));
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void addAdmin_Returns400BadRequest_WithBlankContactNumber() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setContactNumber(null);

        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.contactNumber").value("must not be blank"));
    }

    @Test
    public void addAdmin_Returns401Unauthorized_WithNoAuthentication() throws Exception {
        mockMvc.perform(post("/users/admins"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "USER")
    public void addAdmin_Returns400BadRequest_WithInvalidRole() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isForbidden());
    }





}

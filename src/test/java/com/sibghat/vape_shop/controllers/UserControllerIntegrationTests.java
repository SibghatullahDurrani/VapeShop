package com.sibghat.vape_shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
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

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void addUser_ReturnsHTTP201Created_WithCorrectRequestBody() throws Exception {
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isCreated());
    }

    @Test
    void addUser_ReturnsHTTP400BadRequestAndCorrectResponseBody_WithNoUsername() throws Exception{
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
    void addUser_ReturnsHTTP400BadRequestAndCorrectResponseBody_WithNoFirstName() throws Exception{
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
    void addUser_ReturnsHTTP400BadRequestAndCorrectResponseBody_WithNoLastName() throws Exception{
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
    void addUser_ReturnsHTTP400BadRequestAndCorrectResponseBody_WithNoEmail() throws Exception{
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
    void addUser_ReturnsHTTP400BadRequestAndCorrectResponseBody_WithIncorrectEmail() throws Exception{
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
    void addUser_ReturnsHTTP400BadRequestAndCorrectResponseBody_WithNoPassword() throws Exception{
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
    void addUser_ReturnsHTTP400BadRequestAndCorrectResponseBody_WithNoContactNumber() throws Exception{
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
    void addUser_ReturnsHTTP400BadRequestAndCorrectResponseBody_WithNoRequestBody() throws Exception{
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
    void addUser_ReturnsHTTP409Conflict_WithUsernameThatAlreadyExists() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userServices.addUser(userToAdd);
        userToAdd.setEmail("1234@gmail.com");
        userToAdd.setContactNumber("123412341234");
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isConflict()
        ).andExpect(jsonPath("$.username").value("already exists"));
    }

    @Test
    void addUser_ReturnsHTTP409Conflict_WithEmailThatAlreadyExists() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userServices.addUser(userToAdd);
        userToAdd.setUsername("abc");
        userToAdd.setContactNumber("123412341234");
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isConflict()
        ).andExpect(jsonPath("$.email").value("already exists"));
    }

    @Test
    void adUser_ReturnsHTTP409Conflict_WithContactNumberThatAlreadyExists() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userServices.addUser(userToAdd);
        userToAdd.setUsername("abc");
        userToAdd.setEmail("abc@gmail.com");
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isConflict()
        ).andExpect(jsonPath("$.contactNumber").value("already exists"));
    }

    @Test
    void addUserReturnsHTTP409Conflict_WithEveryUniqueFieldThatAlreadyExists() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userServices.addUser(userToAdd);
        String userJson = objectMapper.writeValueAsString(userToAdd);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isConflict()
        ).andExpect(jsonPath("$.username").value("already exists")
        ).andExpect(jsonPath("$.email").value("already exists")
        ).andExpect(jsonPath("$.contactNumber").value("already exists"));
    }

    @Test
    void verifyUser_ReturnsHTTP200Ok_WithCorrectVerificationCode() throws Exception {
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        ResponseEntity<GetUserDto> user = userServices.addUser(userToAdd);
        mockMvc.perform(patch("/users/verify/"+ Objects
                .requireNonNull(user.getBody()).getVerificationCode())
        ).andExpect(status().isOk());
    }

    @Test
    public void verifyUser_ReturnsHTTP404NotFound_WithIncorrectVerificationCode() throws Exception {
        mockMvc.perform(patch("/users/verify/"+ "Xyz")
        ).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "USER")
    void getUser_ReturnsHTTP200Ok_WithCorrectUserCredentials() throws Exception {
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userServices.addUser(userToAdd);
        mockMvc.perform(get("/users/aqrar"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    void getUser_ReturnsHTTP403Forbidden_WithIncorrectRole() throws Exception {
        mockMvc.perform(get("/users/aqrar"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "xyz", roles = "USER")
    public void getUser_Returns403Forbidden_WhenAccessingUserInformationThatIsNotTheirs() throws Exception {
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userServices.addUser(userToAdd);
        mockMvc.perform(get("/users/aqrar"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getUser_ReturnsHTTP401Unauthorized_WithNoAuthentication() throws Exception {
        mockMvc.perform(get("/users/aqrar"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "USER")
    public void updateUser_ReturnsHTTP200OK_WithCorrectData() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userServices.addUser(userToAdd);

        UpdateUserDto updatedUserData = testDataUtil.updateUserDto();
        String updatedUserDataJson = objectMapper.writeValueAsString(updatedUserData);

        mockMvc.perform(put("/users/aqrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserDataJson)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.username").value(userToAdd.getUsername())
        ).andExpect(jsonPath("$.firstName").value(updatedUserData.getFirstName())
        ).andExpect(jsonPath("$.lastName").value(updatedUserData.getLastName())
        ).andExpect(jsonPath("$.contactNumber").value(updatedUserData.getContactNumber())
        ).andExpect(jsonPath("$.email").value(userToAdd.getEmail()));
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "USER")
    public void updateUser_Returns400BadRequest_WithNoFirstName() throws Exception{
        UpdateUserDto updatedUserData = testDataUtil.updateUserDto();
        updatedUserData.setFirstName(null);
        String updatedUserDataJson = objectMapper.writeValueAsString(updatedUserData);

        mockMvc.perform(put("/users/aqrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserDataJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.firstName").value("must not be blank"));

    }

    @Test
    @WithMockUser(username = "aqrar", roles = "USER")
    public void updateUser_Returns400BadRequest_WithNoLastName() throws Exception{
        UpdateUserDto updatedUserData = testDataUtil.updateUserDto();
        updatedUserData.setLastName(null);
        String updatedUserDataJson = objectMapper.writeValueAsString(updatedUserData);

        mockMvc.perform(put("/users/aqrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserDataJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.lastName").value("must not be blank"));

    }

    @Test
    @WithMockUser(username = "aqrar", roles = "USER")
    public void updateUser_Returns400BadRequest_WithNoContactNumber() throws Exception{
        UpdateUserDto updatedUserData = testDataUtil.updateUserDto();
        updatedUserData.setContactNumber(null);
        String updatedUserDataJson = objectMapper.writeValueAsString(updatedUserData);

        mockMvc.perform(put("/users/aqrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserDataJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.contactNumber").value("must not be blank"));

    }

    @Test
    @WithMockUser(username = "aqrar", roles = "USER")
    public void updateUser_Returns400BadRequest_WithNoData() throws Exception{
        UpdateUserDto updateUserDto = UpdateUserDto.builder().build();
        String updatedUserDataJson = objectMapper.writeValueAsString(updateUserDto);

        mockMvc.perform(put("/users/aqrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserDataJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.contactNumber").value("must not be blank")
        ).andExpect(jsonPath("$.lastName").value("must not be blank")
        ).andExpect(jsonPath("$.firstName").value("must not be blank"));

    }

    @Test
    public void updateUser_ReturnsHTTP401Unauthorized_WithNoAuthentication() throws Exception {
        mockMvc.perform(put("/users/aqrar"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    void updateUser_ReturnsHTTP403Forbidden_WithIncorrectRole() throws Exception {
        UpdateUserDto updatedUserData = testDataUtil.updateUserDto();
        String updatedUserDataJson = objectMapper.writeValueAsString(updatedUserData);

        mockMvc.perform(put("/users/aqrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserDataJson))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "xyz", roles = "USER")
    public void Update_Returns403Forbidden_WhenUpdatingUserInformationThatIsNotTheirs() throws Exception {
        UpdateUserDto updatedUserData = testDataUtil.updateUserDto();
        String updatedUserDataJson = objectMapper.writeValueAsString(updatedUserData);

        mockMvc.perform(put("/users/aqrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserDataJson))
                .andExpect(status().isForbidden());
    }

}

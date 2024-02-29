package com.sibghat.vape_shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.services.user.IUserServices;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final IUserServices userServices;
    private final ObjectMapper objectMapper;


    TestDataUtil testDataUtil = new TestDataUtil();
    private final String userWithNoUsername = "{\"firstName\":\"aqrar\",\"lastName\":\"Bazai\",\"email\":\"aqrar123@gmail.com\",\"password\":\"aqrar\",\"contactNumber\":\"123094123\",\"enabled\":true}";
    private final String userWithNoFirstName = "{\"username\":\"aqrar\",\"lastName\":\"Bazai\",\"email\":\"aqrar123@gmail.com\",\"password\":\"aqrar\",\"contactNumber\":\"123094123\",\"enabled\":true}";
    private final String userWithNoLastName = "{\"username\":\"aqrar\",\"firstName\":\"aqrar\",\"email\":\"aqrar123@gmail.com\",\"password\":\"aqrar\",\"contactNumber\":\"123094123\",\"enabled\":true}";
    private final String userWithNoEmail = "{\"username\":\"aqrar\",\"firstName\":\"aqrar\",\"lastName\":\"Bazai\",\"password\":\"aqrar\",\"contactNumber\":\"123094123\",\"enabled\":true}";
    private final String userWithNoContactNumber = "{\"username\":\"aqrar\",\"firstName\":\"aqrar\",\"lastName\":\"Bazai\",\"email\":\"aqrar123@gmail.com\",\"password\":\"aqrar\",\"enabled\":true}";



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
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

//    @Test
//    void testThatAddUserReturnsHTTP400BadRequestWithNoUsername(){
//        String userJson = "{\"username\":\"aqrar\",\"firstName\":\"aqrar\",\"lastName\":\"Bazai\",\"email\":\"aqrar123@gmail.com\",\"password\":\"aqrar\",\"contactNumber\":\"123094123\",\"enabled\":true}";
//    }

    @Test
    @WithMockUser(username = "aqrar",roles = "USER")
    void testThatGetUserReturnsHTTP200OkWithCorrectUserCredentials() throws Exception {
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userServices.addUser(userToAdd);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/aqrar"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    void testThatGetUserReturnsHTTP403ForbiddenWithIncorrectRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/aqrar"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}

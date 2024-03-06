package com.sibghat.vape_shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.services.user.interfaces.IAdminServices;
import com.sibghat.vape_shop.services.user.interfaces.IClientServices;
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
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AdminUserControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final IAdminServices adminUserServices;
    private final IClientServices userServices;
    private final ObjectMapper objectMapper;
    private final TestDataUtil testDataUtil = new TestDataUtil();

    @Autowired
    public AdminUserControllerIntegrationTests(
            MockMvc mockMvc,
            IAdminServices adminUserServices,
            IClientServices userServices) {
        this.mockMvc = mockMvc;
        this.adminUserServices = adminUserServices;
        this.userServices = userServices;
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
        ).andExpect(status().isCreated()
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
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void addAdmin_Returns409Conflict_WithAllDetailsThatAlreadyExists() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        adminUserServices.addUser(userToAdd, userToAdd.getUsername());

        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isConflict()
        ).andExpect(jsonPath("$.username").value("already exists")
        ).andExpect(jsonPath("$.email").value("already exists")
        ).andExpect(jsonPath("$.contactNumber").value("already exists"));
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void addAdmin_Returns409Conflict_WithUsernameThatAlreadyExists() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();

        adminUserServices.addUser(userToAdd, userToAdd.getUsername());

        userToAdd.setContactNumber("123541348976123");
        userToAdd.setEmail("sdhfbvkjahsdv@gmail.com");

        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isConflict()
        ).andExpect(jsonPath("$.username").value("already exists"));
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void addAdmin_Returns409Conflict_WithEmailThatAlreadyExists() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();

        adminUserServices.addUser(userToAdd, userToAdd.getUsername());

        userToAdd.setContactNumber("123541348976123");
        userToAdd.setUsername("sdhfbvkjahsdv");

        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isConflict()
        ).andExpect(jsonPath("$.email").value("already exists"));
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void addAdmin_Returns409Conflict_WithContactNumberThatAlreadyExists() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();

        adminUserServices.addUser(userToAdd, userToAdd.getUsername());

        userToAdd.setEmail("asdfadsfgas@gmail.com");
        userToAdd.setUsername("sdhfbvkjahsdv");

        String userToAddJson = objectMapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToAddJson)
        ).andExpect(status().isConflict()
        ).andExpect(jsonPath("$.contactNumber").value("already exists"));
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

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAdmin_ReturnsHTTP200OK_WithValidUsername() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        adminUserServices.addUser(userToAdd, userToAdd.getUsername());

        mockMvc.perform(get("/users/admins/"  + userToAdd.getUsername())
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAdmin_Returns403Forbidden_WithUnauthorizedUsername() throws Exception{
        mockMvc.perform(get("/users/admins/xyz")
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "USER")
    public void getAdmin_Returns403Forbidden_WithUserRole() throws Exception{
        mockMvc.perform(get("/users/admins/xyz")
        ).andExpect(status().isForbidden());
    }

    @Test
    public void getAdmin_Returns401Unauthorized_WithNoAuthentication() throws Exception{
        mockMvc.perform(get("/users/admins/xyz")
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllAdmins_Return200OKAndCorrectData_WithValidParamsAndNoUsernameSearch() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        AddUserDto userToAdd2 = testDataUtil.addUserDto2();
        AddUserDto userToAdd3 = testDataUtil.addUserDto3();
        adminUserServices.addUser(userToAdd, userToAdd.getUsername());
        adminUserServices.addUser(userToAdd2, userToAdd2.getUsername());
        adminUserServices.addUser(userToAdd3, userToAdd3.getUsername());

        MvcResult result = mockMvc.perform(get("/users/admins")
                .param("page","0")
                .param("size","5")
                .param("username","")
        ).andExpect(status().isOk()
        ).andReturn();

        List<String> contentJson = JsonPath.read(result.getResponse().getContentAsString(),"$.content");
        assertThat(contentJson).hasSize(3);
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllAdmins_Return200OKAndCorrectData_WithValidParamsAndUsernameSearch() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        AddUserDto userToAdd2 = testDataUtil.addUserDto2();
        AddUserDto userToAdd3 = testDataUtil.addUserDto3();
        adminUserServices.addUser(userToAdd, userToAdd.getUsername());
        adminUserServices.addUser(userToAdd2, userToAdd2.getUsername());
        adminUserServices.addUser(userToAdd3, userToAdd3.getUsername());

        MvcResult result = mockMvc.perform(get("/users/admins")
                .param("page","0")
                .param("size","5")
                .param("username","s")
        ).andExpect(status().isOk()
        ).andReturn();

        List<String> contentJson = JsonPath.read(result.getResponse().getContentAsString(),"$.content");
        assertThat(contentJson).hasSize(2);
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllAdmins_Return400BadRequest_WithNoPageParam() throws Exception{
        mockMvc.perform(get("/users/admins")
                .param("size","5")
                .param("username","s")
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.page").value("parameter required"));

    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllAdmins_Return400BadRequest_WithNoSizeParam() throws Exception{
        mockMvc.perform(get("/users/admins")
                .param("page","5")
                .param("username","s")
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.size").value("parameter required"));

    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllAdmins_Return400BadRequest_WithNoUsernameParam() throws Exception{
        mockMvc.perform(get("/users/admins")
                .param("page","5")
                .param("size","5")
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.username").value("parameter required"));

    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllAdmins_Return400BadRequest_WithPageLessThanZero() throws Exception{

        mockMvc.perform(get("/users/admins")
                .param("page","-1")
                .param("size","5")
                .param("username","s")
        ).andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllAdmins_Return400BadRequest_WithSizeLessThan1() throws Exception{

        mockMvc.perform(get("/users/admins")
                .param("page","0")
                .param("size","0")
                .param("username","s")
        ).andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllAdmins_Return400BadRequest_WithSizeNonInteger() throws Exception{

        mockMvc.perform(get("/users/admins")
                .param("page","0")
                .param("size","s")
                .param("username","s")
        ).andExpect(status().isBadRequest());

    }
    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllAdmins_Return400BadRequest_WithPageNonInteger() throws Exception{

        mockMvc.perform(get("/users/admins")
                .param("page","s")
                .param("size","1")
                .param("username","s")
        ).andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "aqrar", roles = "USER")
    public void getAlLAdmins_Return403Forbidden_WithUserRole() throws Exception{
        mockMvc.perform(get("/users/admins")
                .param("page","0")
                .param("size","1")
                .param("username","s")
        ).andExpect(status().isForbidden());
    }

    @Test
    public void getAlLAdmins_Return401Unauthorized_WithNoAuthentication() throws Exception{
        mockMvc.perform(get("/users/admins")
                .param("page","0")
                .param("size","1")
                .param("username","s")
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllUsers_Return200OKAndCorrectData_WithValidParamsAndNoUsernameSearch() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        AddUserDto userToAdd2 = testDataUtil.addUserDto2();
        AddUserDto userToAdd3 = testDataUtil.addUserDto3();
        userServices.addUser(userToAdd,userToAdd.getUsername());
        userServices.addUser(userToAdd2,userToAdd2.getUsername());
        userServices.addUser(userToAdd3,userToAdd3.getUsername());

        MvcResult result = mockMvc.perform(get("/users")
                .param("page","0")
                .param("size","5")
                .param("username","")
        ).andExpect(status().isOk()
        ).andReturn();

        List<String> contentJson = JsonPath.read(result.getResponse().getContentAsString(),"$.content");
        assertThat(contentJson).hasSize(3);
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllUsers_Return200OKAndCorrectData_WithValidParamsAndUsernameSearch() throws Exception{
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        AddUserDto userToAdd2 = testDataUtil.addUserDto2();
        AddUserDto userToAdd3 = testDataUtil.addUserDto3();
        userServices.addUser(userToAdd,userToAdd.getUsername());
        userServices.addUser(userToAdd2,userToAdd2.getUsername());
        userServices.addUser(userToAdd3,userToAdd3.getUsername());

        MvcResult result = mockMvc.perform(get("/users")
                .param("page","0")
                .param("size","5")
                .param("username","s")
        ).andExpect(status().isOk()
        ).andReturn();

        List<String> contentJson = JsonPath.read(result.getResponse().getContentAsString(),"$.content");
        assertThat(contentJson).hasSize(2);
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllUsers_Return400BadRequest_WithNoPageParam() throws Exception{
        mockMvc.perform(get("/users")
                .param("size","5")
                .param("username","s")
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.page").value("parameter required"));

    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllUsers_Return400BadRequest_WithNoSizeParam() throws Exception{
        mockMvc.perform(get("/users")
                .param("page","5")
                .param("username","s")
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.size").value("parameter required"));

    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllUsers_Return400BadRequest_WithNoUsernameParam() throws Exception{
        mockMvc.perform(get("/users")
                .param("page","5")
                .param("size","5")
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.username").value("parameter required"));

    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllUsers_Return400BadRequest_WithPageLessThanZero() throws Exception{

        mockMvc.perform(get("/users")
                .param("page","-1")
                .param("size","5")
                .param("username","s")
        ).andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllUsers_Return400BadRequest_WithSizeLessThan1() throws Exception{

        mockMvc.perform(get("/users")
                .param("page","0")
                .param("size","0")
                .param("username","s")
        ).andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllUsers_Return400BadRequest_WithSizeNonInteger() throws Exception{

        mockMvc.perform(get("/users")
                .param("page","0")
                .param("size","s")
                .param("username","s")
        ).andExpect(status().isBadRequest());

    }
    @Test
    @WithMockUser(username = "aqrar",roles = "ADMIN")
    public void getAllUsers_Return400BadRequest_WithPageNonInteger() throws Exception{

        mockMvc.perform(get("/users")
                .param("page","s")
                .param("size","1")
                .param("username","s")
        ).andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "aqrar", roles = "USER")
    public void getAllUsers_Return403Forbidden_WithUserRole() throws Exception{
        mockMvc.perform(get("/users")
                .param("page","0")
                .param("size","1")
                .param("username","s")
        ).andExpect(status().isForbidden());
    }

    @Test
    public void getAllUsers_Return401Unauthorized_WithNoAuthentication() throws Exception{
        mockMvc.perform(get("/users")
                .param("page","0")
                .param("size","1")
                .param("username","s")
        ).andExpect(status().isUnauthorized());
    }
}

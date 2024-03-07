package com.sibghat.vape_shop.controllers.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.UpdatePasswordDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import lombok.With;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AdminControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final TestDataUtil testDataUtil = new TestDataUtil();
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminControllerIntegrationTests(
            MockMvc mockMvc,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        User user = testDataUtil.validUser1();
        userRepository.save(user);

        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setEmail(user.getEmail());
        userToAdd.setUsername(user.getUsername());
        userToAdd.setContactNumber(user.getContactNumber());


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
        User user = testDataUtil.validUser1();
        userRepository.save(user);

        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setEmail("afajdskvf@gmail.com");
        userToAdd.setUsername(user.getUsername());
        userToAdd.setContactNumber("1290364192386401932");

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
        User user = testDataUtil.validUser1();
        userRepository.save(user);

        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setEmail(user.getEmail());
        userToAdd.setUsername("asdgfkjhadgsf");
        userToAdd.setContactNumber("1290364192386401932");

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
        User user = testDataUtil.validUser1();
        userRepository.save(user);

        AddUserDto userToAdd = testDataUtil.addUserDto1();
        userToAdd.setContactNumber(user.getContactNumber());
        userToAdd.setEmail("afajdskvf@gmail.com");
        userToAdd.setUsername("asdfasdiufyiads");

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
        User user = testDataUtil.validUser1();
        userRepository.save(user);

        mockMvc.perform(get("/users/admins/"  + user.getUsername())
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
        User userToAdd = testDataUtil.validUser1();
        userToAdd.setRole("ROLE_ADMIN");
        User userToAdd2 = testDataUtil.validUser2();
        userToAdd2.setRole("ROLE_ADMIN");
        User userToAdd3 = testDataUtil.validUser3();
        userToAdd3.setRole("ROLE_ADMIN");
        userRepository.save(userToAdd);
        userRepository.save(userToAdd2);
        userRepository.save(userToAdd3);

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
        User userToAdd = testDataUtil.validUser1();
        userToAdd.setRole("ROLE_ADMIN");
        User userToAdd2 = testDataUtil.validUser2();
        userToAdd2.setRole("ROLE_ADMIN");
        User userToAdd3 = testDataUtil.validUser3();
        userToAdd3.setRole("ROLE_ADMIN");
        userRepository.save(userToAdd);
        userRepository.save(userToAdd2);
        userRepository.save(userToAdd3);

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
        User userToAdd = testDataUtil.validUser1();
        User userToAdd2 = testDataUtil.validUser2();
        User userToAdd3 = testDataUtil.validUser3();
        userRepository.save(userToAdd);
        userRepository.save(userToAdd2);
        userRepository.save(userToAdd3);

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
        User userToAdd = testDataUtil.validUser1();
        User userToAdd2 = testDataUtil.validUser2();
        User userToAdd3 = testDataUtil.validUser3();
        userRepository.save(userToAdd);
        userRepository.save(userToAdd2);
        userRepository.save(userToAdd3);

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

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    public void updateAdmin_ReturnsHTTP200OK_WithValidData() throws Exception{
        User userToAdd = testDataUtil.validUser1();
        userRepository.save(userToAdd);

        UpdateUserDto updatedUserData = testDataUtil.updateUserDto();
        String updatedUserDataJson = objectMapper.writeValueAsString(updatedUserData);

        mockMvc.perform(put("/users/admins/aqrar")
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
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    public void updateAdmin_Returns400BadRequest_WithNoFirstName() throws Exception{
        UpdateUserDto updatedUserData = testDataUtil.updateUserDto();
        updatedUserData.setFirstName(null);
        String updatedUserDataJson = objectMapper.writeValueAsString(updatedUserData);

        mockMvc.perform(put("/users/admins/aqrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserDataJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.firstName").value("must not be blank"));

    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    public void updateAdmin_Returns400BadRequest_WithNoLastName() throws Exception{
        UpdateUserDto updatedUserData = testDataUtil.updateUserDto();
        updatedUserData.setLastName(null);
        String updatedUserDataJson = objectMapper.writeValueAsString(updatedUserData);

        mockMvc.perform(put("/users/admins/aqrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserDataJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.lastName").value("must not be blank"));

    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    public void updateAdmin_Returns400BadRequest_WithNoContactNumber() throws Exception{
        UpdateUserDto updatedUserData = testDataUtil.updateUserDto();
        updatedUserData.setContactNumber(null);
        String updatedUserDataJson = objectMapper.writeValueAsString(updatedUserData);

        mockMvc.perform(put("/users/admins/aqrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserDataJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.contactNumber").value("must not be blank"));

    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    public void updateAdmin_Returns400BadRequest_WithNoData() throws Exception{
        UpdateUserDto updateUserDto = UpdateUserDto.builder().build();
        String updatedUserDataJson = objectMapper.writeValueAsString(updateUserDto);

        mockMvc.perform(put("/users/admins/aqrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserDataJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.contactNumber").value("must not be blank")
        ).andExpect(jsonPath("$.lastName").value("must not be blank")
        ).andExpect(jsonPath("$.firstName").value("must not be blank"));

    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    public void updateAdmin_Returns409Conflict_WithContactNumberThatAlreadyExists() throws Exception{
        User addUserDto = testDataUtil.validUser1();
        userRepository.save(addUserDto);

        UpdateUserDto updatedUserData = testDataUtil.updateUserDto();
        updatedUserData.setContactNumber(addUserDto.getContactNumber());
        String updatedUserDataJson = objectMapper.writeValueAsString(updatedUserData);

        mockMvc.perform(put("/users/admins/aqrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserDataJson)
        ).andExpect(status().isConflict()
        ).andExpect(jsonPath("$.contactNumber").value("already exists"));

    }

    @Test
    public void updateAdmin_ReturnsHTTP401Unauthorized_WithNoAuthentication() throws Exception {
        mockMvc.perform(put("/users/admins/aqrar"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "aqrar",roles = "CLIENT")
    void updateAdmin_ReturnsHTTP403Forbidden_WithIncorrectRole() throws Exception {
        UpdateUserDto updatedUserData = testDataUtil.updateUserDto();
        String updatedUserDataJson = objectMapper.writeValueAsString(updatedUserData);

        mockMvc.perform(put("/users/admins/aqrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserDataJson))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "xyz", roles = "ADMIN")
    public void updateAdmin_Returns403Forbidden_WhenUpdatingUserInformationThatIsNotTheirs() throws Exception {
        UpdateUserDto updatedUserData = testDataUtil.updateUserDto();
        String updatedUserDataJson = objectMapper.writeValueAsString(updatedUserData);

        mockMvc.perform(put("/users/admins/aqrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserDataJson))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    public void updateAdminPassword_ReturnsHTTP200OK_WithValidUsernameAndPassword() throws Exception {
        User userToAdd = testDataUtil.validUser1();

        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .previousPassword(userToAdd.getPassword())
                .newPassword("updated")
                .build();

        userToAdd.setPassword(passwordEncoder.encode(userToAdd.getPassword()));
        userRepository.save(userToAdd);

        String updatePasswordDtoJson = objectMapper.writeValueAsString(updatePasswordDto);

        mockMvc.perform(patch("/users/admins/" + userToAdd.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatePasswordDtoJson)
        ).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    public void updateAdminPassword_ReturnsHTTP400BadRequest_WithInvalidPreviousPassword() throws Exception {
        User userToAdd = testDataUtil.validUser1();
        userRepository.save(userToAdd);

        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .previousPassword("adsfasdfadsf")
                .newPassword("updated")
                .build();

        String updatePasswordDtoJson = objectMapper.writeValueAsString(updatePasswordDto);

        mockMvc.perform(patch("/users/admins/" + userToAdd.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatePasswordDtoJson)
        ).andExpect(status().isBadRequest());

    }

    @Test
    public void updateAdminPassword_ReturnsHTTP401Unauthorized_WithNoAuthentication() throws Exception{
        User userToAdd = testDataUtil.validUser1();
        userRepository.save(userToAdd);

        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .previousPassword("xyz")
                .newPassword("updated")
                .build();

        String updatePasswordDtoJson = objectMapper.writeValueAsString(updatePasswordDto);

        mockMvc.perform(patch("/users/admins/" + userToAdd.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatePasswordDtoJson)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "sibghat", roles = "ADMIN")
    public void updateAdminPassword_ReturnsHTTP403Forbidden_WhenAccessingOtherUsersEndpoint() throws Exception{
        User userToAdd = testDataUtil.validUser1();
        userRepository.save(userToAdd);

        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .previousPassword("xyz")
                .newPassword("updated")
                .build();

        String updatePasswordDtoJson = objectMapper.writeValueAsString(updatePasswordDto);

        mockMvc.perform(patch("/users/admins/" + userToAdd.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatePasswordDtoJson)
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    public void updateAdminPassword_ReturnsHTTP403Forbidden_WithClientRole() throws Exception{
        User userToAdd = testDataUtil.validUser1();
        userRepository.save(userToAdd);

        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .previousPassword("xyz")
                .newPassword("updated")
                .build();

        String updatePasswordDtoJson = objectMapper.writeValueAsString(updatePasswordDto);

        mockMvc.perform(patch("/users/admins/" + userToAdd.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatePasswordDtoJson)
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    public void updateAdminPassword_ReturnsHTTP400BadRequest_WithInvalidRequiredFields() throws Exception{
        User userToAdd = testDataUtil.validUser1();
        userRepository.save(userToAdd);

        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder().build();

        String updatePasswordDtoJson = objectMapper.writeValueAsString(updatePasswordDto);

        mockMvc.perform(patch("/users/admins/" + userToAdd.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatePasswordDtoJson)
        ).andExpect(status().isBadRequest()
        ).andExpect(jsonPath("$.previousPassword").value("must not be blank")
        ).andExpect(jsonPath("$.newPassword").value("must not be blank"));
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    public void disableAdmin_Returns204NoContent_WithValidUsername() throws Exception{
        User userToAdd = testDataUtil.validUser1();
        userToAdd.setRole("ROLE_ADMIN");
        userRepository.save(userToAdd);

        mockMvc.perform(delete("/users/admins/" + userToAdd.getUsername())
        ).andExpect(status().isNoContent());

    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    public void disableAdmin_Returns404NotFound_WithInvalidUsername() throws Exception{
        mockMvc.perform(delete("/users/admins/aqrar")
        ).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    public void disableAdmin_Returns403Forbidden_WithUsernameThatIsNotTheirs() throws Exception{
        mockMvc.perform(delete("/users/admins/xyz")
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    public void disableAdmin_Returns403Forbidden_WithIncorrectRole() throws Exception{
        mockMvc.perform(delete("/users/admins/aqrar")
        ).andExpect(status().isForbidden());
    }
}

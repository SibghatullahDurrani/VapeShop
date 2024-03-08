package com.sibghat.vape_shop.controllers.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.sibghat.vape_shop.AddressTestDataUtil;
import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.address.AddAddressDto;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.dtos.address.UpdateAddressDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.address.AddressServices;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class AddressControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final TestDataUtil testDataUtil = new TestDataUtil();
    private final AddressTestDataUtil addressTestDataUtil = new AddressTestDataUtil();
    private final UserRepository userRepository;
    private final AddressServices addressServices;

    @Autowired
    AddressControllerIntegrationTests(
            MockMvc mockMvc,
            UserRepository userRepository,
            AddressServices addressServices
    ) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.addressServices = addressServices;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    void addAddress_Returns200Created_WithValidDataAndClientRole() throws Exception{
        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);
        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        String addAddressJson = objectMapper.writeValueAsString(addAddressDto);

        mockMvc.perform(post("/address/" + user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(addAddressJson)
        ).andExpect(status().isCreated()
        ).andExpect(jsonPath("$.id").isNumber()
        ).andExpect(jsonPath("$.street").value(addAddressDto.getStreet())
        ).andExpect(jsonPath("$.city").value(addAddressDto.getCity())
        ).andExpect(jsonPath("$.state").value(addAddressDto.getState())
        ).andExpect(jsonPath("$.country").value(addAddressDto.getCountry())
        ).andExpect(jsonPath("$.zip").value(addAddressDto.getZip())
        );
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    void addAddress_Returns200Created_WithValidDataAndAdminRole() throws Exception{
        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);
        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        String addAddressJson = objectMapper.writeValueAsString(addAddressDto);

        mockMvc.perform(post("/address/" + user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(addAddressJson)
        ).andExpect(status().isCreated()
        ).andExpect(jsonPath("$.id").isNumber()
        ).andExpect(jsonPath("$.street").value(addAddressDto.getStreet())
        ).andExpect(jsonPath("$.city").value(addAddressDto.getCity())
        ).andExpect(jsonPath("$.state").value(addAddressDto.getState())
        ).andExpect(jsonPath("$.country").value(addAddressDto.getCountry())
        ).andExpect(jsonPath("$.zip").value(addAddressDto.getZip())
        );
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    void addAddress_Returns400BadRequest_WithUserThatAlreadyHasTwoAddresses() throws Exception{
        User user = testDataUtil.validUser1();
        user.setRole("ROLE_ADMIN");
        userRepository.saveAndFlush(user);

        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        AddAddressDto addAddressDto2 = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        addAddressDto2.setUserId(null);
        addressServices.addAddress(user.getUsername(),addAddressDto);
        addressServices.addAddress(user.getUsername(),addAddressDto2);

        String addAddressJson = objectMapper.writeValueAsString(addAddressDto);

        mockMvc.perform(post("/address/" + user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(addAddressJson)
        ).andExpect(status().isBadRequest()
        );
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    void addAddress_Returns400BadRequest_WithBadRequestBody() throws Exception{
        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);

        AddAddressDto addAddressDto = AddAddressDto.builder().build();


        String addAddressJson = objectMapper.writeValueAsString(addAddressDto);

        mockMvc.perform(post("/address/" + user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(addAddressJson)
        ).andExpect(status().isBadRequest()
        );
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    void addAddress_Returns403Forbidden_WithIncorrectUsername() throws Exception{

        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        String addAddressJson = objectMapper.writeValueAsString(addAddressDto);

        mockMvc.perform(post("/address/xyz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(addAddressJson)
        ).andExpect(status().isForbidden()
        );
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    void getAddress_Returns200OK_WithValidUsernameAndAdminRole() throws Exception{
        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);

        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        AddAddressDto addAddressDto2 = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        addAddressDto2.setUserId(null);
        addressServices.addAddress(user.getUsername(),addAddressDto);
        addressServices.addAddress(user.getUsername(),addAddressDto2);

        var result = mockMvc.perform(get("/address/" + user.getUsername())
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.[1].id").isNumber()
        ).andExpect(jsonPath("$.[1].street").value(addAddressDto.getStreet())
        ).andExpect(jsonPath("$.[1].city").value(addAddressDto.getCity())
        ).andExpect(jsonPath("$.[1].state").value(addAddressDto.getState())
        ).andExpect(jsonPath("$.[1].country").value(addAddressDto.getCountry())
        ).andExpect(jsonPath("$.[1].zip").value(addAddressDto.getZip())
        ).andReturn();

        List<GetAddressDto> results = JsonPath.read(result.getResponse().getContentAsString(),"$");
        assertThat(results).hasSize(2);
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    void getAddress_Returns200OK_WithValidUsernameAndClientRole() throws Exception{
        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);

        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        AddAddressDto addAddressDto2 = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        addAddressDto2.setUserId(null);
        addressServices.addAddress(user.getUsername(),addAddressDto);
        addressServices.addAddress(user.getUsername(),addAddressDto2);

        var result = mockMvc.perform(get("/address/" + user.getUsername())
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.[1].id").isNumber()
        ).andExpect(jsonPath("$.[1].street").value(addAddressDto.getStreet())
        ).andExpect(jsonPath("$.[1].city").value(addAddressDto.getCity())
        ).andExpect(jsonPath("$.[1].state").value(addAddressDto.getState())
        ).andExpect(jsonPath("$.[1].country").value(addAddressDto.getCountry())
        ).andExpect(jsonPath("$.[1].zip").value(addAddressDto.getZip())
        ).andReturn();

        List<GetAddressDto> results = JsonPath.read(result.getResponse().getContentAsString(),"$");
        assertThat(results).hasSize(2);
    }

    @Test
    void getAddress_Returns401Unauthorized_WithNoAuthentication() throws Exception{

        mockMvc.perform(get("/address/aqrar")
        ).andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    void getAddress_Returns403Forbidden_WithInvalidUsernameSearch() throws Exception{

        mockMvc.perform(get("/address/sibghat")
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    void updateAddress_Returns200OK_WithValidUsernameAndAddressIdAndAdminRole() throws Exception{
        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);

        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        addressServices.addAddress(user.getUsername(),addAddressDto);

        UpdateAddressDto updateAddressDto = UpdateAddressDto.builder()
                .street("updated")
                .city("updated")
                .state("updated")
                .country("updated")
                .zip("12345")
                .build();

        String updateAddressJson = objectMapper.writeValueAsString(updateAddressDto);

        mockMvc.perform(put("/address/" + user.getUsername() + "/" + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateAddressJson)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.id").isNumber()
        ).andExpect(jsonPath("$.street").value(updateAddressDto.getStreet())
        ).andExpect(jsonPath("$.city").value(updateAddressDto.getCity())
        ).andExpect(jsonPath("$.state").value(updateAddressDto.getState())
        ).andExpect(jsonPath("$.country").value(updateAddressDto.getCountry())
        ).andExpect(jsonPath("$.zip").value(updateAddressDto.getZip())
        );
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    void updateAddress_Returns200OK_WithValidUsernameAndAddressIdAndClientRole() throws Exception{
        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);

        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        addressServices.addAddress(user.getUsername(),addAddressDto);

        UpdateAddressDto updateAddressDto = UpdateAddressDto.builder()
                .street("updated")
                .city("updated")
                .state("updated")
                .country("updated")
                .zip("12345")
                .build();

        String updateAddressJson = objectMapper.writeValueAsString(updateAddressDto);

        mockMvc.perform(put("/address/" + user.getUsername() + "/" + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateAddressJson)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.id").isNumber()
        ).andExpect(jsonPath("$.street").value(updateAddressDto.getStreet())
        ).andExpect(jsonPath("$.city").value(updateAddressDto.getCity())
        ).andExpect(jsonPath("$.state").value(updateAddressDto.getState())
        ).andExpect(jsonPath("$.country").value(updateAddressDto.getCountry())
        ).andExpect(jsonPath("$.zip").value(updateAddressDto.getZip())
        );
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    void updateAddress_Returns403Forbidden_WhenUpdatingAddressThatIsNotTheirs() throws Exception{
        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);

        User user2 = testDataUtil.validUser2();
        userRepository.saveAndFlush(user2);

        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        addressServices.addAddress(user2.getUsername(),addAddressDto);

        UpdateAddressDto updateAddressDto = UpdateAddressDto.builder()
                .street("updated")
                .city("updated")
                .state("updated")
                .country("updated")
                .zip("12345")
                .build();

        String updateAddressJson = objectMapper.writeValueAsString(updateAddressDto);

        mockMvc.perform(put("/address/" + user.getUsername() + "/" + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateAddressJson)
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    void updateAddress_Returns404NotFound_WithInvalidAddressId() throws Exception{
        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);

        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        addressServices.addAddress(user.getUsername(),addAddressDto);

        UpdateAddressDto updateAddressDto = UpdateAddressDto.builder()
                .street("updated")
                .city("updated")
                .state("updated")
                .country("updated")
                .zip("12345")
                .build();

        String updateAddressJson = objectMapper.writeValueAsString(updateAddressDto);

        mockMvc.perform(put("/address/" + user.getUsername() + "/" + "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateAddressJson)
        ).andExpect(status().isNotFound());
    }

    @Test
    void updateAddress_Returns403Unauthorized_WithNoAuthentication() throws Exception{

        UpdateAddressDto updateAddressDto = UpdateAddressDto.builder()
                .street("updated")
                .city("updated")
                .state("updated")
                .country("updated")
                .zip("12345")
                .build();

        String updateAddressJson = objectMapper.writeValueAsString(updateAddressDto);

        mockMvc.perform(put("/address/aqrar/" + "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateAddressJson)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    void updateAddress_Returns403Forbidden_WhenUpdatingAddressWithIncorrectUsername() throws Exception{

        UpdateAddressDto updateAddressDto = UpdateAddressDto.builder()
                .street("updated")
                .city("updated")
                .state("updated")
                .country("updated")
                .zip("12345")
                .build();

        String updateAddressJson = objectMapper.writeValueAsString(updateAddressDto);

        mockMvc.perform(put("/address/sibghat/" + "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateAddressJson)
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "CLIENT")
    void deleteAddress_Returns204NoContent_WithValidAddressIdAndUsernameAndClientRole() throws Exception{
        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);

        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        addressServices.addAddress(user.getUsername(),addAddressDto);


        mockMvc.perform(delete("/address/" + user.getUsername() + "/" + "1")
        ).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    void deleteAddress_Returns204NoContent_WithValidAddressIdAndUsernameAndAdminRole() throws Exception{
        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);

        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        addressServices.addAddress(user.getUsername(),addAddressDto);


        mockMvc.perform(delete("/address/" + user.getUsername() + "/" + "1")
        ).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    void deleteAddress_Returns404NotFound_WithInvalidAddressIdAndValidUsername() throws Exception{
        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);

        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        addressServices.addAddress(user.getUsername(),addAddressDto);


        mockMvc.perform(delete("/address/" + user.getUsername() + "/" + "2")
        ).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    void deleteAddress_Returns403Forbidden_WhenDeletingAddressThatIsNotTheirs() throws Exception{
        User user = testDataUtil.validUser1();
        User user2 = testDataUtil.validUser2();
        userRepository.saveAndFlush(user);
        userRepository.saveAndFlush(user2);

        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        AddAddressDto addAddressDto2 = addressTestDataUtil.addAddressDto();
        addAddressDto.setUserId(null);
        addAddressDto2.setUserId(null);
        addressServices.addAddress(user.getUsername(),addAddressDto);
        addressServices.addAddress(user2.getUsername(),addAddressDto2);


        mockMvc.perform(delete("/address/" + user.getUsername() + "/" + "2")
        ).andExpect(status().isForbidden());
    }

    @Test
    void deleteAddress_Returns401Unauthorized_WhenUnAuthenticated() throws Exception{
        mockMvc.perform(delete("/address/aqrar" + "1")
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "aqrar", roles = "ADMIN")
    void deleteAddress_Returns403Forbidden_WhenDeletingOtherUsersAddress() throws Exception{
        mockMvc.perform(delete("/address/sibghat/" + "1")
        ).andExpect(status().isForbidden());
    }
}
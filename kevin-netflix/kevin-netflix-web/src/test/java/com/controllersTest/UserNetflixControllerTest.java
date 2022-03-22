package com.controllersTest;

import com.app.AppNetflix;
import com.dtos.NetflixPlanDTO;
import com.dtos.UserNetflixDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.NetflixPlan;
import com.models.UserNetflix;
import com.services.UserNetflixService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest( classes = {AppNetflix.class})
@AutoConfigureMockMvc( addFilters = false )
@ActiveProfiles("kevin")
public class UserNetflixControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserNetflixService service;

    public static UserNetflixDTO userNetflixDTOToSave, userNetflixDTOSaved, userNetflixDTOtoUpdate, userNetflixDTOUpdated;
    public static UserNetflix userNetflixToSave, userNetflixSaved, userNetflixUpdated, userNetflixToUpdate;

    @BeforeAll
    public static void createProfiles(){
        userNetflixDTOToSave = UserNetflixDTO.builder()
                .username("teste")
                .password("123456")
                .build();

        userNetflixDTOSaved = UserNetflixDTO.builder()
                .id(1)
                .username("teste")
                .password(null)
                .role("USER")
                .build();

        userNetflixToSave = UserNetflix.builder()
                .username("teste")
                .password("123456")
                .build();

        userNetflixSaved = UserNetflix.builder()
                .id(1)
                .username("teste")
                .password(null)
                .role("USER")
                .build();

        userNetflixDTOtoUpdate = UserNetflixDTO.builder()
                .id(1)
                .username("teste update")
                .password("123456 update")
                .build();

        userNetflixUpdated = UserNetflix.builder()
                .id(1)
                .username("teste update")
                .password(null)
                .role("USER")
                .build();

        userNetflixToUpdate = UserNetflix.builder()
                .id(1)
                .username("teste update")
                .password("123456 update")
                .build();

        userNetflixDTOUpdated = UserNetflixDTO.builder()
                .id(1)
                .username("teste update")
                .password(null)
                .role("USER")
                .build();
    }

    @Test
    void createUserNetflix() throws Exception {
        when( service.createUserNetflix( userNetflixToSave ) ).thenReturn( userNetflixSaved );

        MvcResult mvcResult = mockMvc.perform( post("/userNetflix/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString( userNetflixDTOToSave )))
                .andExpect(status().isCreated())
                .andReturn();


        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( userNetflixDTOSaved ), response );
    }

    @Test
    void createUserNetflixWithoutAllFieldsThrowsMethodArgumentNotValidException() throws Exception {
        UserNetflixDTO userNetflixDTO = UserNetflixDTO.builder().build();

        mockMvc.perform(post("/userNetflix/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userNetflixDTO)))
                .andExpect(status().isBadRequest())
                .andExpect( result -> assertTrue( result.getResolvedException() instanceof MethodArgumentNotValidException) );
    }

    @Test
    void updateUserNetflix() throws Exception {

        when( service.updateUserNetflix( userNetflixToUpdate ) ).thenReturn( userNetflixUpdated );

        MvcResult mvcResult = mockMvc.perform( put("/userNetflix/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString( userNetflixDTOtoUpdate )))
                .andExpect(status().isOk())
                .andReturn();


        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( userNetflixDTOUpdated ), response );

    }

    @Test
    void updateUserNetflixWithoutAllFieldsThrowsMethodArgumentNotValidException() throws Exception {
        UserNetflixDTO userNetflixDTO = UserNetflixDTO.builder().id(1).build();

        mockMvc.perform(put("/userNetflix/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userNetflixDTO)))
                .andExpect(status().isBadRequest())
                .andExpect( result -> assertTrue( result.getResolvedException() instanceof MethodArgumentNotValidException) );
    }

    @Test
    void findById() throws Exception {
        when( service.findById( same( 1 ) ) ).thenReturn( userNetflixSaved );

        MvcResult mvcResult = this.mockMvc.perform(get("/userNetflix/1")).andExpect( status().isOk() ).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( userNetflixDTOSaved ), response );
    }

    @Test
    void adminAddPlanToUser() throws Exception {
        NetflixPlan netflixPlan = NetflixPlan.builder()
                .id(1)
                .planName("FAMILY")
                .value(30.0)
                .build();

        UserNetflix userNetflix = UserNetflix.builder()
                .id(1)
                .username("test")
                .password(null)
                .role("USER")
                .netflixPlan(netflixPlan)
                .build();

        NetflixPlanDTO netflixPlanDTO = NetflixPlanDTO.builder()
                .id(1)
                .planName("FAMILY")
                .value(30.0)
                .build();

        UserNetflixDTO userNetflixDTO = UserNetflixDTO.builder()
                .id(1)
                .username("test")
                .password(null)
                .role("USER")
                .netflixPlan(netflixPlan)
                .build();

        when( service.addPlanToUser( "test", "FAMILY") ).thenReturn( userNetflix );

        MvcResult mvcResult = mockMvc.perform( post("/userNetflix/user/test/plan/FAMILY"))
                .andExpect(status().isOk())
                .andReturn();


        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( userNetflixDTO ), response );

    }

}

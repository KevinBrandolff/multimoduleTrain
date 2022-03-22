package com.controllersTest;

import com.app.AppNetflix;
import com.dtos.NetflixPlanDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.NetflixPlan;
import com.services.NetflixPlanService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
public class NetflixPlanControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NetflixPlanService service;

    public static NetflixPlanDTO netflixPlanDTOToSave, netflixPlanDTOSaved, netflixPlanDTOtoUpdate, netflixPlanDTOUpdated;
    public static NetflixPlan netflixPlanSaved, netflixPlanUpdated, netflixPlanToUpdate;

    @BeforeAll
    public static void createProfiles(){
        netflixPlanDTOToSave = NetflixPlanDTO.builder()
                .planName("FAMILY")
                .value(30.0)
                .build();

        netflixPlanDTOSaved = NetflixPlanDTO.builder()
                .id(1)
                .planName("FAMILY")
                .value(30.0)
                .build();

        netflixPlanSaved = NetflixPlan.builder()
                .id(1)
                .planName("FAMILY")
                .value(30.0)
                .build();

        netflixPlanDTOtoUpdate = NetflixPlanDTO.builder()
                .id(1)
                .planName("NORMAL")
                .value(40.0)
                .build();

        netflixPlanUpdated = NetflixPlan.builder()
                .id(1)
                .planName("NORMAL")
                .value(40.0)
                .build();

        netflixPlanToUpdate = NetflixPlan.builder()
                .id(1)
                .planName("NORMAL")
                .value(40.0)
                .build();

        netflixPlanDTOUpdated = NetflixPlanDTO.builder()
                .id(1)
                .planName("NORMAL")
                .value(40.0)
                .build();
    }

    @Test
    void createNetflixPlan() throws Exception {
        Mockito.when( service.createNetflixPlan( any( NetflixPlan.class ) ) ).thenReturn( netflixPlanSaved );

        MvcResult mvcResult = mockMvc.perform( post("/netflixPlan/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString( netflixPlanDTOToSave )))
                .andExpect(status().isCreated())
                .andReturn();


        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( netflixPlanDTOSaved ), response );
    }

    @Test
    void createNetflixPlanWithoutAllFieldsThrowsMethodArgumentNotValidException() throws Exception {
        NetflixPlanDTO netflixPlanDTO = NetflixPlanDTO.builder().build();

        mockMvc.perform(post("/netflixPlan/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(netflixPlanDTO)))
                .andExpect(status().isBadRequest())
                .andExpect( result -> assertTrue( result.getResolvedException() instanceof MethodArgumentNotValidException) );

    }

    @Test
    void updateNetflixPlan() throws Exception {
        Mockito.when( service.updateNetflixPlan( netflixPlanToUpdate ) ).thenReturn( netflixPlanUpdated );

        MvcResult mvcResult = mockMvc.perform( put("/netflixPlan/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString( netflixPlanDTOtoUpdate )))
                .andExpect(status().isOk())
                .andReturn();


        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( netflixPlanDTOUpdated ), response );
    }

    @Test
    void updateNetflixPlanWithoutAllFieldsThrowsMethodArgumentNotValidException() throws Exception {
        NetflixPlanDTO netflixPlanDTO = NetflixPlanDTO.builder().id(1).build();

        mockMvc.perform(put("/netflixPlan/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(netflixPlanDTO)))
                .andExpect(status().isBadRequest())
                .andExpect( result -> assertTrue( result.getResolvedException() instanceof MethodArgumentNotValidException) );

    }

    @Test
    void findById() throws Exception {
        when( service.findById( same( 1 ) ) ).thenReturn( netflixPlanSaved );

        MvcResult mvcResult = this.mockMvc.perform(get("/netflixPlan/1")).andExpect( status().isOk() ).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( netflixPlanDTOSaved ), response );
    }

}

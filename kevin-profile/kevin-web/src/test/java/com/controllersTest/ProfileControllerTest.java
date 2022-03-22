package com.controllersTest;

import com.app.App;
import com.dtos.ProfileDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Profile;
import com.services.ProfileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest( classes = {App.class})
@AutoConfigureMockMvc( addFilters = false )
@ActiveProfiles("kevin")
public class ProfileControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService service;

    public static ProfileDTO profileDTOToSave, profileDTOSaved, profileDTOtoUpdate, profileDTOUpdated;
    public static Profile profileSaved, profileUpdated, profileToUpdate;

    @BeforeAll
    public static void createProfiles(){
        profileDTOToSave = ProfileDTO.builder()
                .name("kevin")
                .age(21)
                .interests("surf")
                .build();

        profileDTOSaved = ProfileDTO.builder()
                .id(1)
                .name("kevin")
                .age(21)
                .interests("surf")
                .createAt(LocalDate.now())
                .build();

        profileSaved = Profile.builder()
                .id(1)
                .name("kevin")
                .age(21)
                .interests("surf")
                .createAt(LocalDate.now())
                .build();

        profileDTOtoUpdate = ProfileDTO.builder()
                .id(1)
                .name("kevin to update")
                .age(22)
                .interests("surf")
                .createAt(LocalDate.now())
                .build();

        profileUpdated = Profile.builder()
                .id(1)
                .name("kevin to update")
                .age(22)
                .interests("surf")
                .createAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        profileToUpdate = Profile.builder()
                .id(1)
                .name("kevin to update")
                .age(22)
                .interests("surf")
                .createAt(LocalDate.now())
                .build();

        profileDTOUpdated = ProfileDTO.builder()
                .id(1)
                .name("kevin to update")
                .age(22)
                .interests("surf")
                .createAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();
    }

    @Test
    public void shouldReturnAEmployeeByIdWithStatusOk() throws Exception {

        when( service.findById( any( Integer.class ) ) ).thenReturn( profileSaved );

        MvcResult mvcResult = this.mockMvc.perform(get("/profile/1")).andExpect( status().isOk() ).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( profileSaved ), response );

    }

    @Test
    public void shouldCreateAEmployeeWithStatusCreated() throws Exception {

        when( service.createProfile( any( Profile.class ) ) ).thenReturn( profileSaved );

        MvcResult mvcResult = mockMvc.perform(post("/profile/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(profileDTOToSave)))
                .andExpect(status().isCreated())
                .andReturn();


        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( profileSaved ), response );

    }

    @Test
    public void shouldUpdateAEmployeeWithStatusOk() throws Exception {

        when( service.updateProfile( profileToUpdate ) ).thenReturn( profileUpdated );

        MvcResult mvcResult = mockMvc.perform(put("/profile/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString( profileDTOtoUpdate )))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( profileDTOUpdated ), response );

    }

    @Test
    public void shouldFindAllWithStatusOk() throws Exception {

        when( service.findAll() ).thenReturn( Arrays.asList(profileSaved));

        MvcResult mvcResult = this.mockMvc.perform(get("/profile/")).andExpect( status().isOk() ).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertEquals( objectMapper.writeValueAsString( Arrays.asList(profileDTOSaved) ), response );

    }

}

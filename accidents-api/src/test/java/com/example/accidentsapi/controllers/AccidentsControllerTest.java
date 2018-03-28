package com.example.accidentsapi.controllers;
import com.example.accidentsapi.models.Accident;
import com.example.accidentsapi.repositories.AccidentRepository;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;


import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(AccidentsController.class)

public class AccidentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Accident newAccident;

    private Accident updatedSecondAccident;

    @Autowired
    private ObjectMapper jsonObjectMapper;

    @MockBean
    private AccidentRepository mockAccidentRepository;

    @Before
    public void setUp() {
        Accident firstAccident = new Accident(
                "date1",
                "time1",
                "borough1",
                "zip1",
                12345.0,
                -12345.0,
                "notes1", 0,0
        );

        Accident secondAccident = new Accident(
                "date2",
                "time2",
                "borough2",
                "zip2",
                123.9,
                -123.9,
                "notes2", 0,0
        );

        newAccident = new Accident(
                "newDate",
                "newTime",
                "newBorough",
                "newZip",
                1111.0,
                1111.0,
                "notes3", 0, 0
        );
        given(mockAccidentRepository.save(newAccident)).willReturn(newAccident);

        updatedSecondAccident = new Accident(
                "updated_date",
                "updated_time",
                "updated_borough",
                "updated_zip",
                2222.0,
                2222.0,
                "notes4", 0, 0
        );
        given(mockAccidentRepository.save(updatedSecondAccident)).willReturn(updatedSecondAccident);


        Iterable<Accident> mockAccidents =
                Stream.of(firstAccident, secondAccident).collect(Collectors.toList());
        given(mockAccidentRepository.findAll()).willReturn(mockAccidents);
        given(mockAccidentRepository.findOne(1L)).willReturn(firstAccident);
        given(mockAccidentRepository.findOne(4L)).willReturn(null);
        doAnswer(invocation -> {
            throw new EmptyResultDataAccessException("ERROR MESSAGE FROM MOCK!!!", 1234);
        }).when(mockAccidentRepository).delete(4L);
    }

    @Test
    public void findAllAccidents_success_returnsStatusOK() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllAccidents_success_returnAllUsersAsJSON() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void findAllAccidents_success_returnDateForEachAcccident() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].date", is("date1")));
    }

    @Test
    public void findAllAccidents_success_returnTimeForEachAcccident() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].time", is("time1")));
    }

    @Test
    public void findAllAccidents_success_returnBoroughForEachAccident() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].borough", is("borough1")));
    }
    @Test
    public void findAllAccidents_success_returnZipForEachAcccident() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].zip_code", is("zip1")));
    }


    @Test
    public void findAllAccidents_success_returnLatitudeForEachAcccident() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].latitude", is(12345.0)));
    }

    @Test
    public void findAllAccidents_success_returnLongitudeForEachAcccident() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].longitude", is(-12345.0)));
    }

    @Test
    public void findAllAccidents_success_returnInjuredForEachAcccident() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].number_of_persons_injured", is(0)));
    }

    @Test
    public void findAllAccidents_success_returnKilledForEachAcccident() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].number_of_persons_killed", is(0)));
    }


    @Test
    public void findAccidentById_success_returnsStatusOK() throws Exception {

        this.mockMvc
                .perform(get("/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAccidentById_success_returnBorough() throws Exception {

        this.mockMvc
                .perform(get("/1"))
                .andExpect(jsonPath("$.borough", is("borough1")));
    }

    @Test
    public void findAccidentById_success_returndate() throws Exception {

        this.mockMvc
                .perform(get("/1"))
                .andExpect(jsonPath("$.date", is("date1")));
    }

    @Test
    public void findAccidentById_success_returnLatitude() throws Exception {

        this.mockMvc
                .perform(get("/1"))
                .andExpect(jsonPath("$.latitude", is(12345.0)));
    }
    @Test
    public void findAccidentById_failure_accidentNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(get("/4"))
                .andExpect(status().reason(containsString("Accident with ID of 4 was not found!")));
    }

    @Test
    public void deleteAccidentById_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(delete("/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAccidentById_success_deletesViaRepository() throws Exception {

        this.mockMvc.perform(delete("/1"));

        verify(mockAccidentRepository, times(1)).delete(1L);
    }

    @Test
    public void deleteAccidentById_failure_userNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(delete("/4"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void createAccident_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newAccident))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void createAccident_success_returnsUserName() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newAccident))
                )
                .andExpect(jsonPath("$.borough", is("newBorough")));
    }

    @Test
    public void createAccident_success_returnsFirstName() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newAccident))
                )
                .andExpect(jsonPath("$.date", is("newDate")));
    }

    @Test
    public void createAccident_success_returnsLatitude() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newAccident))
                )
                .andExpect(jsonPath("$.latitude", is(1111.0)));
    }
    @Test
    public void updateAccidentById_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondAccident))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void updateAccidentById_success_returnsUpdatedDate() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondAccident))
                )
                .andExpect(jsonPath("$.date", is("updated_date")));
    }

    @Test
    public void updateAccidentById_success_returnsUpdatedBorough() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondAccident))
                )
                .andExpect(jsonPath("$.borough", is("updated_borough")));
    }

    @Test
    public void updateAccidentById_success_returnsUpdatedlongitude() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondAccident))
                )
                .andExpect(jsonPath("$.longitude", is(2222.0)));
    }

    @Test
    public void updateAccidentById_failure_userNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(
                        patch("/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondAccident))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAccidentById_failure_userNotFoundReturnsNotFoundErrorMessage() throws Exception {

        this.mockMvc
                .perform(
                        patch("/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondAccident))
                )
                .andExpect(status().reason(containsString("Accident with ID of 4 was not found!")));
    }

}

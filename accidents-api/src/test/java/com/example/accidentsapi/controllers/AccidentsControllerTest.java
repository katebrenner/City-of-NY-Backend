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
                -12345.0
        );

        Accident secondAccident = new Accident(
                "date2",
                "time2",
                "borough2",
                "zip2",
                123.9,
                -123.9
        );


        Iterable<Accident> mockAccidents =
                Stream.of(firstAccident, secondAccident).collect(Collectors.toList());

        given(mockAccidentRepository.findAll()).willReturn(mockAccidents);
        given(mockAccidentRepository.findOne(1L)).willReturn(firstAccident);
        given(mockAccidentRepository.findOne(4L)).willReturn(null);
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
    public void findAllAccidents_success_returnBoroughForEachAccident() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].borough", is("borough1")));
    }

    @Test
    public void findAllAccidents_success_returnDateForEachAcccident() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].date", is("date1")));
    }

    @Test
    public void findAllAccidents_success_returnLatitudeForEachAcccident() throws Exception {

        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].latitude", is(12345.0)));
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

}

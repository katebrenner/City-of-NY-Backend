package com.example.cityofnewyorkbackend.features;

import com.example.cityofnewyorkbackend.models.Accident;
import com.example.cityofnewyorkbackend.repositories.AccidentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;

import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccidentsApiFeatureTest {


    @Autowired
    private AccidentRepository accidentRepository;

    @Before
    public void setUp() {
        accidentRepository.deleteAll();
    }

    @After
    public void tearDown() {
        accidentRepository.deleteAll();
    }

    @Test
    public void shouldAllowFullCrudForAUser() throws Exception {

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


        Stream.of(firstAccident, secondAccident)
                .forEach(accident -> {
                    accidentRepository.save(accident);
                });

        when()
                .get("http://localhost:8080/accidents")
                .then()
                .statusCode(is(200))
                .and().body(containsString("borough1"))
                .and().body(containsString("time2"));

        Accident accidentNotYetInDb = new Accident(
                "Accident",
                "Not",
                "Yet", "Created", 000.00, 000.00
        );

        given()
                .contentType(JSON)
                .and().body(accidentNotYetInDb)
                .when()
                .post("http://localhost:8080/accidents")
                .then()
                .statusCode(is(200))
                .and().body(containsString("Accident"));

// Test get all Accidents

        when()
                .get("http://localhost:8080/accidents/")
                .then()
                .statusCode(is(200))
                .and().body(containsString("borough1"))
                .and().body(containsString("time2"))
                .and().body(containsString("Created"));

// Test finding one accident by ID
        when()
                .get("http://localhost:8080/accidents/" + secondAccident.getId())
                .then()
                .statusCode(is(200))
                .and().body(containsString("date2"))
                .and().body(containsString("time2"));

// Test updating a accident
        secondAccident.setBorough("changed_borough");

        given()
                .contentType(JSON)
                .and().body(secondAccident)
                .when()
                .patch("http://localhost:8080/accidents/" + secondAccident.getId())
                .then()
                .statusCode(is(200))
                .and().body(containsString("changed_borough"));

// Test deleting a accident
        when()
                .delete("http://localhost:8080/accidents/" + secondAccident.getId())
                .then()
                .statusCode(is(200));

    }
}

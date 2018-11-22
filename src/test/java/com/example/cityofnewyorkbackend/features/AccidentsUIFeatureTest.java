package com.example.cityofnewyorkbackend.features;

import com.example.cityofnewyorkbackend.models.Accident;
import com.example.cityofnewyorkbackend.repositories.AccidentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccidentsUIFeatureTest {


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
    public void shouldAllowFullCrudForAnAccident() throws Exception {

        Accident firstAccident = new Accident(
                "date1",
                "time1",
                "borough1",
                "zip1",
                12345.0,
                -12345.0,
                "notes1",
                0,
                0

        );

        Accident secondAccident = new Accident(
                "date2",
                "time2",
                "borough2",
                "zip2",
                123.9,
                -123.9,
                "notes1",
                0,
                0
        );


        Stream.of(firstAccident, secondAccident)
                .forEach(accident -> {
                    accidentRepository.save(accident);
                });

        System.setProperty("selenide.browser", "Chrome");

        open("http://localhost:3000");

        // There should only be two users
        $$("[data-user-display]").shouldHave(size(2));

    }
}

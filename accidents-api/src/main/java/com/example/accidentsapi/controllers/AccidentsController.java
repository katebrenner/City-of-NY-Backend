package com.example.accidentsapi.controllers;

import com.example.accidentsapi.models.Accident;
import com.example.accidentsapi.repositories.AccidentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AccidentsController {

    @Autowired
    private AccidentRepository accidentRepository;

    @GetMapping("/")
    public Iterable<Accident> findAllUsers() {
        return accidentRepository.findAll();
    }

    @GetMapping("/{accidentId}")
    public Accident findAccidentById(@PathVariable Long accidentId) throws NotFoundException {

        Accident foundAccident = accidentRepository.findOne(accidentId);

        if (foundAccident == null) {
            throw new NotFoundException("Accident with ID of " + accidentId + " was not found!");
        }

        return foundAccident;
    }

    @ExceptionHandler
    void handleAccidentNotFound(
            NotFoundException exception,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @DeleteMapping("/{accidentId}")
    public HttpStatus deleteAccidentById(@PathVariable Long accidentId) {
        return HttpStatus.OK;
    }

}

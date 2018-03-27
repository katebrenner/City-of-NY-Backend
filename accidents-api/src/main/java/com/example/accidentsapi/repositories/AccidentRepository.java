package com.example.accidentsapi.repositories;

import com.example.accidentsapi.models.Accident;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public interface AccidentRepository extends CrudRepository<Accident, Long>{

}

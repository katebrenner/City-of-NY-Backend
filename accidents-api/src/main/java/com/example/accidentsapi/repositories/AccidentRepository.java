package com.example.accidentsapi.repositories;

import com.example.accidentsapi.models.Accident;
import org.springframework.data.repository.CrudRepository;

public interface AccidentRepository extends CrudRepository<Accident, Long>{
}

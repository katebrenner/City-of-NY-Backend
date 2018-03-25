package com.example.cityofnewyorkbackend.repositories;

import com.example.cityofnewyorkbackend.models.Accident;
import org.springframework.data.repository.CrudRepository;

public interface AccidentRepository extends CrudRepository<Accident, Long>{
}


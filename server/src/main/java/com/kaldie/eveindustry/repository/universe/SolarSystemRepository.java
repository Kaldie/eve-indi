package com.kaldie.eveindustry.repository.universe;
import com.kaldie.eveindustry.repository.BulkInsertUpdateRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolarSystemRepository extends 
    CrudRepository <SolarSystem, Long>, BulkInsertUpdateRepository<SolarSystem> { 
}

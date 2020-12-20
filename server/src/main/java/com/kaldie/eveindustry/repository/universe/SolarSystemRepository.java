package com.kaldie.eveindustry.repository.universe;

import com.kaldie.eveindustry.repository.BulkInsertUpdateRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolarSystemRepository extends BulkInsertUpdateRepository<SolarSystem>, JpaRepository<SolarSystem, Long> { 
}

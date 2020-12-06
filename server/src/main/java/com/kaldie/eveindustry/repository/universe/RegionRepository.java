package com.kaldie.eveindustry.repository.universe;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends CrudRepository<Region, Long>{

    @Query(value = 
    "FROM Region t "  + 
    "JOIN FETCH t.name " + 
    "WHERE t.name.itemName = :name")
    Region getRegionByName(String name);

} 

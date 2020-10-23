package com.kaldie.eveindustry.repository.type_id;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeIDRepository extends CrudRepository<TypeId,Long>{
    
    @Query(value = 
        "SELECT t " + 
        "FROM TypeId t "  + 
        "JOIN FETCH t.name " + 
        "JOIN FETCH t.description " + 
        " " + 
        "WHERE t.id = :id")
    Optional<TypeId> findLoadedTypeId(@Param("id") Long id);
}

package com.kaldie.eveindustry.repository.groups;

import com.kaldie.eveindustry.repository.BulkInsertUpdateRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatagoryRepository extends 
    CrudRepository<Catagory,Long>, 
    BulkInsertUpdateRepository<Catagory>{
}

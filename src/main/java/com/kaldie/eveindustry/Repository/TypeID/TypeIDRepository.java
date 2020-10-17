package com.kaldie.eveindustry.Repository.TypeID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeIDRepository extends CrudRepository<TypeId,Long>{
    
}

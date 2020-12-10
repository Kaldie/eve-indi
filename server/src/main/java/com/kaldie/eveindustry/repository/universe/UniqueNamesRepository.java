package com.kaldie.eveindustry.repository.universe;

import org.springframework.stereotype.Repository;

import com.kaldie.eveindustry.repository.BulkInsertUpdateRepository;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface UniqueNamesRepository 
    extends CrudRepository <UniqueName, Long>, 
            BulkInsertUpdateRepository<UniqueName> {}

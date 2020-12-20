package com.kaldie.eveindustry.repository.universe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaldie.eveindustry.repository.BulkInsertUpdateRepository;

@Repository
public interface UniqueNamesRepository 
    extends JpaRepository<UniqueName,Long>, BulkInsertUpdateRepository<UniqueName> {}

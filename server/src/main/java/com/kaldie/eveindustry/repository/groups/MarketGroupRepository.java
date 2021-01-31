package com.kaldie.eveindustry.repository.groups;

import com.kaldie.eveindustry.repository.BulkInsertUpdateRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketGroupRepository extends CrudRepository<MarketGroup,Long>, BulkInsertUpdateRepository<MarketGroup>{}


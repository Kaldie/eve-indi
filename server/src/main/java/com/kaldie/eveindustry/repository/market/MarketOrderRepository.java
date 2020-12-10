package com.kaldie.eveindustry.repository.market;

import java.util.List;

import com.kaldie.eveindustry.repository.universe.SolarSystem;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface MarketOrderRepository extends CrudRepository<MarketOrder, Long>, CustomMarketOrderRepository{

    @Query(value = 
        "SELECT mo " + 
        "FROM MarketOrder mo "  + 
        "JOIN FETCH mo.typeId " + 
        "JOIN FETCH mo.systemId " +
        "WHERE mo.systemId = :system",
    countQuery = 
        "SELECT count(mo) " + 
        "FROM MarketOrder mo " + 
        "WHERE mo.systemId = :system")
    public Page<MarketOrder> getMarketOrderBySystemId(SolarSystem system, Pageable pageable);

}


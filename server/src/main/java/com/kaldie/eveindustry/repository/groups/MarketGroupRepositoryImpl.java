package com.kaldie.eveindustry.repository.groups;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import lombok.Data;

@Repository
@Data
public class MarketGroupRepositoryImpl implements MarketGroupRepository{

    Logger logger = LoggerFactory.getLogger(MarketGroupRepositoryImpl.class);
    private final EntityManager entityManager;

    @Override
    public Iterable<MarketGroup>findAllWithName(){
        return entityManager.createQuery(
            "SELECT mg.id, mg.parentGroup.id, mg.name.en " + 
            "FROM market_group mg " +
            "JOIN mg.name ").getResultList();
    }

    @Override
    public Iterable<MarketGroupLineage> findMarketGroupLineage(Long id) {
        return entityManager.createNamedQuery("MarketGroupLineage.findMarketGroupLineage")
        .setParameter("id", id)
        .getResultList();
    }
    
}

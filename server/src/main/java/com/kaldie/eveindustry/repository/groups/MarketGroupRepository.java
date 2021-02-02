package com.kaldie.eveindustry.repository.groups;

import org.springframework.stereotype.Repository;

@Repository
public interface MarketGroupRepository {

    public Iterable<MarketGroup>findAllWithName();
    public Iterable<MarketGroupLineage>findMarketGroupLineage(Long id);
}


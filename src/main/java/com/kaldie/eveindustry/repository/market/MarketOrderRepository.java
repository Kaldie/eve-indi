package com.kaldie.eveindustry.repository.market;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MarketOrderRepository extends CrudRepository<MarketOrder, Long>, CustomMarketOrderRepository{}


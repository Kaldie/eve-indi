package com.kaldie.eveindustry.repository.market;

import java.util.List;

import net.troja.eve.esi.model.MarketOrdersResponse;

public interface CustomMarketOrderRepository {

    public void updateFromMarketOrderResponses(List<MarketOrdersResponse> responses);
    
}

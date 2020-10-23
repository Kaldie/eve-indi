package com.kaldie.eveindustry.service.experiments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kaldie.eveindustry.repository.type_id.TypeIDRepository;
import com.kaldie.eveindustry.repository.universe.RegionRepository;
import com.kaldie.eveindustry.service.experiments.MarketOppurtunities;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import net.troja.eve.esi.model.MarketOrdersResponse;

public class MarketOppurtunitiesTest {

    @Mock
    RegionRepository regionRepository;

    @Mock
    TypeIDRepository typeIdRepository;

    private MarketOppurtunities marketOppurtunities = new MarketOppurtunities(regionRepository, typeIdRepository);
    List<MarketOrdersResponse> buyOrders = Arrays.asList(
        new MarketOrdersResponse().typeId(10).isBuyOrder(true).locationId(11L).price(100.00),
        new MarketOrdersResponse().typeId(10).isBuyOrder(true).locationId(11L).price(150.00)
    );
    
    List<MarketOrdersResponse> buyorderMultipleItems = Arrays.asList(
        new MarketOrdersResponse().typeId(10).isBuyOrder(true).locationId(11L).price(100.00),
        new MarketOrdersResponse().typeId(10).isBuyOrder(true).locationId(11L).price(150.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(true).locationId(11L).price(170.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(true).locationId(11L).price(150.00)
    );

    List<MarketOrdersResponse> buyAndSellOrders = Arrays.asList(
        new MarketOrdersResponse().typeId(11).isBuyOrder(true).locationId(11L).price(170.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(true).locationId(11L).price(150.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(false).locationId(11L).price(220.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(false).locationId(11L).price(230.00)
    );


    @Test
    public void testBuyPrice() {
        assertNotNull(marketOppurtunities);
        Map<Integer, Double> outcome = marketOppurtunities.getBuyPrice(buyOrders);
        assertNotNull(outcome);
        assertNotNull(outcome.get(10));
        assertEquals((Double)150.000, outcome.get(10));
    }

    @Test
    public void testBuyPriceMultipleTypes() {
        Map<Integer, Double> outcome = marketOppurtunities.getBuyPrice(buyorderMultipleItems);
        assertNotNull(outcome.get(10));
        assertEquals((Double)150.000, outcome.get(10));
        assertNotNull(outcome.get(11));
        assertEquals((Double)170.000, outcome.get(11));
    }

    @Test
    public void testBuyPriceMultipleTypesIncludingSellOrders() {
        Map<Integer, Double> outcome = marketOppurtunities.getBuyPrice(buyAndSellOrders);
        assertNotNull(outcome.get(11));
        assertEquals((Double)170.000, outcome.get(11));
    }

    
    
}

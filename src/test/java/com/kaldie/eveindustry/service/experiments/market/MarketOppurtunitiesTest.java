package com.kaldie.eveindustry.service.experiments.market;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kaldie.eveindustry.repository.type_id.TypeIDRepository;
import com.kaldie.eveindustry.repository.universe.RegionRepository;

import org.apache.commons.lang3.tuple.MutablePair;
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

    List<MarketOrdersResponse> buyAndSellOrdersAndDifferentTypes = Arrays.asList(
        new MarketOrdersResponse().typeId(11).isBuyOrder(true).locationId(11L).price(10.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(true).locationId(11L).price(20.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(false).locationId(11L).price(30.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(false).locationId(11L).price(35.00),
        new MarketOrdersResponse().typeId(10).isBuyOrder(true).locationId(11L).price(100.00),
        new MarketOrdersResponse().typeId(10).isBuyOrder(true).locationId(11L).price(110.00),
        new MarketOrdersResponse().typeId(10).isBuyOrder(false).locationId(11L).price(220.00),
        new MarketOrdersResponse().typeId(10).isBuyOrder(false).locationId(11L).price(230.00)
    );

    List<MarketOrdersResponse> buyAndSellOrdersAndMultipleLocations = Arrays.asList(
        new MarketOrdersResponse().typeId(11).isBuyOrder(true).locationId(11L).price(110.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(true).locationId(11L).price(120.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(false).locationId(11L).price(125.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(false).locationId(11L).price(130.00),

        new MarketOrdersResponse().typeId(11).isBuyOrder(true).locationId(12L).price(100.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(true).locationId(12L).price(105.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(false).locationId(12L).price(110.00),
        new MarketOrdersResponse().typeId(11).isBuyOrder(false).locationId(12L).price(111.00),

        new MarketOrdersResponse().typeId(10).isBuyOrder(true).locationId(11L).price(109.00),
        new MarketOrdersResponse().typeId(10).isBuyOrder(true).locationId(11L).price(119.00),
        new MarketOrdersResponse().typeId(10).isBuyOrder(false).locationId(11L).price(124.00),
        new MarketOrdersResponse().typeId(10).isBuyOrder(false).locationId(11L).price(129.00),

        new MarketOrdersResponse().typeId(10).isBuyOrder(true).locationId(11L).price(99.00),
        new MarketOrdersResponse().typeId(10).isBuyOrder(true).locationId(11L).price(104.00),
        new MarketOrdersResponse().typeId(10).isBuyOrder(false).locationId(11L).price(109.00),
        new MarketOrdersResponse().typeId(10).isBuyOrder(false).locationId(11L).price(110.00)
    );


    @Test
    public void testBuyPrice() {
        assertNotNull(marketOppurtunities);
        Map<Integer, Double> outcome = marketOppurtunities.getBuyPrice(buyOrders);
        assertNotNull(outcome);
        assertNotNull(outcome.get(10));
        assertEquals((Double)150.000, outcome.get(10));
        // assertEquals(150.000, outcome_.get(11L).get(10).left );

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
        Map<Integer, Double> outcome = marketOppurtunities.getBuyPrice(buyAndSellOrdersAndMultipleLocations);
        assertNotNull(outcome.get(11));
        assertEquals((Double)120.000, outcome.get(11));
    }
    

    @Test
    public void testBuyAndSellPrices() {
        HashMap<Long,HashMap<Integer,MutablePair<Double,Double>>> outcome_ = marketOppurtunities.getBuyAndSellPrice(buyOrders);
        assertNotNull(outcome_);
  
        assertNotNull(outcome_.keySet());
        assertNotEquals(0, outcome_.keySet().size());
        assertNotNull(outcome_.get(11L));
        assertNotNull(outcome_.get(11L).get(10));
        assertEquals(150.0, outcome_.get(11L).get(10).left);
        assertEquals(Double.MAX_VALUE, outcome_.get(11L).get(10).right);
    }

    @Test
    public void testBuyAndSellMultipleItems() {
        HashMap<Long,HashMap<Integer,MutablePair<Double,Double>>> outcome_ = marketOppurtunities.getBuyAndSellPrice(buyorderMultipleItems);
        assertNotNull(outcome_);

        assertNotNull(outcome_.keySet());
        assertNotEquals(0, outcome_.keySet().size());

        assertNotNull(outcome_.get(11L));
        assertNotNull(outcome_.get(11L).get(10));
        assertEquals(150.0, outcome_.get(11L).get(10).left);

        assertNotNull(outcome_.get(11L));
        assertNotNull(outcome_.get(11L).get(10));
        assertEquals(170.0, outcome_.get(11L).get(11).left);
    }

    @Test
    public void testBuyAndSellBuyAndSellOrders() {
        HashMap<Long,HashMap<Integer,MutablePair<Double,Double>>> outcome_ = marketOppurtunities.getBuyAndSellPrice(buyAndSellOrders);
        assertNotNull(outcome_);

        assertNotNull(outcome_.keySet());
        assertNotEquals(0, outcome_.keySet().size());

        assertNotNull(outcome_.get(11L));
        assertNotNull(outcome_.get(11L).get(11));
        assertEquals(170.0, outcome_.get(11L).get(11).left);
        assertEquals(220.0, outcome_.get(11L).get(11).right);
    }

    @Test
    public void testBuyAndSellBuyAndSellOrdersDifferentTypes() {
        HashMap<Long,HashMap<Integer,MutablePair<Double,Double>>> outcome_ = marketOppurtunities.getBuyAndSellPrice(buyAndSellOrdersAndMultipleLocations);
        assertNotNull(outcome_);

        assertNotNull(outcome_.keySet());
        assertNotEquals(0, outcome_.keySet().size());

        assertNotNull(outcome_.get(11L));
        assertNotNull(outcome_.get(11L).get(11));
        assertEquals(120.0, outcome_.get(11L).get(11).left);
        assertEquals(125.0, outcome_.get(11L).get(11).right);

        assertNotNull(outcome_.get(12L));
        assertNotNull(outcome_.get(12L).get(11));
        assertEquals(105.0, outcome_.get(12L).get(11).left);
        assertEquals(110.0, outcome_.get(12L).get(11).right);

    }
    
}


package com.kaldie.eveindustry.service.task.market;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Predicate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kaldie.eveindustry.client.esi.MarketOrders;
import com.kaldie.eveindustry.repository.market.MarketOrderRepository;
import com.kaldie.eveindustry.repository.type_id.TypeIDRepository;
import com.kaldie.eveindustry.repository.universe.Region;
import com.kaldie.eveindustry.repository.universe.RegionRepository;
import com.kaldie.eveindustry.service.task.Task;

import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import net.troja.eve.esi.model.MarketOrdersResponse;

@Component
@com.kaldie.eveindustry.annotations.Experiment
@RequiredArgsConstructor
public class MarketOppurtunities extends Task {

    private static Logger logger = LoggerFactory.getLogger(MarketOppurtunities.class);

    private final RegionRepository regionRepository;
    private final TypeIDRepository typeIdRepository;
    private final MarketOrderRepository marketOrderRepository;

    @Override
    public void run() {

        List<Region> regions = Arrays.asList(
                regionRepository.getRegionByName("The Forge"),
                regionRepository.getRegionByName("Domain"));

                
        regions.forEach(region -> {
                    
            logger.info("Requestion market orders from {} ", region.getName().getItemName());
            try {
                List<MarketOrdersResponse> orders = MarketOrders.getRegionalItemOrders(region.getRegionID());
                marketOrderRepository.updateFromMarketOrderResponses(orders);
            } catch ( Exception e) {
                logger.warn("Could not get all orders from {}!", region.getName());
                logger.error(e.toString());
                logger.error("{}", e.getStackTrace());
            }
        });

    }

    private static void writeOrdersToFile(List<MarketOrdersResponse> orders) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            mapper.writeValue(new File("target/example_orders2.json"), orders);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void filterOrdersOnDifferential(HashMap<Long, HashMap<Integer, MutablePair<Double, Double>>> orders) {

        // HashMap<Long,HashMap<Integer,MutablePair<Double,Double>>> filteredOrders =
        // (HashMap<Long,HashMap<Integer,MutablePair<Double,Double>>>)
        // orders.entrySet().stream()
        // // .filter(entry -> entry.getValue().size() > 10000)
        // .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        List<Integer> removedEntries = new ArrayList<>();

        orders.values().forEach(locationEntry -> {
            if (locationEntry != null && locationEntry.entrySet() != null) {

                removedEntries.clear();
                locationEntry.entrySet().forEach(entry -> {
                    if (entry != null) {
                        MutablePair<Double, Double> prices = entry.getValue();
                        if (prices.left == Double.MIN_VALUE || prices.right == Double.MAX_VALUE
                                || (prices.left * 1.2) >= prices.right
                                        && (locationEntry != null && locationEntry.containsKey(entry.getKey()))) {
                            removedEntries.add(entry.getKey());
                        }
                    }
                });

                locationEntry.keySet().removeAll(removedEntries);
            }
        });

        orders.entrySet().forEach(locationEntries -> {
            locationEntries.getValue().entrySet().forEach(entry -> {
                typeIdRepository.findLoadedTypeId(Long.valueOf(entry.getKey())).ifPresent(typeId -> {
                    logger.info("At location: {}, Item with id: {}, has buy price: {} and sell price: {}, %diff: {}",
                            entry.getKey(), typeId.getName().getEn(), entry.getValue().getLeft(),
                            entry.getValue().getRight(), entry.getValue().getRight() / entry.getValue().getLeft());
                });
            });
        });

    }

    public HashMap<Long, List<MarketOrdersResponse>> groupByLocation(List<MarketOrdersResponse> regionOrders) {
        return regionOrders.stream().reduce(new HashMap<Long, List<MarketOrdersResponse>>(), (map, order) -> {
            map.getOrDefault(order.getLocationId(), new ArrayList<>()).add(order);
            return map;
        }, (map1, map2) -> {
            map1.keySet().forEach(key -> map1.get(key).addAll(map2.get(key)));
            return map1;
        });
    }

    public HashMap<Integer, List<MarketOrdersResponse>> groupByItem(List<MarketOrdersResponse> itemOrders) {
        return itemOrders.stream().reduce(new HashMap<Integer, List<MarketOrdersResponse>>(), (map, order) -> {
            map.getOrDefault(order.getTypeId(), new ArrayList<>()).add(order);
            return map;
        }, (map1, map2) -> {
            map1.keySet().forEach(key -> map1.get(key).addAll(map2.get(key)));
            return map1;
        });
    }

    public HashMap<Long, HashMap<Integer, MutablePair<Double, Double>>> getBuyAndSellPrice(
            List<MarketOrdersResponse> orders) {
        // this returns a map that given a location id provides a map that given a item
        // id provides a pair with lowest sell price / highest buy price
        return orders.stream().reduce(new HashMap<Long, HashMap<Integer, MutablePair<Double, Double>>>(),
                (map, order) -> {
                    HashMap<Integer, MutablePair<Double, Double>> itemsAtLocationMap = map
                            .getOrDefault(order.getLocationId(), new HashMap<Integer, MutablePair<Double, Double>>());
                    MutablePair<Double, Double> prices = itemsAtLocationMap.getOrDefault(order.getTypeId(),
                            MutablePair.of(Double.MIN_VALUE, Double.MAX_VALUE));

                    // update the pair if the order is a buy order
                    if (Boolean.TRUE.equals(order.getIsBuyOrder()))
                        prices.setLeft(prices.getLeft() < order.getPrice() ? order.getPrice() : prices.getLeft());

                    // update the pair if the order is a sell order
                    if (Boolean.FALSE.equals(order.getIsBuyOrder()))
                        prices.setRight(prices.getRight() > order.getPrice() ? order.getPrice() : prices.getRight());

                    itemsAtLocationMap.put(order.getTypeId(), prices);
                    map.put(order.getLocationId(), itemsAtLocationMap);
                    return map;
                }, (map1, map2) -> {
                    // for each location key
                    map1.keySet().forEach(regionKey ->
                    // merge the location by
                    map1.merge(regionKey, map2.getOrDefault(regionKey, new HashMap<>()), (value1, value2) -> {
                        // for each item type
                        value1.keySet().forEach(itemKey -> {
                            // update the pair to the lowest sell price and highest buy price if they
                            // collide
                            map1.get(regionKey)
                                    .merge(itemKey,
                                            map2.getOrDefault(regionKey, new HashMap<>()).getOrDefault(itemKey,
                                                    MutablePair.of(Double.MIN_VALUE, Double.MAX_VALUE)),
                                            (pair1, pair2) -> {
                                                pair1.left = pair1.left > pair2.left ? pair2.left : pair1.left;
                                                pair1.right = pair1.right < pair2.right ? pair2.right : pair1.right;
                                                return pair1;
                                            });
                        });
                        return value1;
                    }));
                    return map1;
                });
    }

    public HashMap<Integer, Double> getBuyPrice(List<MarketOrdersResponse> orders) {
        return getPrice(orders, // the actual orders
                (MarketOrdersResponse::getIsBuyOrder), // only work with buy orders
                (x, y) -> x > y ? x : y, // return max values
                0.0); // start with the lowest possible price
    }

    private HashMap<Integer, Double> getPrice(List<MarketOrdersResponse> orders,
            Predicate<MarketOrdersResponse> predicate, DoubleBinaryOperator binaryOperator, Double defaultValue) {
        return orders.stream().filter(predicate).reduce(new HashMap<Integer, Double>(), (map, order) -> {
            Double maxBuy = map.getOrDefault(order.getTypeId(), defaultValue);
            map.put(order.getTypeId(), binaryOperator.applyAsDouble(maxBuy, order.getPrice()));
            return map;
        }, (map1, map2) -> {
            map1.keySet().forEach(key -> {
                Double map1Value = map1.getOrDefault(key, defaultValue);
                Double map2Value = map2.getOrDefault(key, defaultValue);
                map1.put(key, binaryOperator.applyAsDouble(map1Value, map2Value));
            });
            return map1;
        });
    }

    @Override
    public void setup() {
        // TODO Auto-generated method stub
        logger.error("Setup");

    }

    @Override
    public void breakdown() {
        // TODO Auto-generated method stub
        logger.error("Breakdown");
    }

}
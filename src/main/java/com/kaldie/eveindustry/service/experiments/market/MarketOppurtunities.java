package com.kaldie.eveindustry.service.experiments.market;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.kaldie.eveindustry.client.esi.MarketOrders;
import com.kaldie.eveindustry.repository.type_id.TypeIDRepository;
import com.kaldie.eveindustry.repository.type_id.TypeId;
import com.kaldie.eveindustry.repository.universe.Region;
import com.kaldie.eveindustry.repository.universe.RegionRepository;
import com.kaldie.eveindustry.service.experiments.Task;

import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.model.MarketOrdersResponse;

@Component
@com.kaldie.eveindustry.eve_indi_annotations.Experiment
@RequiredArgsConstructor
public class MarketOppurtunities extends Task {

    private static Logger logger = LoggerFactory.getLogger(MarketOppurtunities.class);
    
    private final RegionRepository regionRepository;   
    private final TypeIDRepository typeIdRepository;

    @Override
    public void run() {

        List<Region> regions = Arrays.asList(
            // regionRepository.getRegionByName("TheForge"), 
            regionRepository.getRegionByName("Domain"));

        List<MarketOrdersResponse> orders = new ArrayList<MarketOrdersResponse>(){
            private static final long serialVersionUID = -8113636192680648062L;
        };

        regions.forEach(region -> {
            
            logger.info("{}",regions);
            try {
                orders.addAll(MarketOrders.getRegionalItemOrders(region.getRegionID()));
            } catch (ApiException e) {
                logger.warn("Could not get all orders from {}!", region.getName());
                logger.error(e.toString());
            }
        });

        HashMap<Integer, Double> buyprices = getBuyPrice(orders.stream().filter(order -> order.getLocationId() == 60008494).collect(Collectors.toList()));
        HashMap<Integer, Double> sellprices = getSellPrice(orders.stream().filter(order -> order.getLocationId() == 60008494).collect(Collectors.toList()));
        logger.info("Trit: buy {} sell: {}",buyprices.get(34), sellprices.get(34));
        logger.info("pye: buy {} sell: {}",buyprices.get(35), sellprices.get(35));
        // HashMap<Integer, Double> sell = getSellPrice(orders);

        HashMap<Long,HashMap<Integer,MutablePair<Double,Double>>> buyAndSellPrices = getBuyAndSellPrice(orders);
        MutablePair<Double,Double> tritAtAmarr = buyAndSellPrices.get(60008494L).get(34);
        logger.info("Trit: buy {} sell: {}",tritAtAmarr.left, tritAtAmarr.right);

        // filterOrdersOnDifferential(buyAndSellPrices);

    }

    public void filterOrdersOnDifferential(HashMap<Long,HashMap<Integer,MutablePair<Double,Double>>> orders) {
               
        HashMap<Long,HashMap<Integer,MutablePair<Double,Double>>> filteredOrders = (HashMap<Long,HashMap<Integer,MutablePair<Double,Double>>>)
            orders.entrySet().stream()
            // .filter(entry -> entry.getValue().size() > 10000)
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        filteredOrders.values().forEach(locationEntry -> {
            if (locationEntry != null) {
                logger.info("{}", locationEntry);
                if (locationEntry.entrySet() != null) {
                    locationEntry.entrySet().forEach(entry -> {
                        if (entry != null) {
                            MutablePair<Double,Double> prices = entry.getValue();
                            if (prices.getLeft() *1.1 <= prices.getRight()) {
                                if (!(locationEntry != null) && locationEntry.containsKey(entry.getKey()))
                                    locationEntry.remove(entry.getKey()); 
                            }
                        }
                    });
                    logger.info("{}", locationEntry);
                }
            }
        });

        filteredOrders.entrySet().forEach(locationEntries -> {
            locationEntries.getValue().entrySet().forEach(entry -> {
                logger.info("at location: {}, Item with id: {}, has buy price: {} and sell price: {}, %diff: {}",
                locationEntries.getKey(), 
                typeIdRepository.findLoadedTypeId(Long.valueOf(entry.getKey())).orElse(new TypeId()).getName().getEn(), 
                entry.getValue().getLeft(), entry.getValue().getRight(), (entry.getValue().getRight() / entry.getValue().getLeft()));
            });
        });


            
        // return orders.entrySet().stream()
        // .filter(entry -> entry.getValue().size() > 100) // filter locations which do not have enough items to go to
        // .collect(Collectors.toMap(
        //     HashMap.Entry::getKey,
        //     (locationEntry) -> {
        //         HashMap<Integer,MutablePair<Double,Double>> itemEntries = locationEntry.getValue();
        //         itemEntries.entrySet().forEach(itemEntry -> {
        //             MutablePair<Double,Double> buyAndSellPrices = itemEntry.getValue();
        //             if (buyAndSellPrices.getLeft() *1.15 < buyAndSellPrices.getRight()) {
        //                 locationEntry.remove(itemEntry.getKey());
        //             }
        //             return locationEntry.values();
        //         });
        //         return itemEntry;
        //     }));
    }

    public HashMap<Long, List<MarketOrdersResponse>> groupByLocation(List<MarketOrdersResponse> regionOrders) {
        return regionOrders.stream().reduce(new HashMap<Long, List<MarketOrdersResponse>>(),
        (map, order) -> {
            map.getOrDefault(order.getLocationId(), new ArrayList<>()).add(order);
            return map;
        },
        (map1, map2) -> {
            map1.keySet().forEach(key -> map1.get(key).addAll(map2.get(key)));
            return map1;
        });
    }

    public HashMap<Integer, List<MarketOrdersResponse>> groupByItem(List<MarketOrdersResponse> itemOrders) {
        return itemOrders.stream().reduce(new HashMap<Integer, List<MarketOrdersResponse>>(),
        (map, order) -> {
            map.getOrDefault(order.getTypeId(), new ArrayList<>() ).add(order);
            return map;
        },
        (map1, map2) -> {
            map1.keySet().forEach(key -> map1.get(key).addAll(map2.get(key)));
            return map1;
        });
    }

    public HashMap<Long,HashMap<Integer,MutablePair<Double,Double>>> getBuyAndSellPrice(List<MarketOrdersResponse> orders) {
        // this returns a map that given a location id provides a map that given a item id provides a pair with lowest sell price / highest buy price
        return orders.stream().reduce(new HashMap<Long,HashMap<Integer,MutablePair<Double,Double>>>(),
            (map, order) -> {
                HashMap<Integer,MutablePair<Double,Double>> itemsAtLocationMap = map.getOrDefault(order.getLocationId(), new HashMap<Integer,MutablePair<Double,Double>>());
                MutablePair<Double,Double> prices = itemsAtLocationMap.getOrDefault(order.getTypeId(), MutablePair.of(Double.MIN_VALUE, Double.MAX_VALUE));
                
                // update the pair if the order is a buy order
                if (Boolean.TRUE.equals(order.getIsBuyOrder())) prices.setLeft(prices.getLeft() < order.getPrice() ? order.getPrice():prices.getLeft() );
                
                // update the pair if the order is a sell order
                if (Boolean.FALSE.equals(order.getIsBuyOrder())) prices.setRight(prices.getRight() > order.getPrice() ? order.getPrice():prices.getRight() );

                itemsAtLocationMap.put(order.getTypeId(), prices);
                map.put(order.getLocationId(), itemsAtLocationMap);
                return map;
            },
            (map1, map2) -> {
                // for each location key
                map1.keySet().forEach(regionKey ->
                    // merge the location by
                    map1.merge(regionKey, map2.get(regionKey), (value1, value2) -> {
                        // for each item type
                        value1.keySet().forEach(itemKey -> {
                            // update the pair to the lowest sell price and highest buy price if they collide
                            map1.get(regionKey).merge(itemKey, map2.get(regionKey).get(itemKey), (pair1, pair2) -> {
                                pair1.left = pair1.left > pair2.left ? pair2.left : pair1.left;
                                pair1.right = pair1.right < pair2.right ? pair2.right : pair1.right;
                                return pair1;
                            });
                        });
                        return value1;
                    })
                );
            return map1;
        });
       

    }

    public HashMap<Integer, Double> getSellPrice(List<MarketOrdersResponse> orders) {
  
        return getPrice(
            orders, // the actual orders
            ((Predicate<MarketOrdersResponse>)MarketOrdersResponse::getIsBuyOrder).negate(), // only work with sell orders
            (x, y) -> x < y ? x : y, // return min values
            Double.MAX_VALUE ); // start with the higest possible price
    }

    public HashMap<Integer, Double> getBuyPrice(List<MarketOrdersResponse> orders) {
        return getPrice(
            orders, // the actual orders
            (MarketOrdersResponse::getIsBuyOrder), // only work with buy orders
            (x, y) -> x > y ? x : y, // return max values
            0.0 ); // start with the lowest possible price
    }


    private HashMap<Integer, Double> getPrice(
        List<MarketOrdersResponse> orders, 
        Predicate<MarketOrdersResponse> predicate, 
        DoubleBinaryOperator binaryOperator, Double defaultValue) {
            return orders.stream()
            .filter(predicate)       
            .reduce(new HashMap<Integer, Double>(),
                (map, order) -> {
                    Double maxBuy = map.getOrDefault(order.getTypeId(), defaultValue);
                    map.put(order.getTypeId(), binaryOperator.applyAsDouble(maxBuy, order.getPrice()));
                    return map;
                },
                (map1, map2) -> {
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
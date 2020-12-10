package com.kaldie.eveindustry.repository.market;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import net.troja.eve.esi.model.MarketOrdersResponse;

@Repository
public class CustomMarketOrderRepositoryImpl implements CustomMarketOrderRepository {

    Logger logger = LoggerFactory.getLogger(CustomMarketOrderRepositoryImpl.class);

    private final EntityManager entityManager;

    private List<MarketOrder> getAllMarketOrders( Set<Integer> systems) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MarketOrder> criteriaQuery = criteriaBuilder.createQuery(MarketOrder.class);
        Root<MarketOrder> rootEntry = criteriaQuery.from(MarketOrder.class);

        criteriaQuery = criteriaQuery.select(rootEntry);
        In<Object> inCriteria = criteriaBuilder.in(rootEntry.get("systemId"));
        systems.forEach(inCriteria::value);
        criteriaQuery = criteriaQuery.where(inCriteria);

        return  entityManager.createQuery(criteriaQuery).getResultList();
    }

    private void updateMarketOrderFromResponse(MarketOrder order, MarketOrdersResponse response) {
        if (!order.almostEqualToResponse(response)) {
            logger.debug("Reponse is not equal!");
            order.setDuration(response.getDuration());
            order.setIsBuy(response.getIsBuyOrder());
            order.setIssued(Adaptor.from(response.getIssued()));
            order.setLocation(response.getLocationId());
            order.setMinVolume(response.getMinVolume());
            order.setPrice(response.getPrice());
            order.setRange(response.getRange().toString());
            order.setVolumeRemain(response.getVolumeRemain());
        }
    }

    private void insertMarketOrderFromResponse(Collection<MarketOrdersResponse> marketOrdersResponses) {
        marketOrdersResponses.forEach(marketOrdersResponse -> 
            entityManager.persist(Adaptor.from(marketOrdersResponse)));
    }

    private void deleteOrderFromResponses(MarketOrder order) {
        entityManager.remove(order);
    }

    public CustomMarketOrderRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void updateFromMarketOrderResponses(List<MarketOrdersResponse> responses) {

        Set<Integer> systems = new HashSet<>();
        Map<Long, MarketOrdersResponse> marketOrderResponseMap = new HashMap<>();
        responses.forEach(reponse -> {
            systems.add(reponse.getSystemId());
            marketOrderResponseMap.put(reponse.getOrderId(), reponse);
        });
        
        List<MarketOrder> orders = getAllMarketOrders(systems);

        orders.forEach(order -> {
            Long orderId = order.getOrderId();
            if (marketOrderResponseMap.containsKey(orderId)) {
                updateMarketOrderFromResponse(order,marketOrderResponseMap.get(orderId));
                marketOrderResponseMap.remove(orderId);
            } else {
                deleteOrderFromResponses(order);
            }
        });

        insertMarketOrderFromResponse(marketOrderResponseMap.values());
    }



    
}
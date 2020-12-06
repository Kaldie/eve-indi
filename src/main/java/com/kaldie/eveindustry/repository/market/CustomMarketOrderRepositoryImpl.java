package com.kaldie.eveindustry.repository.market;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.kaldie.eveindustry.repository.type_id.TypeId;
import com.kaldie.eveindustry.repository.universe.SolarSystem;

import org.springframework.stereotype.Repository;

import net.troja.eve.esi.model.MarketOrdersResponse;

@Repository
public class CustomMarketOrderRepositoryImpl implements CustomMarketOrderRepository {

    private final EntityManager entityManager;

    private List<MarketOrder> getAllMarketOrders() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MarketOrder> criteriaQuery = criteriaBuilder.createQuery(MarketOrder.class);
        Root<MarketOrder> rootEntry = criteriaQuery.from(MarketOrder.class);
        CriteriaQuery<MarketOrder> all = criteriaQuery.select(rootEntry);
        TypedQuery<MarketOrder> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    private void updateMarketOrderFromResponse(MarketOrder order, MarketOrdersResponse response) {
        
        order.setDuration(response.getDuration());
        order.setIsBuy(response.getIsBuyOrder());
        order.setIssued(Adaptor.from(response.getIssued()));
        order.setLocation(response.getLocationId());
        order.setMinVolume(response.getMinVolume());
        order.setPrice(response.getPrice());
        order.setRange(response.getRange().toString());
        order.setVolumeRemain(response.getVolumeRemain());
        // order.setSystemId(new SolarSystem(response.getSystemId()));
        // order.setTypeId(new TypeId(response.getTypeId()));
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


        Map<Long, MarketOrdersResponse> marketOrderResponseMap = new HashMap<>();
        responses.forEach(reponse -> 
            marketOrderResponseMap.put(reponse.getOrderId(), reponse)
        );
        
        List<MarketOrder> orders = getAllMarketOrders();

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

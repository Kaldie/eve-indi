package com.kaldie.eveindustry.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository
public class BulkInsertUpdateRepositoryImpl<T> implements BulkInsertUpdateRepository<T> {
    private final EntityManager manager;
    private final EntityManagerFactory factory;
    private Logger logger = LoggerFactory.getLogger(BulkInsertUpdateRepositoryImpl.class);

    private Stream<T> getAll(Class<T> classInstance) {
        logger.debug("Retrieving all items");
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(classInstance);
        Root<T> rootEntry = criteriaQuery.from(classInstance);
        criteriaQuery = criteriaQuery.select(rootEntry);
        return  manager.createQuery(criteriaQuery).getResultStream();
    }

    @Override
    @Transactional
	public void updateInsertDeleteFromBulk(List<T> items, Class<T> classInstance) {
        logger.debug("Updating, inserting and deleting from bulk");
        Map<Object, T> newItemsMap = new HashMap<>();
        PersistenceUnitUtil persistenceUtil = factory.getPersistenceUnitUtil();
       
        items.forEach(item -> 
            newItemsMap.put( persistenceUtil.getIdentifier(item), item));

        getAll(classInstance).forEach(item -> {
            Object id = persistenceUtil.getIdentifier(item);
            if (newItemsMap.containsKey(id)) {
                BeanUtils.copyProperties(newItemsMap.get(id), item);
                newItemsMap.remove(id);
            } else {
                manager.remove(item);
            }
        });

        newItemsMap.values().forEach(manager::persist);
    }
}

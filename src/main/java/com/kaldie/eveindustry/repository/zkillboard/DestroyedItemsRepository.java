package com.kaldie.eveindustry.repository.zkillboard;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface DestroyedItemsRepository extends CrudRepository<DestroyedContent, Long> {}

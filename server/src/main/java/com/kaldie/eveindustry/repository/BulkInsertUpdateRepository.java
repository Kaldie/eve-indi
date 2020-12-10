package com.kaldie.eveindustry.repository;

import java.util.List;

public interface BulkInsertUpdateRepository<T> {
    public void updateInsertDeleteFromBulk(List<T> items, Class<T> classInstance);
}

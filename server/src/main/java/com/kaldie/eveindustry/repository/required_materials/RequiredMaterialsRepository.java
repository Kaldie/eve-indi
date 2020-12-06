package com.kaldie.eveindustry.repository.required_materials;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequiredMaterialsRepository extends CrudRepository<RequiredMaterialsEntity, Long> {
    
    @Cacheable("getRequiredMaterials")
    @Query(nativeQuery = true, name = "getRequiredMaterials")
    public List<RequiredMaterialsEntity> getRequiredMaterials(@Param("id") long id);

}

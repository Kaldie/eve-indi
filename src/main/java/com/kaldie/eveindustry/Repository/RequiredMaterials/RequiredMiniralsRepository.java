package com.kaldie.eveindustry.Repository.RequiredMaterials;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequiredMiniralsRepository extends CrudRepository<RequiredMiniralMaterialsEntity, Long> {
    
    @Cacheable("getRequiredMinirals")
    @Query(nativeQuery = true, name = "getRequiredMinirals")
    public List<RequiredMiniralMaterialsEntity> getRequireMinirals(@Param("id") long id);

}

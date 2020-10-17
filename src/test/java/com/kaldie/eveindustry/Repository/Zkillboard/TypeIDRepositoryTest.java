package com.kaldie.eveindustry.Repository.Zkillboard;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import com.kaldie.eveindustry.Repository.RequiredMaterials.RequiredMaterialsEntity;
import com.kaldie.eveindustry.Repository.RequiredMaterials.RequiredMaterialsRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class TypeIDRepositoryTest {

    @Autowired
    private RequiredMaterialsRepository requiredMaterialsRepository;

    @Test
    void retrieveRequiredMaterials() {
        List<RequiredMaterialsEntity> materials = requiredMaterialsRepository.getRequiredMaterials(11202L);
        assertNotEquals(0, materials.size());

    }
}

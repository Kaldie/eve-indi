package com.kaldie.eveindustry.repository;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import com.kaldie.eveindustry.EveIndustryApplicationTestConfiguration;
import com.kaldie.eveindustry.repository.required_materials.RequiredMaterialsEntity;
import com.kaldie.eveindustry.repository.required_materials.RequiredMaterialsRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;


@AutoConfigureTestDatabase(replace=Replace.NONE) // using a predefined test database
@ContextConfiguration(classes = {EveIndustryApplicationTestConfiguration.class})
@DataJpaTest
class TypeIDRepositoryTest {

    @Autowired
    private RequiredMaterialsRepository requiredMaterialsRepository;
    
    @BeforeAll
    void loadRequiredData() {
    }

    @Test
    void retrieveRequiredMaterials() {
        List<RequiredMaterialsEntity> materials = requiredMaterialsRepository.getRequiredMaterials(11202L);
        assertNotEquals(0, materials.size());

    }
    
}

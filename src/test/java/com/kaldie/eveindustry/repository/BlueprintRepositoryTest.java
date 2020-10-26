package com.kaldie.eveindustry.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.kaldie.eveindustry.EveIndustryApplicationTestConfiguration;
import com.kaldie.eveindustry.repository.blueprint.Blueprint;
import com.kaldie.eveindustry.repository.blueprint.BlueprintRepository;
import com.kaldie.eveindustry.repository.blueprint.Material;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;

@AutoConfigureTestDatabase(replace = Replace.NONE) // using a predefined test database
@ContextConfiguration(classes = { EveIndustryApplicationTestConfiguration.class })
@DataJpaTest
public class BlueprintRepositoryTest {

    @Autowired
    BlueprintRepository blueprintRepository;

    @BeforeAll
    static void loadTestData(@Autowired BlueprintRepository lalal) throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<Long, Blueprint> blueprint = mapper.readValue(
            new ClassPathResource("blueprint_example.yml").getFile(),
            new TypeReference<Map<Long, Blueprint>>() { });
        
        // Add the id to the entry
        for (Map.Entry<Long, Blueprint> entry : blueprint.entrySet()) {
            entry.getValue().setBlueprintTypeID(entry.getKey());
        }

        assertNotNull(lalal);
        assertNotNull(blueprint);
        assertNotNull(blueprint.values());

        lalal.saveAll(blueprint.values());
    }

    @Test
    void testBlueprintRetrieval() {
        assertNotNull(blueprintRepository);
        ArrayList<Blueprint> blueprints = new ArrayList<Blueprint>();
        blueprintRepository.findAll().forEach(blueprints::add);
        
        assertNotNull(blueprints);
        assertEquals(1, blueprints.size());
        Blueprint blueprint = blueprints.get(0);
        assertEquals(682,blueprint.getBlueprintTypeID());

        assertNotNull(blueprint.getActivities());
        assertNotNull(blueprint.getActivities().getManufacturing());
        assertNotNull(blueprint.getActivities().getManufacturing().getProducts());
        assertEquals(1,blueprint.getActivities().getManufacturing().getProducts().size());
        assertEquals(166,blueprint.getActivities().getManufacturing().getProducts().get(0).getTypeID().getId());
        assertEquals(1,blueprint.getActivities().getManufacturing().getProducts().get(0).getQuantity());

        List<Material> materials = blueprint.getActivities().getManufacturing().getRequiredMaterials();
        assertEquals(2, materials.size());
        assertThat(materials.get(0).getTypeID().getId(), anyOf(is(34L), is(38L)));
        assertThat(materials.get(0).getQuantity(), anyOf(is(123), is(133)));

        assertThat(materials.get(1).getTypeID().getId(), anyOf(is(34L), is(38L)));
        assertThat(materials.get(0).getQuantity(), anyOf(is(123), is(133)));
    }
    
}

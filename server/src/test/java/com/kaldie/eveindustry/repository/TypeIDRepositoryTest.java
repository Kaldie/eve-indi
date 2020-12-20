package com.kaldie.eveindustry.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.kaldie.eveindustry.EveIndustryApplicationTestConfiguration;
import com.kaldie.eveindustry.repository.type_id.TypeIDRepository;
import com.kaldie.eveindustry.repository.type_id.TypeId;

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
class TypeIDRepositoryTest {

    @Autowired
    private TypeIDRepository typeRepository;

    @BeforeAll
    static void loadTestData(@Autowired TypeIDRepository lalal) throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<Long, TypeId> typeMap = mapper.readValue(
            new ClassPathResource("typeid_example.yml").getFile(),
            new TypeReference<Map<Long, TypeId>>() { });
        
        // Add the id to the entry
        for (Map.Entry<Long, TypeId> entry : typeMap.entrySet()) {
            entry.getValue().setId(entry.getKey());
        }

        assertNotNull(lalal);
        assertNotNull(typeMap);
        assertNotNull(typeMap.values());

        lalal.saveAll(typeMap.values());
    }

    @Test
    void testCreationAndRetrievalOfNewTypeId() {
        assertNotNull(typeRepository.findById(34L));
    }

    @Test
    void testRetrieveByName() {
        assertNotNull(typeRepository.findByEnglishName("Tritanium"));
    }
    
}

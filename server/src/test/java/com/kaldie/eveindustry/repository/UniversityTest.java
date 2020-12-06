package com.kaldie.eveindustry.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.kaldie.eveindustry.EveIndustryApplicationTestConfiguration;
import com.kaldie.eveindustry.repository.universe.Moon;
import com.kaldie.eveindustry.repository.universe.NPCStation;
import com.kaldie.eveindustry.repository.universe.Planet;
import com.kaldie.eveindustry.repository.universe.RegionRepository;
import com.kaldie.eveindustry.repository.universe.SolarSystem;
import com.kaldie.eveindustry.repository.universe.SolarSystemRepository;

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
public class UniversityTest {

    @Autowired
    SolarSystemRepository solarSystemRepository;

    @Test
    public void deserialisationSolarSystemTest() throws IOException {

        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        SolarSystem solarSystem = new SolarSystem();

        assertDoesNotThrow(() -> {
            mapper.readValue(new ClassPathResource("solarsystem_example.staticdata").getFile(), SolarSystem.class);
        });

        solarSystem = mapper.readValue(new ClassPathResource("solarsystem_example.staticdata").getFile(),
                SolarSystem.class);

        assertNotNull(solarSystem.getPlanets());
        assertEquals(30001615L, solarSystem.getSolarSystemID());
        assertEquals(false, solarSystem.getBorder());
        assertEquals(false, solarSystem.getHub());

        // Verify planets
        assertEquals(9, solarSystem.getPlanets().size());
        solarSystem.getPlanets().forEach(planet -> {
            assertThat(planet.getId(), anyOf(is(1111L), is(40103000L), is(40103003L), is(40103005L), is(40103008L),
                    is(40103015L), is(40103018L), is(40103021L), is(40103041L)));
        });

        assertNotNull(solarSystem.getPlanet(40103021L));
      
        // Planet planet = solarSystem.getPlanet(40103021L);
        // assertNotNull(planet.getMoons());
        // assertEquals(17, planet.getMoons().size());
      
        // Moon moon = planet.getMoon(40103033L);
        // assertNotNull(moon);
        // assertNotNull(moon.getNpcStations());
        // assertEquals(1, moon.getNpcStations().size());
      
        // NPCStation station = moon.getNpcStations().get(0);
        // assertNotNull(station);
        // assertEquals(60013747l, station.getId());
        // assertEquals(false, station.getIsConquerable());
   
    }  

    @Test
    public void persistanceTest() throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        SolarSystem solarSystem = new SolarSystem();

        assertDoesNotThrow(() -> {
            mapper.readValue(new ClassPathResource("solarsystem_example.staticdata").getFile(), SolarSystem.class);
        });

        solarSystem = mapper.readValue(new ClassPathResource("solarsystem_example.staticdata").getFile(),
                SolarSystem.class);

        // ArrayList<Planet> newPlanets = new ArrayList<Planet>(){};
        // Planet planet = solarSystem.getPlanets().get(1);
        // newPlanets.add(solarSystem.getPlanets().get(1));
        
        // solarSystem.setPlanets(newPlanets);
        
        solarSystemRepository.save(solarSystem);
    }
}

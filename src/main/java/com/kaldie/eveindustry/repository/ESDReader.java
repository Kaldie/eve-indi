package com.kaldie.eveindustry.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.kaldie.eveindustry.repository.blueprint.Blueprint;
import com.kaldie.eveindustry.repository.blueprint.BlueprintRepository;
import com.kaldie.eveindustry.repository.type_id.TypeIDRepository;
import com.kaldie.eveindustry.repository.type_id.TypeId;
import com.kaldie.eveindustry.repository.type_id.type_materials.TypeMaterial;
import com.kaldie.eveindustry.repository.universe.Planet;
import com.kaldie.eveindustry.repository.universe.Region;
import com.kaldie.eveindustry.repository.universe.RegionRepository;
import com.kaldie.eveindustry.repository.universe.RegionVisitor;
import com.kaldie.eveindustry.repository.universe.SolarSystem;
import com.kaldie.eveindustry.repository.universe.SolarSystemRepository;
import com.kaldie.eveindustry.repository.universe.UniqueName;
import com.kaldie.eveindustry.repository.universe.UniqueNamesRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Data
public class ESDReader {
    
    private Collection<TypeId> types;
    private Collection<Blueprint> blueprints;
    private Collection<TypeMaterial> typeMaterials;
    private List<Region> regions;
    private List<SolarSystem> solarSystems;
    private List<UniqueName> names;

    private Logger logger = LoggerFactory.getLogger(ESDReader.class);
    
    private final BlueprintRepository blueprintRepository;
    
    private final TypeIDRepository typeRepository;

    private final RegionRepository regionRepository;

    private final SolarSystemRepository solarSystemRepository;

    private final UniqueNamesRepository uniqueNamesRepository;

    public void loadEsd() {
        try {
            loadTypeIds();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.loadBlueprints();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            loadTypeMaterials();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            loadRegions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void storeBlueprints() throws IOException {
        if (blueprints == null) {
            logger.info("loading blueprint from yml files.");
            this.loadBlueprints();
        }
        logger.info("Save Blueprints to db.");
        for (Blueprint blueprint : blueprints) {
            try {
                blueprintRepository.save(blueprint);
            } catch (DataIntegrityViolationException e) {
                logger.error("Could not insert blue print!");
                logger.info("Blueprint id: {}", blueprint.getBlueprintTypeID());
                logger.info("Produced: {}", blueprint.getProducedID());
                logger.info("Required for manufacturing: {}", blueprint.getActivities().getManufacturing().getRequiredMaterials().toArray());
            }
        }
    }

    private void storeTypeIds() throws IOException {
        if (types == null) {
            logger.debug("loading types from yml files.");
            this.loadTypeIds();
        }
        logger.debug("Saving types in db.");
        typeRepository.saveAll(types);
    }

    private void storeRegions() throws IOException {
        if (regions == null || solarSystems == null) {
            logger.debug("loading regions from yml files.");
            this.loadRegions();
        }
        logger.debug("Save regions to db.");

        for (Region region : regions) {
            try {
                regionRepository.save(region);
            } catch (DataIntegrityViolationException e) {
                logger.error("Could not insert region with id: {}!", region.getRegionID());
            }
        }

        for (SolarSystem system : solarSystems) {
            try {
                solarSystemRepository.save(system);
            } catch (DataIntegrityViolationException e) {
                logger.error("Could not insert system with id: {}!", system.getSolarSystemID());
                logger.error("Planets with the following ids: {}", system.getPlanets().stream().map(Planet::getId).map(id -> Long.toString(id)).toArray());
            }
        }
    }

    private void storeNames() throws IOException {
        if (names == null) {
            logger.debug("loading names");
            this.loadNames();
        }

        logger.debug("Save names to db.");
        uniqueNamesRepository.saveAll(names);
    }

    public void storeEsd() throws IOException {
        logger.info("Storing Names");
        storeNames();
        logger.info("Storing Type IDs");
        storeTypeIds();
        logger.info("Storing Regions");
        storeRegions();
        logger.info("Storing Blueprints");
        storeBlueprints();
    }

    private void loadTypeIds() throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<Long, TypeId> typeMap = mapper.readValue(new File("resources/sde/fsd/typeIDs.yaml"),
            new TypeReference<Map<Long, TypeId>>() { });
        
        // Add the id to the entry
        for (Map.Entry<Long, TypeId> entry : typeMap.entrySet()) {
            entry.getValue().setId(entry.getKey());
        }
        types = typeMap.values();
    }

    private void loadBlueprints() throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<Long, Blueprint> blueprintMap = mapper.readValue(new File("resources/sde/fsd/blueprints.yaml"),
            new TypeReference<Map<Long, Blueprint>>() { });

        for (Map.Entry<Long, Blueprint> entry : blueprintMap.entrySet()) {
            entry.getValue().setBlueprintTypeID(entry.getKey());
        }
        blueprints = blueprintMap.values();
    }

    private void loadTypeMaterials() throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<Long, TypeMaterial> typeMaterialMap = mapper.readValue(new File("resources/sde/fsd/typeMaterials.yaml"),
            new TypeReference<Map<Long, TypeMaterial>>() { });
        typeMaterials = typeMaterialMap.values();
    }

    private void loadRegions() throws IOException {
        RegionVisitor regionVisitor = new RegionVisitor();
        Files.walkFileTree(Paths.get("resources/sde/fsd/universe"), regionVisitor);
        regions = regionVisitor.getRegions();
        solarSystems = regionVisitor.getSolarSystems();
    }

    private void loadNames() throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        names = mapper.readValue(new File("resources/sde/bsd/invNames.yaml"),
            new TypeReference<List<UniqueName>>() { });
    }
    
}

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
import com.kaldie.eveindustry.repository.bluePrint.Blueprint;
import com.kaldie.eveindustry.repository.bluePrint.BlueprintRepository;
import com.kaldie.eveindustry.repository.type_id.TypeIDRepository;
import com.kaldie.eveindustry.repository.type_id.TypeId;
import com.kaldie.eveindustry.repository.type_id.type_materials.TypeMaterial;
import com.kaldie.eveindustry.repository.universe.Region;
import com.kaldie.eveindustry.repository.universe.RegionRepository;
import com.kaldie.eveindustry.repository.universe.RegionVisitor;

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

    private Logger logger = LoggerFactory.getLogger(ESDReader.class);
    
    private final BlueprintRepository blueprintRepository;
    
    private final TypeIDRepository typeRepository;

    private final RegionRepository regionRepository;

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
            logger.info("loading types from yml files.");
            this.loadTypeIds();
        }
        logger.info("Saving types in db.");
        typeRepository.saveAll(types);
    }

    private void storeRegions() throws IOException {
        if (regions == null) {
            logger.info("loading regions from yml files.");
            this.loadRegions();
        }
        logger.info("Save regions to db.");
        regionRepository.saveAll(regions);
    }

    public void storeEsd() throws IOException {
        logger.info("Storing Type IDs");
        storeTypeIds();
        logger.info("Storing Blueprints");
        storeBlueprints();
        logger.info("Storing Regions");
        storeRegions();
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
    }
    
}

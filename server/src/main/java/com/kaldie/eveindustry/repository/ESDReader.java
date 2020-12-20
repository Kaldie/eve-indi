package com.kaldie.eveindustry.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.kaldie.eveindustry.repository.blueprint.Blueprint;
import com.kaldie.eveindustry.repository.blueprint.BlueprintRepository;
import com.kaldie.eveindustry.repository.type_id.TypeIDRepository;
import com.kaldie.eveindustry.repository.type_id.TypeId;
import com.kaldie.eveindustry.repository.type_id.type_materials.TypeMaterial;
import com.kaldie.eveindustry.repository.universe.Moon;
import com.kaldie.eveindustry.repository.universe.MoonRepository;
import com.kaldie.eveindustry.repository.universe.NPCStation;
import com.kaldie.eveindustry.repository.universe.NPCStationRepository;
import com.kaldie.eveindustry.repository.universe.Planet;
import com.kaldie.eveindustry.repository.universe.PlanetRepository;
import com.kaldie.eveindustry.repository.universe.Region;
import com.kaldie.eveindustry.repository.universe.RegionRepository;
import com.kaldie.eveindustry.repository.universe.RegionVisitor;
import com.kaldie.eveindustry.repository.universe.SimpleStargate;
import com.kaldie.eveindustry.repository.universe.SolarSystem;
import com.kaldie.eveindustry.repository.universe.SolarSystemRepository;
import com.kaldie.eveindustry.repository.universe.Stargate;
import com.kaldie.eveindustry.repository.universe.SimpleStargateRepository;
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
    
    private List<TypeId> types;
    private Collection<Blueprint> blueprints;
    private Collection<TypeMaterial> typeMaterials;
    private List<Region> regions;
    private List<SolarSystem> solarSystems;
    private List<UniqueName> names;

    private Logger logger = LoggerFactory.getLogger(ESDReader.class);
    
    private final BlueprintRepository blueprintRepository;
    
    private final TypeIDRepository typeRepository;

    private final RegionRepository regionRepository;

    
    private final UniqueNamesRepository uniqueNamesRepository;
    
    private final RegionVisitor regionVisitor;
    
    private final SolarSystemRepository solarSystemRepository;
    private final PlanetRepository planetRepository;
    private final MoonRepository moonRepository;
    private final NPCStationRepository npcStationRepository;
    private final SimpleStargateRepository simpleStargateRepository;


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
        typeRepository.updateInsertDeleteFromBulk(types, TypeId.class);
    }

    private void storeRegions() throws IOException {
        if (regionVisitor.getSolarSystems().isEmpty() || regionVisitor.getRegions().isEmpty() ) {
            logger.info("loading regions from yml files.");
            this.loadRegions();
        }
        logger.info("Save regions to db.");

        regionRepository.updateInsertDeleteFromBulk(regionVisitor.getRegions(), Region.class);

        logger.info("Save solarSystem to db.");
        solarSystemRepository.updateInsertDeleteFromBulk(regionVisitor.getSolarSystems(), SolarSystem.class);
        logger.info("Save stargate to db.");

        Map<Long, Stargate> gates = new HashMap<Long, Stargate>();
        regionVisitor.getStargates().forEach(gate -> gates.put(gate.getId(), gate));
        gates.values().forEach(gate -> {
            if (!gates.containsKey(gate.getDestination().getId())) {
                logger.error("Fuck: {} {} ",gate.getId(), gate.getDestination().getId() );
            }
        });

        logger.info("Save stargates to db.");
        simpleStargateRepository.updateInsertDeleteFromBulk(
            regionVisitor.getStargates().stream().map(SimpleStargate::from).collect(Collectors.toList()), 
            SimpleStargate.class);
        logger.info("Save planet to db.");
        planetRepository.updateInsertDeleteFromBulk(regionVisitor.getPlanets(), Planet.class);
        logger.info("Save moon to db.");
        moonRepository.updateInsertDeleteFromBulk(regionVisitor.getMoons(), Moon.class);
        logger.info("Save npcStation to db.");
        npcStationRepository.updateInsertDeleteFromBulk(regionVisitor.getNpcStations(), NPCStation.class);
    }

    private void storeNames() throws IOException {
        if (names == null) {
            logger.debug("loading names");
            this.loadNames();
        }

        logger.debug("Save names to db.");
        uniqueNamesRepository.updateInsertDeleteFromBulk(names, UniqueName.class);
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
        types = new ArrayList<>(typeMap.values());
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
        logger.info("Reset");
        regionVisitor.reset();

        logger.info("walk");
        Files.walkFileTree(Paths.get("resources/sde/fsd/universe/eve"), regionVisitor);
        logger.info("unpack");
        regionVisitor.unpack();
        logger.info("done");
    }

    private void loadNames() throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        names = mapper.readValue(new File("resources/sde/bsd/invNames.yaml"),
            new TypeReference<List<UniqueName>>() { });
    }
    
}

package com.kaldie.eveindustry.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.kaldie.eveindustry.repository.blueprint.Blueprint;
import com.kaldie.eveindustry.repository.blueprint.BlueprintRepository;
import com.kaldie.eveindustry.repository.groups.Adaptor;
import com.kaldie.eveindustry.repository.groups.Catagory;
import com.kaldie.eveindustry.repository.groups.CatagoryRepository;
import com.kaldie.eveindustry.repository.groups.Group;
import com.kaldie.eveindustry.repository.groups.GroupRepository;
import com.kaldie.eveindustry.repository.groups.MarketGroup;
import com.kaldie.eveindustry.repository.groups.SimpleMarketGroup;
import com.kaldie.eveindustry.repository.groups.SimpleMarketGroupRepository;
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
    private List<Group> groups;
    private List<MarketGroup> marketGroups;
    private List<Catagory> catagories;

    private Logger logger = LoggerFactory.getLogger(ESDReader.class);
    
    private final BlueprintRepository blueprintRepository;
    private final TypeIDRepository typeRepository;
    private final RegionRepository regionRepository;
    private final UniqueNamesRepository uniqueNamesRepository;
    private final SolarSystemRepository solarSystemRepository;
    private final PlanetRepository planetRepository;
    private final MoonRepository moonRepository;
    private final NPCStationRepository npcStationRepository;
    private final SimpleStargateRepository simpleStargateRepository;
    private final GroupRepository groupRepository;
    private final SimpleMarketGroupRepository simpleMarketGroupRepository;
    private final CatagoryRepository catagroyRepository;

    private final RegionVisitor regionVisitor;


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

        try {
            loadGroups();
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

    private void storeGroups() throws IOException {
        if (marketGroups == null || groups== null || catagories == null) {
            logger.info("Loading groups from yml files.");
            this.loadGroups();
        }

        catagroyRepository.updateInsertDeleteFromBulk(catagories, Catagory.class);
        groupRepository.updateInsertDeleteFromBulk(groups, Group.class);
        
        List<SimpleMarketGroup> simpleMarketGroupList = new ArrayList<>();
        marketGroups.forEach(marketGroup -> simpleMarketGroupList.add(Adaptor.from(marketGroup)));
        simpleMarketGroupRepository.updateInsertDeleteFromBulk(
            simpleMarketGroupList, 
            SimpleMarketGroup.class);
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
        logger.info("Storing Group information");
        storeGroups();
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

    private void loadGroups() throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        
        // Groups
        Map<Long, Group> groupMap = mapper.readValue(new File("resources/sde/fsd/groupIDs.yaml"),
            new TypeReference<Map<Long, Group>>() { });
        
        for (Map.Entry<Long, Group> entry : groupMap.entrySet()) {
            entry.getValue().setId(entry.getKey());
        }
        groups = new ArrayList<>(groupMap.values());

        // MarketGroups
        Map<Long, MarketGroup> marketMap = mapper.readValue(new File("resources/sde/fsd/marketGroups.yaml"),
        new TypeReference<Map<Long, MarketGroup>>() { });
    
        for (Map.Entry<Long, MarketGroup> entry : marketMap.entrySet()) {
            entry.getValue().setId(entry.getKey());
        }
        marketGroups = new ArrayList<>(marketMap.values());

        // Catagories
        Map<Long, Catagory> catagoryMap = mapper.readValue(new File("resources/sde/fsd/categoryIDs.yaml"),
        new TypeReference<Map<Long, Catagory>>() { });
    
        for (Map.Entry<Long, Catagory> entry : catagoryMap.entrySet()) {
            entry.getValue().setId(entry.getKey());
        }
        catagories = new ArrayList<>(catagoryMap.values());




    }
    
}

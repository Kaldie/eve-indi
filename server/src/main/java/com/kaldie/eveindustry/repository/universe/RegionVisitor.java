package com.kaldie.eveindustry.repository.universe;

import static java.nio.file.FileVisitResult.*;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class RegionVisitor extends SimpleFileVisitor<Path> {

    private List<Region> regions = new ArrayList<>();
    private List<SolarSystem> solarSystems = new ArrayList<>();
    private List<Planet> planets = new ArrayList<>();
    private List<Moon> moons = new ArrayList<>();
    private List<NPCStation> npcStations = new ArrayList<>();
    private List<Stargate> stargates = new ArrayList<>();
    private boolean isUnpacked = false;

    private final Logger logger = LoggerFactory.getLogger(RegionVisitor.class);

    public void reset() {
        isUnpacked = false;
        regions = new ArrayList<>();
        solarSystems = new ArrayList<>();
        planets = new ArrayList<>();
        moons = new ArrayList<>();
        npcStations = new ArrayList<>();
    }

    // Print information about
    // each type of file.
    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attr) throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        if (file.getFileName().toString().equals("region.staticdata")) {
            Region region = mapper.readValue(file.toFile(), Region.class);
            region.setName(new UniqueName(region.getRegionID()));
            region.setDirectoryName(file.getParent().toString());
            regions.add(region);
        }

        if (file.getFileName().toString().equals("solarsystem.staticdata")) {
            SolarSystem system = mapper.readValue(file.toFile(), SolarSystem.class);
            solarSystems.add(system);
        }
        return CONTINUE;
    }

    public void unpack() {
        if (!isUnpacked) {
            isUnpacked = true;
            solarSystems.forEach(solarSystem -> {
                
                stargates.addAll(solarSystem.getStargates());

                solarSystem.getPlanets().forEach(planet -> {
                    planets.add(planet);
                    planet.getMoons().forEach(moon -> {
                        moons.add(moon);
                        moon.getNpcStations().forEach(station -> {
                            npcStations.add(station);
                        });
                    });     
                });
            });
        }
    }
}

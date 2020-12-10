package com.kaldie.eveindustry.repository.universe;

import static java.nio.file.FileVisitResult.*;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.Data;

@Data
public class RegionVisitor extends SimpleFileVisitor<Path> {

    List<Region> regions = new ArrayList<>();
    List<SolarSystem> solarSystems = new ArrayList<>();

    // Print information about
    // each type of file.
    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attr) throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        if (file.getFileName().toString().equals("region.staticdata")) {
            Region region = mapper.readValue(file.toFile(), Region.class);
            region.setDirectoryName(file.getParent().toString());
            regions.add(region);
        }

        if (file.getFileName().toString().equals("solarsystem.staticdata")) {
            SolarSystem system = mapper.readValue(file.toFile(), SolarSystem.class);
            solarSystems.add(system);
        }
        return CONTINUE;
    }
}

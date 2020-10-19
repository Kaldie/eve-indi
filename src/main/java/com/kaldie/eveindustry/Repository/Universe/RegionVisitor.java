package com.kaldie.eveindustry.Repository.Universe;

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

public class RegionVisitor extends SimpleFileVisitor<Path> {

    List<Region> regions = new ArrayList<>();

    // Print information about
    // each type of file.
    @Override
    public FileVisitResult visitFile(Path file,
                                   BasicFileAttributes attr) throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        if (file.getFileName().toString().equals("region.staticdata")) {
            Region region = mapper.readValue(file.toFile(), Region.class);
            region.setName(file.getName(file.getNameCount()-2).toString());
            regions.add(region);
        }
        return CONTINUE;
    }

    // Print each directory visited.
    @Override
    public FileVisitResult postVisitDirectory(Path dir,
                                          IOException exc) {
        return CONTINUE;
    }

    // If there is some error accessing
    // the file, let the user know.
    // If you don't override this method
    // and an error occurs, an IOException 
    // is thrown.
    @Override
    public FileVisitResult visitFileFailed(Path file,
                                       IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }

    public List<Region> getRegions() {
        return this.regions;
    }
}

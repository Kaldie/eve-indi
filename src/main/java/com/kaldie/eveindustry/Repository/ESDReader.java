package com.kaldie.eveindustry.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import com.kaldie.eveindustry.Repository.BluePrint.Blueprint;
import com.kaldie.eveindustry.Repository.BluePrint.BlueprintRepository;
import com.kaldie.eveindustry.Repository.TypeID.TypeIDRepository;
import com.kaldie.eveindustry.Repository.TypeID.TypeId;
import com.kaldie.eveindustry.Repository.TypeID.TypeMaterials.TypeMaterial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;


@Service
@Data
public class ESDReader {
    
    private Collection<TypeId> types;
    private Collection<Blueprint> blueprints;
    private Collection<TypeMaterial> typeMaterials;
    
    @Autowired
    private BlueprintRepository blueprintRepository;
    
    @Autowired
    private TypeIDRepository typeRepository;

    public void loadEsd() {
        try {
            loadTypeIds();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.load_blueprints();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            load_typeMaterials();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void storeBlueprints() throws IOException {
        if (blueprints == null) {
            this.load_blueprints();
        }
        blueprintRepository.saveAll(blueprints);
    }

    private void storeTypeIds() throws IOException {
        if (types == null) {
            this.loadTypeIds();
        }
        typeRepository.saveAll(types);
    }

    public void storeEsd() throws IOException {
        storeTypeIds();
        storeBlueprints();
    }

    // public List<Material> getRequiredMaterials(Long id) throws IOException {
    //     // if (blueprints == null) {
    //     //     this.load_blueprints();
    //     // }

    //     // List<Entry<Long, Blueprint>> bluePrints = blueprints.filter(item -> item.getValue().getProducedID().equals(id)).collect(Collectors.toList());

    //     // if (bluePrints.size() != 1) {
    //     //     throw new IOException("booboo");
    //     // }

    //     // return bluePrints.get(0).getValue().getActivities().getManufacturing().getMaterials();

    // }

    public String getName(Long id) throws IOException {
        if (types == null) {
            this.loadTypeIds();
        }
        Optional<TypeId> foundType = types.stream().filter(type -> type.getId().equals(id)).findFirst();
        return foundType.isPresent() ? foundType.get().toString() : "unkown";
    }

    private void loadTypeIds() throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<Long, TypeId> typeMap = mapper.readValue(new File("resources/typeIDs.yaml"),
            new TypeReference<Map<Long, TypeId>>() { });
        
        // Add the id to the entry
        for (Map.Entry<Long, TypeId> entry : typeMap.entrySet()) {
            entry.getValue().setId(entry.getKey());
        }
        types = typeMap.values();
    }

    private void load_blueprints() throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<Long, Blueprint> blueprintMap = mapper.readValue(new File("resources/blueprints.yaml"),
            new TypeReference<Map<Long, Blueprint>>() { });

        for (Map.Entry<Long, Blueprint> entry : blueprintMap.entrySet()) {
            entry.getValue().setBlueprintTypeID(entry.getKey());
        }
        blueprints = blueprintMap.values();
    }

    private void load_typeMaterials() throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<Long, TypeMaterial> typeMaterialMap = mapper.readValue(new File("resources/typeMaterials.yaml"),
            new TypeReference<Map<Long, TypeMaterial>>() { });
        typeMaterials = typeMaterialMap.values();
    }
    
}

package com.kaldie.eveindustry.deserializers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.kaldie.eveindustry.repository.universe.Moon;
import com.kaldie.eveindustry.repository.universe.Planet;

public class PlanetDeserializer extends EveDeserializer<Planet> {

    private static final long serialVersionUID = 1L;

    public PlanetDeserializer() {
        super(Planet.class);
    }

    @Override
    public Planet deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        JsonNode node = jp.getCodec().readTree(jp);
        Planet planet = defaultDeserialisation(jp, ctxt, node);
        
        List<Moon> moons = new ArrayList<>();
        node.with("moons").fields().forEachRemaining(field -> {
            JsonParser moonParser = field.getValue().traverse();
            moonParser.setCodec(jp.getCodec());

            Moon moon;
            try {
                moon = moonParser.readValueAs(Moon.class);
                moon.setId(Long.parseLong(field.getKey()));
                moon.setPlanet(planet);
                moons.add(moon);
      
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });

        planet.setMoons(moons);
        return planet;
    }



    
}

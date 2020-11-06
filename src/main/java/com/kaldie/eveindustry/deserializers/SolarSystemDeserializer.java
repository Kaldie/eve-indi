package com.kaldie.eveindustry.deserializers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.kaldie.eveindustry.repository.universe.Planet;
import com.kaldie.eveindustry.repository.universe.SolarSystem;

public class SolarSystemDeserializer extends EveDeserializer<SolarSystem> {
    private static final long serialVersionUID = 1L;

    public SolarSystemDeserializer() {
        super(SolarSystem.class);
    }

    @Override
    public SolarSystem deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        SolarSystem solarSystem = defaultDeserialisation(jp, ctxt, node);

        solarSystem.setPlanets(new ArrayList<>());
        // JsonNode node = parser.getCodec().readTree(parser);
           
        List<Planet> planets = new ArrayList<Planet>();
        node.with("planets").fields().forEachRemaining(field -> {
            JsonParser planetParser = field.getValue().traverse();
            planetParser.setCodec(jp.getCodec());
           
            try {
                Planet planet = planetParser.readValueAs(Planet.class);
                planet.setId(Long.parseLong(field.getKey()));
                planet.setSolarSystem(solarSystem);
                planets.add(planet);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
  
        });

        solarSystem.setPlanets(planets);
        return solarSystem;
    }
}
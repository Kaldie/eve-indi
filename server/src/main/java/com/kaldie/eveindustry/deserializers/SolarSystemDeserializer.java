package com.kaldie.eveindustry.deserializers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.kaldie.eveindustry.repository.universe.Planet;
import com.kaldie.eveindustry.repository.universe.SolarSystem;
import com.kaldie.eveindustry.repository.universe.Stargate;
import com.kaldie.eveindustry.repository.universe.UniqueName;

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
        solarSystem.setName(new UniqueName(solarSystem.getSolarSystemID()));

        serializePlanets(solarSystem, node, jp);
        serializeStargates(solarSystem, node, jp);
       
        return solarSystem;
    }

    private void serializePlanets(SolarSystem solarSystem, JsonNode node, JsonParser jp) {

        List<Planet> planets = new ArrayList<>();

        node.with("planets").fields().forEachRemaining(field -> {
            JsonParser planetParser = field.getValue().traverse();
            planetParser.setCodec(jp.getCodec());
           
            try {
                Planet planet = planetParser.readValueAs(Planet.class);
                planet.setId(Long.parseLong(field.getKey()));
                planet.setName(new UniqueName(planet.getId()));
                planet.setSolarSystem(solarSystem);
                planets.add(planet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        solarSystem.setPlanets(planets);
    }

    private void serializeStargates(SolarSystem solarSystem, JsonNode node, JsonParser jp) {

        List<Stargate> stargates = new ArrayList<>();

        node.with("stargates").fields().forEachRemaining(field -> {
            JsonParser gateParser = field.getValue().traverse();
            gateParser.setCodec(jp.getCodec());
           
            try {
                Stargate stargate = gateParser.readValueAs(Stargate.class);
                stargate.setId(Long.parseLong(field.getKey()));
                stargate.setDestination(new Stargate(field.getValue().findValue("destination").asLong()));
                stargate.setLocation(solarSystem);
                stargates.add(stargate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        solarSystem.setStargates(stargates);
    }



}

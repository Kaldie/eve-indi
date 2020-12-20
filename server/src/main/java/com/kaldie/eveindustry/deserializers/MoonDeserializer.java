package com.kaldie.eveindustry.deserializers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.kaldie.eveindustry.repository.universe.Moon;
import com.kaldie.eveindustry.repository.universe.NPCStation;
import com.kaldie.eveindustry.repository.universe.UniqueName;

public class MoonDeserializer extends EveDeserializer<Moon> {

    private static final long serialVersionUID = 1L;

    public MoonDeserializer() {
        super(Moon.class);
    }

    @Override
    public Moon deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {

        JsonNode node = jp.getCodec().readTree(jp);
        Moon moon = defaultDeserialisation(jp, ctxt, node);
        
        List<NPCStation> stations = new ArrayList<>();
        node.with("npcStations").fields().forEachRemaining(field -> {
            JsonParser stationParser = field.getValue().traverse();
            stationParser.setCodec(jp.getCodec());

            NPCStation station;
            try {
                station = stationParser.readValueAs(NPCStation.class);
                station.setId(Long.parseLong(field.getKey()));
                station.setName(new UniqueName(station.getId()));
                station.setMoon(moon);
                stations.add(station);
      
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });

        moon.setNpcStations(stations);
        return moon;
    }
   
}

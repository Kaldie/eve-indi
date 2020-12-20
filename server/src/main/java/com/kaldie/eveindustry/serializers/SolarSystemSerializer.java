package com.kaldie.eveindustry.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kaldie.eveindustry.repository.universe.SolarSystem;

public class SolarSystemSerializer extends StdSerializer<SolarSystem> {

    public SolarSystemSerializer() {
        this(null);
    }

    public SolarSystemSerializer(Class<SolarSystem> system) {
        super(system);
    }
    
    @Override
    public void serialize(SolarSystem value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("solarSystemID", value.getSolarSystemID());
        gen.writeBooleanField("border", value.getBorder());
        gen.writeBooleanField("hub", value.getHub());
        gen.writeNumberField("security", value.getSecurity());
        
        if (value.getName() != null)
            gen.writeStringField("name", value.getName().getItemName());

        gen.writeNumberField("number of planets", value.getPlanets().size());
        gen.writeNumberField("number of stargates", value.getStargates().size());
    }

    
}

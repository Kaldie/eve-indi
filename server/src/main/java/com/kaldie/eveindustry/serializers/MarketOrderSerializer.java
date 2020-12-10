package com.kaldie.eveindustry.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kaldie.eveindustry.repository.market.MarketOrder;

public class MarketOrderSerializer extends StdSerializer<MarketOrder> {

    private static final long serialVersionUID = 1L;

    public MarketOrderSerializer() {
        this(null);
    }

    public MarketOrderSerializer(Class<MarketOrder> order) {
        super(order);
    }



    @Override
    public void serialize(MarketOrder value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("orderId", value.getOrderId());
        gen.writeNumberField("duration", value.getDuration());
        gen.writeBooleanField("isBuy", value.getIsBuy());
        gen.writeNumberField("location", value.getLocation());
        gen.writeNumberField("minVolume", value.getMinVolume());
        gen.writeNumberField("price", value.getPrice());
        gen.writeStringField("range", value.getRange());
        gen.writeNumberField("volumeRemain", value.getVolumeRemain());
        gen.writeNumberField("volumeTotal", value.getVolumeTotal());
        gen.writeStringField("issued", value.getIssued().toString());
        gen.writeStringField("systemId", value.getSystemId().getName().getItemName());
        gen.writeStringField("typeId", value.getTypeId().getName().getEn());
        gen.writeEndObject();
    }
}

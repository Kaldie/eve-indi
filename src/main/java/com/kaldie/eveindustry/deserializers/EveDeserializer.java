package com.kaldie.eveindustry.deserializers;

import java.io.IOException;

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
import com.fasterxml.jackson.databind.type.TypeFactory;

public abstract class EveDeserializer<T> extends StdDeserializer<T> {


    protected EveDeserializer(Class<?> vc) {
      super(vc);
    }

    private static final long serialVersionUID = 1L;

	public T defaultDeserialisation(JsonParser jp, DeserializationContext ctxt, JsonNode node) 
        throws IOException, JsonProcessingException{
        ObjectCodec oc = jp.getCodec();

        DeserializationConfig config = ctxt.getConfig();

        JavaType type = TypeFactory.defaultInstance().constructType(handledType());
        JsonDeserializer<Object> defaultDeserializer = BeanDeserializerFactory.instance.buildBeanDeserializer(ctxt, type, config.introspect(type));
    
        if (defaultDeserializer instanceof ResolvableDeserializer) {
            ((ResolvableDeserializer) defaultDeserializer).resolve(ctxt);
        }
    
        JsonParser treeParser = oc.treeAsTokens(node);
        config.initialize(treeParser);
    
        if (treeParser.getCurrentToken() == null) {
            treeParser.nextToken();
        }

        return (T) defaultDeserializer.deserialize(treeParser, ctxt);
    }

}

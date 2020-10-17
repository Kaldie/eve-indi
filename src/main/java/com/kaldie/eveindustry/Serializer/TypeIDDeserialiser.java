// package com.kaldie.eveindustry.Serializer;

// import java.io.IOException;

// import com.fasterxml.jackson.core.JsonParser;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.DeserializationContext;
// import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

// public class TypeIDDeserialiser extends StdDeserializer<TypeID> {

//     final static 

//     public ItemDeserializer() { 
//         this(null); 
//     }

//     public ItemDeserializer(Class<?> vc) { 
//         super(vc); 
//     }

//     // @Override
//     // public Item deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
//     //     JsonNode node = jp.getCodec().readTree(jp);
//     //     int id = (Integer) ((IntNode) node.get("id")).numberValue();
//     //     String itemName = node.get("itemName").asText();
//     //     int userId = (Integer) ((IntNode) node.get("createdBy")).numberValue();

//     //     return new Item(id, itemName, new User(userId, null));
//     // }

//     @Override
//     public TypeID deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
//         // TODO Auto-generated method stub
//         return null;
//     }
// }
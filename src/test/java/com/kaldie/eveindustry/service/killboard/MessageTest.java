package com.kaldie.eveindustry.service.killboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaldie.eveindustry.repository.type_id.TypeId;
import com.kaldie.eveindustry.repository.zkillboard.Message;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class MessageTest {


    @Test
    void serialiseExampleMessage() throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("exampleZkillMessage.txt");
        assertNotNull(stream);
        String text = IOUtils.toString(stream, StandardCharsets.UTF_8.name());
        assertFalse(text.isEmpty());
        Message message = new ObjectMapper().readValue(text, Message.class);
        assertNotNull(message); // test deserialisation
        assertEquals(0, message.getId() ); // test default construction
        assertEquals(34, message.getAttackers().size()); // test if the attackers were deserialized properly
        assertEquals(new TypeId(33476).getId(), message.getVictim().getShip_type_id().getId()); // get the ship id of the victum
    }

 
    
}

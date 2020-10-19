package com.kaldie.eveindustry.Publisher.Killboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaldie.eveindustry.Events.Killboard.KillEvent;
import com.kaldie.eveindustry.Repository.Zkillboard.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class KillboardWebSocketPublisher {

	private KillboardWebSocketPublisher() {}

	@Autowired
	private ApplicationEventPublisher publisher;

	public void publish(String message) {
		try {
			Message serializedMessage = new ObjectMapper().readValue(message, Message.class);
			publisher.publishEvent(new KillEvent(ApplicationEventPublisher.class, serializedMessage));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
package com.kaldie.eveindustry.publisher.killboard;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaldie.eveindustry.events.Killboard.KillEvent;
import com.kaldie.eveindustry.repository.zkillboard.Message;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KillboardWebSocketPublisher {

	private final ApplicationEventPublisher publisher;

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
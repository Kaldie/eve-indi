package com.kaldie.eveindustry.Publisher.Killboard;

import java.net.URI;
import java.nio.ByteBuffer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaldie.eveindustry.Events.Killboard.KillEvent;
import com.kaldie.eveindustry.Repository.Zkillboard.Message;

import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class KillboardWebSocketClient extends WebSocketClient {

	private static String url = "wss://zkillboard.com/websocket/";

	@Autowired
	private ApplicationEventPublisher publisher;

	public KillboardWebSocketClient() throws URISyntaxException {
		super(new URI(KillboardWebSocketClient.url));
	}

	public void startListnening() throws InterruptedException, JsonProcessingException {
		this.connectBlocking();
		this.sendSettings();
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		System.out.println("new connection opened");
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		System.out.println("closed with exit code " + code + " additional info: " + reason);
	}

	@Override
	public void onMessage(String message) {
		try {
			Message serializedMessage = new ObjectMapper().readValue(message, Message.class);
			publisher.publishEvent(new KillEvent(this, serializedMessage));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(ByteBuffer message) {
		System.out.println("received ByteBuffer");
	}

	@Override
	public void onError(Exception ex) {
		System.err.println("an error occurred:" + ex);
	}

	private void sendSettings() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String settingsAsString = objectMapper.writeValueAsString(new Settings());
		this.send(settingsAsString);
	}

}
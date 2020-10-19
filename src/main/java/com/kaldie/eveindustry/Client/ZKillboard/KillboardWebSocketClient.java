package com.kaldie.eveindustry.Client.ZKillboard;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaldie.eveindustry.Publisher.Killboard.KillboardWebSocketPublisher;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KillboardWebSocketClient extends WebSocketClient {

	private static String url = "wss://zkillboard.com/websocket/";

	private Logger logger = LoggerFactory.getLogger(KillboardWebSocketClient.class);

	@Autowired
	KillboardWebSocketPublisher publisher;

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
		logger.info("New message recieved");
		publisher.publish(message);
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
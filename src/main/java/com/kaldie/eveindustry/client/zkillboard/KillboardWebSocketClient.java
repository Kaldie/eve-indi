package com.kaldie.eveindustry.client.zkillboard;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaldie.eveindustry.publisher.killboard.KillboardWebSocketPublisher;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Component
public class KillboardWebSocketClient extends WebSocketClient {

	@Value
	public class Settings {
		public String action = "sub";
		public String channel = "killstream";   
	}
	
	private static String url = "wss://zkillboard.com/websocket/";

	private Logger logger = LoggerFactory.getLogger(KillboardWebSocketClient.class);


	private final KillboardWebSocketPublisher publisher;

	@Autowired
	public KillboardWebSocketClient(KillboardWebSocketPublisher publisher) throws URISyntaxException {
		super(new URI(KillboardWebSocketClient.url));
		this.publisher = publisher;
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
		this.reconnect();
		try {
			this.sendSettings();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendSettings() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String settingsAsString = objectMapper.writeValueAsString(new Settings());
		this.send(settingsAsString);
	}

}
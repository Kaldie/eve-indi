package com.kaldie.eveindustry;

import java.io.IOException;

import com.kaldie.eveindustry.client.zkillboard.KillboardWebSocketClient;
import com.kaldie.eveindustry.repository.ESDReader;
import com.kaldie.eveindustry.repository.universe.RegionRepository;
import com.kaldie.eveindustry.service.experiments.ExperimentRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableCaching
@RequiredArgsConstructor
public class EveIndustryApplication {

	Logger logger = LoggerFactory.getLogger(EveIndustryApplication.class);

	// private final KillboardWebSocketClient killboardWebSocketClient;

	// private final RegionRepository regionRepository;

	// private final MarketOppurtunities opp;

	private final ExperimentRunner runner;

	private final ESDReader reader;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws IOException {

		// reader.storeEsd();
		
		runner.runAll();

		// try {
		// 	// MarketOrders.getRegionalItemOrders(34L, 10000002L);
		// } catch (ApiException e) {
		// 	e.printStackTrace();
		// }
		// // try {
		// 	killboardWebSocketClient.startListnening();
		// } catch (JsonProcessingException | InterruptedException e) {
		// 	e.printStackTrace();
		// }
	}


	public static void main(String[] args) {
		SpringApplication.run(EveIndustryApplication.class, args);
	}
	
}

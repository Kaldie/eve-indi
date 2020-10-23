package com.kaldie.eveindustry;

import com.kaldie.eveindustry.client.zkillboard.KillboardWebSocketClient;
import com.kaldie.eveindustry.repository.ESDReader;
import com.kaldie.eveindustry.repository.universe.RegionRepository;
import com.kaldie.eveindustry.service.fu.MarketOppurtunities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableCaching
public class EveIndustryApplication {

	Logger logger = LoggerFactory.getLogger(EveIndustryApplication.class);

	@Autowired
	private KillboardWebSocketClient killboardWebSocketClient;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private MarketOppurtunities opp;

	// @Autowired
	// ExperimentRunner runner;

	@Autowired
	ESDReader reader;


	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		// logger.error("{}",regionRepository.getRegionByName("Domain").getRegionID());
		// new MarketOppurtunities().run();
		// opp.run();
		System.out.println("FUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
		// System.out.println(opp.getClass());
		System.out.println("FUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
		// applicationContext.getBean("com.kaldie.eveindustry.Service.Experiments.Market.MarketOppurtunities");
		// runner.setApplicationContext(applicationContext);
		// runner.runAll();
		// try {
		// 	// MarketOrders.getRegionalItemOrders(34L, 10000002L);
		// } catch (ApiException e) {
		// 	// TODO Auto-generated catch block
		// 	e.printStackTrace();
		// }
		// // try {
		// 	killboardWebSocketClient.startListnening();
		// } catch (JsonProcessingException | InterruptedException e) {
		// 	// TODO Auto-generated catch block
		// 	e.printStackTrace();
		// }
	}


	public static void main(String[] args) {
		SpringApplication.run(EveIndustryApplication.class, args);
	}
	
}

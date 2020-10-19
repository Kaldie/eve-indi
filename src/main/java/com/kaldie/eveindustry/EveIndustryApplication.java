package com.kaldie.eveindustry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kaldie.eveindustry.Publisher.Killboard.KillboardWebSocketClient;
import com.kaldie.eveindustry.Repository.ESDReader;

@SpringBootApplication
@EnableCaching
public class EveIndustryApplication {

	@Autowired
	private KillboardWebSocketClient killboardWebSocketClient;

	@Autowired
	ESDReader reader;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {


		// try {
			// reader.storeEsd();
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }


		try {
			killboardWebSocketClient.startListnening();
		} catch (JsonProcessingException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// List<RequiredMaterialsEntity> materials = requiredMaterialsRepository.getRequiredMaterials(11202L);
		// materials.stream().forEach(material -> System.out.println(material.getRequiredMaterial().getName().getEn()));
	
	}


	public static void main(String[] args) {
		SpringApplication.run(EveIndustryApplication.class, args);
	}
	
}

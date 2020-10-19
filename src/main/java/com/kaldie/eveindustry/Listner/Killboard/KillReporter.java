package com.kaldie.eveindustry.Listner.Killboard;

import com.kaldie.eveindustry.Events.Killboard.KillEvent;
import com.kaldie.eveindustry.Repository.TypeID.TypeIDRepository;
import com.kaldie.eveindustry.Repository.Zkillboard.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class KillReporter implements ApplicationListener<KillEvent> {

    @Autowired
    TypeIDRepository typeIDRepository;

    private Logger logger = LoggerFactory.getLogger(KillReporter.class);

    @Override
    public void onApplicationEvent(KillEvent event) {
        Message message = event.getMessage();
        logger.info("There is a kill to report");
        logger.info("Reported at: {}",message.getKillmailTime());
        typeIDRepository.findLoadedTypeId(message.getVictim().getShip_type_id().getId()).ifPresentOrElse(
            ship -> logger.info("Victims ship was a {}",ship.getName().getEn()),
            () -> logger.info("Victims ship is unknown")
        );     
    }
}

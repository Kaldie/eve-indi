package com.kaldie.eveindustry.Listner.Killboard;

import java.util.Date;

import com.kaldie.eveindustry.Events.Killboard.KillEvent;
import com.kaldie.eveindustry.Repository.TypeID.TypeId;
import com.kaldie.eveindustry.Repository.Zkillboard.Message;
import com.kaldie.eveindustry.Repository.Zkillboard.MessageRepository;
import com.kaldie.eveindustry.Service.Killboard.KillboardServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class KillListner implements ApplicationListener<KillEvent> {

    @Autowired
    private MessageRepository killboardRepository;

    @Autowired
    private KillboardServiceImp service;

    @Override
    public void onApplicationEvent(KillEvent event) {
        Message message = event.getMessage();
        killboardRepository.save(message);
        Date date = message.getKillmailTime();

        // list all the items that were destroyed
        message.getVictim().getItems().stream()
        .filter(item -> item.getDestroyed() > 0)
        .forEach(item -> {
            TypeId id = item.getItemId();
            long quentity = item.getDestroyed();
            service.addDestroyedMaterials(id, quentity, date);
        });

        // list the ship that was destroyed
        if (message.getVictim().getShip_type_id() != null) {
            service.addDestroyedMaterials(message.getVictim().getShip_type_id(),1, date);
        }
    }
    
}

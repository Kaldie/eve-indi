package com.kaldie.eveindustry.listner.killboard;

import java.util.Date;

import com.kaldie.eveindustry.events.killboard.KillEvent;
import com.kaldie.eveindustry.repository.type_id.TypeId;
import com.kaldie.eveindustry.repository.zkillboard.Message;
import com.kaldie.eveindustry.repository.zkillboard.MessageRepository;
import com.kaldie.eveindustry.service.killboard.KillboardServiceImp;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KillListner implements ApplicationListener<KillEvent> {

    private final MessageRepository killboardRepository;

    private final KillboardServiceImp service;

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

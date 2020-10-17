package com.kaldie.eveindustry.Listner.Killboard;

import java.util.Date;
import java.util.List;

import com.kaldie.eveindustry.Events.Killboard.KillEvent;
import com.kaldie.eveindustry.Repository.RequiredMaterials.RequiredMaterialsEntity;
import com.kaldie.eveindustry.Repository.RequiredMaterials.RequiredMaterialsRepository;
import com.kaldie.eveindustry.Repository.RequiredMaterials.RequiredMiniralsRepository;
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
            service.addMaterials(id, quentity, date);
        });

        // list the ship that was destroyed
        if (message.getVictim().getShip_type_id() != null) {
            service.addMaterials(message.getVictim().getShip_type_id(),1, date);
        }
    }
    
}

package com.kaldie.eveindustry.Events.Killboard;

import com.kaldie.eveindustry.Repository.Zkillboard.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

import lombok.Data;

@Data
public class KillEvent extends ApplicationEvent {
    @Autowired
    private Message message;

    static final long serialVersionUID = 1l;

    public KillEvent(Object source, Message message) {
        super(source);
        this.message = message;
    }
    
}

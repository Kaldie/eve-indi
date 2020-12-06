package com.kaldie.eveindustry.events.killboard;

import com.kaldie.eveindustry.repository.zkillboard.Message;

import org.springframework.context.ApplicationEvent;

import lombok.Data;

@Data
public class KillEvent extends ApplicationEvent {

    private final Message message;

    static final long serialVersionUID = 1l;

    public KillEvent(Object source, Message message) {
        super(source);
        this.message = message;
    }
    
}

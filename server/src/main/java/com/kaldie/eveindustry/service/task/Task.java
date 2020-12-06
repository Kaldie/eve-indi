package com.kaldie.eveindustry.service.task;

import org.springframework.stereotype.Component;

@com.kaldie.eveindustry.annotations.Experiment
@Component
public abstract class Task {

    public abstract void run();

    public abstract void setup();

    public abstract void breakdown();
    
}

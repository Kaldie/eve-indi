package com.kaldie.eveindustry.service.experiments;

import org.springframework.stereotype.Component;

@com.kaldie.eveindustry.eve_indi_annotations.Experiment
@Component
public abstract class Task {

    public abstract void run();

    public abstract void setup();

    public abstract void breakdown();
    
}
package com.kaldie.eveindustry.repository.universe;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stargate {
    
    @Id
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination")
    private SolarSystem destination;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    private SolarSystem location;
}

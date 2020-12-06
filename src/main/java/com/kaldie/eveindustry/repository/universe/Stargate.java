package com.kaldie.eveindustry.repository.universe;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stargate {

    public Stargate() {
    } 

    public Stargate(int id_int) {
        this.id = Long.valueOf(id_int);
    } 
    
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination")
    @JsonIgnore
    private SolarSystem destination;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    @JsonIgnore
    private SolarSystem location;
}

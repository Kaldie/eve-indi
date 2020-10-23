package com.kaldie.eveindustry.repository.universe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Region {

    @Id
    @Column(name = "regionId")
    private long regionID;
    private String name;
    
}

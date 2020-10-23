package com.kaldie.eveindustry.repository.blueprint;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activities {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Activity copying;

    @OneToOne(cascade = CascadeType.ALL)
    private Activity invention;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Activity manufacturing;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Activity researchMaterial;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Activity researchTime;
}

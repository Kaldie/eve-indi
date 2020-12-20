package com.kaldie.eveindustry.repository.universe;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kaldie.eveindustry.deserializers.SolarSystemDeserializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = SolarSystemDeserializer.class)
public class SolarSystem {


    public SolarSystem() {
    } 

    public SolarSystem(int id) {
        this.solarSystemID = Long.valueOf(id);
    } 

    @Id
    @Column(name="id")
    private Long solarSystemID;
    
    @OneToMany(mappedBy = "solarSystem", fetch = FetchType.LAZY)
    @JsonIgnore // this property is read via the custom Deserializer 
    private List<Planet> planets;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    @JsonIgnore // this property is read via the custom Deserializer 
    private List<Stargate> stargates;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnore
    private Region region;
    
    private Boolean border;
    
    private Boolean hub;

    private Float security;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id")
    private UniqueName name;

	public Planet getPlanet(long id) {
        Planet planet = null;
        for(Planet aPlanet : planets) {
            if (aPlanet.getId().equals(id)) planet = aPlanet;
        }
        return planet;
    }
    
    @Transient
    private final Logger logger = LoggerFactory.getLogger(SolarSystem.class);

    @Override
    public boolean equals(Object other) {
        boolean isEquals = true;
        if( other != null && other.getClass() == SolarSystem.class) {
            SolarSystem otherSystem = (SolarSystem) other;
            isEquals = otherSystem.solarSystemID.equals(this.solarSystemID);
            isEquals = isEquals && this.border.equals(otherSystem.border);
            isEquals = isEquals && this.hub.equals(otherSystem.hub);
            isEquals = isEquals && this.security.equals(otherSystem.security);
            isEquals = isEquals && this.border.equals(otherSystem.border);
        } else {
            isEquals = false;
        }
        return isEquals;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(solarSystemID);
    }
}

package com.kaldie.eveindustry.repository.universe;

import javax.persistence.CascadeType;
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

    public Stargate(Long id) {
        this.id = id;
    } 
    
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination")
    @JsonIgnore
    private Stargate destination;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    @JsonIgnore
    private SolarSystem location;

    @Override
    public boolean equals(Object other) {
        boolean isEquals = true;
        if( other != null && other.getClass() == SolarSystem.class) {
            Stargate otherStargate = (Stargate) other;
            isEquals = otherStargate.id.equals(this.id);
            isEquals = isEquals && this.destination.getId().equals(otherStargate.destination.getId());
            isEquals = isEquals && this.location.getSolarSystemID().equals(otherStargate.location.getSolarSystemID());
        } else {
            isEquals = false;
        }
        return isEquals;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}

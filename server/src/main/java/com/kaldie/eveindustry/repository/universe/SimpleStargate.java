package com.kaldie.eveindustry.repository.universe;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name="stargate")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStargate {

    public SimpleStargate() {
    } 

    public SimpleStargate(Long id) {
        this.id = id;
    } 
    
    @Id
    private Long id;

    @JsonIgnore
    private Long destination;
    
    @JsonIgnore
    private Long location;

    @Override
    public boolean equals(Object other) {
        boolean isEquals = true;
        if( other != null && other.getClass() == SolarSystem.class) {
            SimpleStargate otherStargate = (SimpleStargate) other;
            isEquals = otherStargate.id.equals(this.id);
            isEquals = isEquals && this.destination.equals(otherStargate.destination);
            isEquals = isEquals && this.location.equals(otherStargate.location);
        } else {
            isEquals = false;
        }
        return isEquals;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    public static SimpleStargate from(Stargate stargate) {
        SimpleStargate simpleStargate = new SimpleStargate();

        if (stargate.getId() != null) {
            simpleStargate.id = stargate.getId();
        }
       
        if (stargate.getLocation() != null) {
            simpleStargate.location = stargate.getLocation().getSolarSystemID();
        }

        if (stargate.getDestination() != null) {
            simpleStargate.destination = stargate.getDestination().getId();
        }

        return simpleStargate;
    }
}

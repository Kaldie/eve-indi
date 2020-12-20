package com.kaldie.eveindustry.repository.universe;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kaldie.eveindustry.deserializers.PlanetDeserializer;
import com.kaldie.eveindustry.repository.type_id.TypeId;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = PlanetDeserializer.class)
public class Planet {
    
    @Id
    private Long id;

    private int celestialIndex;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TypeId typeID;

    @OneToMany(mappedBy = "planet", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Moon> moons;
    
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id")
    private UniqueName name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="solar_system_id")
    @JsonIgnore
    private SolarSystem solarSystem;

    public Moon getMoon(Long id) {
        Moon thisMoon = null;
        for(Moon moon : moons) {
            if (moon.getId().equals(id)) thisMoon = moon;
        }
        return thisMoon;
    }

    @Override
    public boolean equals(Object other) {
        boolean isEquals = true;
        if( other != null && other.getClass() == Planet.class) {
            Planet otherPlanet = (Planet) other;
            isEquals = otherPlanet.id == this.id;
            isEquals = isEquals && this.celestialIndex == otherPlanet.celestialIndex;
            isEquals = isEquals && this.typeID.getId() == otherPlanet.typeID.getId();

            isEquals = isEquals && this.name.getItemID() == otherPlanet.name.getItemID();
            isEquals = isEquals && this.solarSystem.getSolarSystemID() == otherPlanet.solarSystem.getSolarSystemID();
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

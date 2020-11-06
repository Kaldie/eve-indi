package com.kaldie.eveindustry.repository.universe;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

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

    @OneToMany(mappedBy = "planet", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Moon> moons;
    
    private int celetialIndex;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TypeId typeID;

    @ManyToOne
    @JoinColumn(name="solar_system_id")
    private SolarSystem solarSystem;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private UniqueName name;

    public Moon getMoon(Long id) {
        Moon thisMoon = null;
        for(Moon moon : moons) {
            if (moon.getId().equals(id)) thisMoon = moon;
        }
        return thisMoon;
    }

}

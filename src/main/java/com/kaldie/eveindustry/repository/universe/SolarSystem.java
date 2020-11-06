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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kaldie.eveindustry.deserializers.SolarSystemDeserializer;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = SolarSystemDeserializer.class)
public class SolarSystem {

    @Id
    @Column(name="id")
    private Long solarSystemID;
    
    @OneToMany(mappedBy = "solarSystem", cascade = CascadeType.ALL)
    @JsonIgnore // this property is read via the custom Deserializer 
    private List<Planet> planets;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    private Boolean border;

    private Boolean hub;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private UniqueName name;

	public Planet getPlanet(long id) {
        Planet planet = null;
        for(Planet aPlanet : planets) {
            if (aPlanet.getId().equals(id)) planet = aPlanet;
        }
        return planet;
	}

}

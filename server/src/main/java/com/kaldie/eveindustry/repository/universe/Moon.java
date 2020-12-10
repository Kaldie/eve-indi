package com.kaldie.eveindustry.repository.universe;

import java.util.List;

import javax.persistence.CascadeType;
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
import com.kaldie.eveindustry.deserializers.MoonDeserializer;
import com.kaldie.eveindustry.repository.type_id.TypeId;

import lombok.Data;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@JsonDeserialize(using = MoonDeserializer.class)
public class Moon {
    @Id
    private Long id;

    @OneToMany(mappedBy = "moon", fetch = FetchType.LAZY)
    @JsonIgnore
    List<NPCStation> npcStations;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TypeId typeID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="planet_id")
    @JsonIgnore
    Planet planet;   

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private UniqueName name;
}


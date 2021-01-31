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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kaldie.eveindustry.deserializers.MoonDeserializer;
import com.kaldie.eveindustry.repository.type_id.TypeId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id")
    private UniqueName name;

    @Transient
    private final Logger logger = LoggerFactory.getLogger(Moon.class);

    @Override
    public boolean equals(Object other) {
        boolean isEquals = true;
        if( other != null && other.getClass() == Moon.class) {
            Moon otherMoon = (Moon) other;
            isEquals = otherMoon.id == this.id;
            isEquals = isEquals && this.typeID.getId().equals(otherMoon.getTypeID().getId());
            isEquals = isEquals && this.getPlanet().getId().equals(otherMoon.getPlanet().getId());
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


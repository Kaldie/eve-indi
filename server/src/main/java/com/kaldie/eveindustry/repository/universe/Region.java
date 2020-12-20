package com.kaldie.eveindustry.repository.universe;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Region {

    @Id
    @Column(name = "id")
    private Long regionID;
    
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id")
    private UniqueName name;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<SolarSystem> solarSystems;
    
    @Transient
    private String directoryName;

    @Override
    public boolean equals(Object other) {
        boolean isEquals = true;
        if( other != null && other.getClass() == Region.class) {
            Region otherRegion = (Region) other;
            isEquals = otherRegion.regionID.equals(this.regionID);           
        } else {
            isEquals = false;
        }
        return isEquals;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(regionID);
    }
    
}

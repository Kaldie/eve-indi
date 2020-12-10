package com.kaldie.eveindustry.repository.universe;

import java.util.List;

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
    private long regionID;
    
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id")
    private UniqueName name;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<SolarSystem> solarSystems;
    
    @Transient
    private String directoryName;
    
}

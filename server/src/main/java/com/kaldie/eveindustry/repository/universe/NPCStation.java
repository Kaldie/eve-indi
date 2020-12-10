package com.kaldie.eveindustry.repository.universe;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaldie.eveindustry.repository.type_id.TypeId;

import lombok.Data;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "station")
@Data
public class NPCStation {
    @Id
    private Long id;

    @Column(name = "graphical_id")
    private Long graphicID;
    private Boolean isConquerable;

    @Column(name = "owner_id")
    private Long ownerID;
    private float reprocessingEfficiency;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TypeId typeID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moon_id")
    private Moon moon;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="solar_system_id")
    private SolarSystem solarSystem;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private UniqueName name;

}

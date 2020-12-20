package com.kaldie.eveindustry.repository.universe;

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
    
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id")
    private UniqueName name;

    @Override
    public boolean equals(Object other) {
        boolean isEquals = true;
        if( other != null && other.getClass() == NPCStation.class) {
            NPCStation otherStation = (NPCStation) other;
            isEquals = otherStation.id == this.id;
            isEquals = isEquals && this.graphicID == otherStation.getGraphicID();
            isEquals = isEquals && this.isConquerable == otherStation.getIsConquerable();

            isEquals = isEquals && this.ownerID == otherStation.getOwnerID();
            isEquals = isEquals && this.reprocessingEfficiency == otherStation.getReprocessingEfficiency();
            isEquals = isEquals && this.typeID.getId() == otherStation.getTypeID().getId();
            isEquals = isEquals && this.moon.getId() == otherStation.getMoon().getId();
            isEquals = isEquals && this.name.getItemID().equals(otherStation.getName().getItemID());
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

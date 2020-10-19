package com.kaldie.eveindustry.Repository.TypeID;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TypeId implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Masteries masteries;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TanslatedString name;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TanslatedString description;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Traits traits;
    
    private float mass;
    private boolean published;
    private float volume;
    private float radius;


    private String sofFactionName;
    private float capacity;
    private int portionSize;

    private long groupID;
    private long graphicID;
    private long soundID;
    private long iconID;
    private long raceID;
    private Long basePrice;
    private long marketGroupID;
    private long metaGroupID;
    private long variationParentTypeID;
    private long factionID;
    private long sofMaterialSetID;

    @JsonCreator
    public TypeId(int id) {
        this.id = Long.valueOf(id);
    }
}


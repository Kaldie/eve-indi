package com.kaldie.eveindustry.repository.type_id;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private TranslatedString name;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TranslatedString description;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Traits traits;
    
    private Float mass;
    private Boolean published;
    private Float volume;
    private Float radius;


    private String sofFactionName;
    private Float capacity;
    private Integer portionSize;

    private Long groupID;
    private Long graphicID;
    private Long soundID;
    private Long iconID;
    private Long raceID;
    private Long basePrice;
    private Long marketGroupID;
    private Long metaGroupID;
    private Long variationParentTypeID;
    private Long factionID;
    private Long sofMaterialSetID;

    @Transient
    static Logger logger = LoggerFactory.getLogger(TypeId.class);

    @JsonCreator
    public TypeId(int id) {
        this.id = Long.valueOf(id);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public boolean equals(Object other) {
        boolean isEquals = true;
        if( other != null && other.getClass() == TypeId.class) {
            TypeId otherType = (TypeId) other;
            isEquals = this.id.equals(otherType.id);
            isEquals = isEquals && isEqualProperty(this.groupID, otherType.groupID);
            isEquals = isEquals && isEqualProperty(this.graphicID, otherType.graphicID);
            isEquals = isEquals && isEqualProperty(this.soundID, otherType.soundID);
            isEquals = isEquals && isEqualProperty(this.iconID, otherType.iconID);
            isEquals = isEquals && isEqualProperty(this.raceID, otherType.raceID);
            isEquals = isEquals && isEqualProperty(this.marketGroupID, otherType.marketGroupID);
            isEquals = isEquals && isEqualProperty(this.metaGroupID, otherType.metaGroupID);
            isEquals = isEquals && isEqualProperty(this.variationParentTypeID, otherType.variationParentTypeID);
            isEquals = isEquals && isEqualProperty(this.factionID, otherType.factionID);
            isEquals = isEquals && isEqualProperty(this.sofMaterialSetID, otherType.sofMaterialSetID);
            isEquals = isEquals && isEqualProperty(this.mass, otherType.mass);
            isEquals = isEquals && isEqualProperty(this.published, otherType.published);
            isEquals = isEquals && isEqualProperty(this.volume, otherType.volume);
            isEquals = isEquals && isEqualProperty(this.radius, otherType.radius);
            isEquals = isEquals && isEqualProperty(this.sofFactionName, otherType.sofFactionName);
            isEquals = isEquals && isEqualProperty(this.capacity, otherType.capacity);
            isEquals = isEquals && isEqualProperty(this.portionSize, otherType.portionSize);
        } else {
            isEquals = false;
        }
        return isEquals;
    }

    private <T> boolean  isEqualProperty(T first, T second) {
        Boolean equals = true;
        if ( first != null && second != null) {
            equals = first.equals(second);
        } else if (second != null || first != null) {
            equals = false;
        }
        return equals;
    }
}


package com.kaldie.eveindustry.repository.bluePrint;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaldie.eveindustry.repository.type_id.TypeId;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Blueprint {
    @Id
    private long blueprintTypeID;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Activities activities;
    
    private int productionLimit;

    public TypeId getProducedID() {
        Activity manufacturing = activities.getManufacturing();
        // check if the blueprint has any manufactering
        if (manufacturing == null) return null;

        // check if there is only one outcome
        Material product = manufacturing.getProducts().get(0);

        return product != null ? product.getTypeID() : null;
    }
}
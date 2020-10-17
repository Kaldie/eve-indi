package com.kaldie.eveindustry.Repository.BluePrint;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    public Long getProducedID() {
        Activity manufacturing = activities.getManufacturing();
        // check if the blueprint has any manufactering
        if (manufacturing == null) return -1L;

        // check if there is only one outcome
        Material product = manufacturing.getProducts().get(1);

        return product != null ? product.getTypeID() : 0;
    }
}
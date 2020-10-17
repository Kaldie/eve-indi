package com.kaldie.eveindustry.Repository.Zkillboard;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaldie.eveindustry.Repository.TypeID.TypeId;

import lombok.Data;

@Data
@Entity
public class Item implements Serializable {
    static final long serialVersionUID = 1l;
    
    private int flag;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @JsonProperty("item_type_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private TypeId itemId;
    
    @JsonProperty("quantity_dropped")
    private long dropped;

    @JsonProperty("quantity_destroyed")
    private long destroyed;
    
    private int singleton;
}

package com.kaldie.eveindustry.repository.blueprint;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.kaldie.eveindustry.repository.type_id.TypeId;

import lombok.Data;

@Data
@Entity(name = "blueprint_materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeId")
    private TypeId typeID;

    private int quantity;
    private float probability = 1;
}

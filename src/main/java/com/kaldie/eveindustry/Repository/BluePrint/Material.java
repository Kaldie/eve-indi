package com.kaldie.eveindustry.Repository.BluePrint;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity(name = "blueprint_materials")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long typeID;
    private int quantity;
    private float probability = 1;
}

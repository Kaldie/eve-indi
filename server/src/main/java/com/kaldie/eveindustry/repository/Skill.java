package com.kaldie.eveindustry.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Skill {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private int level;
    private long typeID;
}

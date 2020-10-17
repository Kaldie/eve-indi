package com.kaldie.eveindustry.Repository.BluePrint;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaldie.eveindustry.Repository.Skill;

import lombok.Data;


@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JsonAlias("materials")
    private List<Material> requiredMaterials;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Material> products;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Skill> skills;

    private int time;
}

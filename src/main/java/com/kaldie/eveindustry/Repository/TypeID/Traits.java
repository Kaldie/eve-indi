package com.kaldie.eveindustry.Repository.TypeID;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Traits implements Serializable {
    
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long iconID;
    
    // @OneToMany
    // private List<Bonus> miscBonuses;

    // @OneToMany
    // private List<Bonus> roleBonuses;

}

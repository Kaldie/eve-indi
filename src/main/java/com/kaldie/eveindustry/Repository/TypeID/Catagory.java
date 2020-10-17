package com.kaldie.eveindustry.Repository.TypeID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Catagory {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    private TanslatedString name;
    
    private boolean published;
}



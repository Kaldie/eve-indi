package com.kaldie.eveindustry.Repository.TypeID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bonus {
    private float bonusAmount;
    
    @OneToOne(cascade = CascadeType.ALL)
    private TanslatedString bonusText;
    
    private int importance;
    private boolean isPositive;
    
    @Id
    private Long unitID;
}

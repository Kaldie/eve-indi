package com.kaldie.eveindustry.repository.type_id;

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
    private TranslatedString bonusText;
    
    private int importance;
    private boolean isPositive;
    
    @Id
    private Long unitID;
}

package com.kaldie.eveindustry.repository.groups;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaldie.eveindustry.repository.type_id.TranslatedString;

import lombok.Data;

@Data
@Entity(name = "market_group")
public class MarketGroup {

    @Id
    private Long id;


    @JsonProperty("descriptionID")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="description")
    private TranslatedString description;
    
    @JsonProperty("nameID")
    @JoinColumn(name = "name")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private TranslatedString name;

    @Column(name="has_types")
    private Boolean hasTypes;

    @Column(name="icon_id")
    private Long iconID;
    
    @JsonProperty("parentGroupID")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parent_group")
    private MarketGroup parentGroup;

    public MarketGroup(){}
    
    public MarketGroup(Integer id) {
        this.id = Long.valueOf(id);
    }
    
}

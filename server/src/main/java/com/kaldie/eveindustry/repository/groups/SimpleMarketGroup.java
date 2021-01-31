package com.kaldie.eveindustry.repository.groups;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.kaldie.eveindustry.repository.type_id.TranslatedString;

import lombok.Data;

@Data
@Entity
@Table(name="market_group")
public class SimpleMarketGroup {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, 
        cascade = CascadeType.PERSIST)
    @JoinColumn(name="description")
    private TranslatedString description;
    
    @JoinColumn(name = "name")
    @OneToOne(fetch = FetchType.LAZY, 
        cascade = CascadeType.PERSIST)
    private TranslatedString name;

    @Column(name="has_types")
    private Boolean hasTypes;

    @Column(name="icon_id")
    private Long iconID;

    @Column(name = "parent_group")
    private Long parentGroupID;

    public SimpleMarketGroup(){}
    
    public SimpleMarketGroup(Integer id) {
        this.id = Long.valueOf(id);
    }
    
}

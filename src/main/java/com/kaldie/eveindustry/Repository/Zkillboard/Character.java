package com.kaldie.eveindustry.Repository.Zkillboard;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.kaldie.eveindustry.Repository.TypeID.TypeId;

import lombok.Data;

@Data
@Entity
public class Character implements Serializable {
    static final long serialVersionUID = 1l;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long character_id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(nullable=true)),
        @AttributeOverride(name="z", column=@Column(nullable=true)),
        @AttributeOverride(name="y", column=@Column(nullable=true))
    })
    private Position position;

    private long alliance_id;
    private long faction_id;
    private long corporation_id;
    private long damage_done;
    private boolean final_blow;
    private long security_status;
    
    @OneToOne(cascade = CascadeType.DETACH,  optional = true, fetch = FetchType.LAZY )
    private TypeId ship_type_id;
    
    @OneToOne(cascade = CascadeType.DETACH, optional = true, fetch = FetchType.LAZY )
    private TypeId weapon_type_id;

    private long damage_taken;
}

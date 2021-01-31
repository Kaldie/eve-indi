package com.kaldie.eveindustry.repository.groups;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.kaldie.eveindustry.repository.type_id.TranslatedString;

import lombok.Data;

@Data
@Entity(name="type_group")
public class Group {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="name")
    private TranslatedString name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category")
    private Catagory categoryID;
        
    private Boolean anchorable;
    private Boolean anchored;
    private Boolean fittableNonSingleton;

    @Column(name="icon")
    private Long iconID;

    private Boolean published;
    private Boolean useBasePrice;
}

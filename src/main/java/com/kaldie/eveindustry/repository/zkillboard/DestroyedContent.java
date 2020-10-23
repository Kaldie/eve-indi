package com.kaldie.eveindustry.repository.zkillboard;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kaldie.eveindustry.repository.type_id.TypeId;

import lombok.Data;


@Data
@Entity
public class DestroyedContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeId")
    private TypeId typeId;
    
    private Date date;
    private Long quentity;

    public DestroyedContent(TypeId type, Date date, Long quantity) {
        this.typeId = type;
        this.date = date;
        this.quentity = quantity;
    }
    
}

package com.kaldie.eveindustry.Repository.Zkillboard;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kaldie.eveindustry.Repository.TypeID.TypeId;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@Entity
public class DestroyedContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToOne(fetch = FetchType.LAZY)
    private TypeId typeId;
    
    private Date date;
    private Long quentity;

    public DestroyedContent(TypeId type, Date date, Long quantity) {
        this.typeId = type;
        this.date = date;
        this.quentity = quantity;

    }
    
}

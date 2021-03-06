package com.kaldie.eveindustry.repository.universe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class UniqueName {
    
    public UniqueName() {}

    public UniqueName(Long id) {
        this.itemID = id;
    }

    @Id
    @Column(name = "id")
    private Long itemID;

    @Column(name="name")
    private String itemName;
}

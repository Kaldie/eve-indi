package com.kaldie.eveindustry.repository.universe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
public class UniqueName {
    @Id
    @Column(name = "id")
    private Long itemID;

    @Column(name="name")
    private String itemName;

    @Version
    private Short version;
}

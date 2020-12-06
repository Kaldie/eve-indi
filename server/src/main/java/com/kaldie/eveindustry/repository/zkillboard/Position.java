package com.kaldie.eveindustry.repository.zkillboard;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Position implements Serializable {
    static final long serialVersionUID = 1l;
    private double x;
    private double y;
    private double z;
}

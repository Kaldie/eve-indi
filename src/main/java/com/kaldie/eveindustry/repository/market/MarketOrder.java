package com.kaldie.eveindustry.repository.market;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.kaldie.eveindustry.repository.type_id.TypeId;
import com.kaldie.eveindustry.repository.universe.SolarSystem;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import lombok.Data;

@Entity
@Table(name="market_order")
@Data
public class MarketOrder {

    @Id
    @Column(name="id")
    private long orderId;
    private int duration;
    private Boolean isBuy;
    private long location;
    private int minVolume;
    private double price;
    private String range;
    private Integer volumeRemain;
    private Integer volumeTotal;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime issued;

    @OneToOne(fetch = FetchType.LAZY)
    private SolarSystem systemId;

    @OneToOne(fetch = FetchType.LAZY)
    private TypeId typeId;

}

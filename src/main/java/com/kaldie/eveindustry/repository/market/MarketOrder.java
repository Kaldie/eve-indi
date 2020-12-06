package com.kaldie.eveindustry.repository.market;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.kaldie.eveindustry.repository.type_id.TypeId;
import com.kaldie.eveindustry.repository.universe.SolarSystem;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import lombok.Data;
import net.troja.eve.esi.model.MarketOrdersResponse;

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
    @JoinColumn(name = "system_id", referencedColumnName = "id")
    private SolarSystem systemId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TypeId typeId;

    public boolean equals(Object response) {
        if (response == null) return false;

        if ()

        boolean isEqual = true;
        isEqual = response.getOrderId() == orderId;
        isEqual = isEqual && response.getDuration().equals(duration);
        isEqual = isEqual && response.getIsBuyOrder().equals(isBuy);
        isEqual = isEqual && response.getLocationId().equals(location);
        isEqual = isEqual && response.getMinVolume().equals(minVolume);
        isEqual = isEqual && response.getPrice().equals(price);
        isEqual = isEqual && response.getRangeString().equals(range);
        isEqual = isEqual && response.getVolumeRemain().equals(volumeRemain);
        isEqual = isEqual && response.getVolumeTotal().equals(volumeTotal);
        isEqual = isEqual && response.getIssued().equals(Adaptor.from(issued));
        isEqual = isEqual && response.getSystemId().equals(systemId != null ? systemId.getSolarSystemID())
        isEqual = isEqual && response.getIssued().equals(Adaptor.from(issued));
        isEqual = isEqual && response.getIssued().equals(Adaptor.from(issued));
        

        return isEqual;
    }

}

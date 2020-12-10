package com.kaldie.eveindustry.repository.market;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaldie.eveindustry.repository.type_id.TypeId;
import com.kaldie.eveindustry.repository.universe.SolarSystem;
import com.kaldie.eveindustry.serializers.MarketOrderSerializer;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;
import net.troja.eve.esi.model.MarketOrdersResponse;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@Entity
@Table(name="market_order")
@Data
@JsonSerialize(using = MarketOrderSerializer.class)
public class MarketOrder {
    
    @Transient
    @JsonIgnore
    private Logger logger = LoggerFactory.getLogger(MarketOrder.class);

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

    public boolean almostEqualToResponse(MarketOrdersResponse response) {
            boolean isEqual = true;
            isEqual = response.getOrderId() == orderId;
            logger.debug("{}",isEqual);
            isEqual = isEqual && response.getDuration().equals(duration);
            logger.debug("{}",isEqual);
            isEqual = isEqual && response.getIsBuyOrder().equals(isBuy);
            logger.debug("{}",isEqual);
            isEqual = isEqual && response.getLocationId().equals(location);
            logger.debug("{}",isEqual);
            isEqual = isEqual && response.getMinVolume().equals(minVolume);
            logger.debug("{}",isEqual);
            isEqual = isEqual && response.getPrice().equals(price);
            logger.debug("{}",isEqual);
            isEqual = isEqual && response.getRangeString().equals(range);
            logger.debug("{}",isEqual);
            isEqual = isEqual && response.getVolumeRemain().equals(volumeRemain);
            logger.debug("{}",isEqual);
            isEqual = isEqual && response.getVolumeTotal().equals(volumeTotal);
            logger.debug("{}",isEqual);
            Long responseEpoch = response.getIssued().toInstant().toEpochMilli();
            Long orderEpoch = issued.getMillis();
            isEqual = isEqual && responseEpoch.equals(orderEpoch);
            logger.debug("{}: {} ?= {}",isEqual, responseEpoch, orderEpoch);

            return isEqual;
    }
}

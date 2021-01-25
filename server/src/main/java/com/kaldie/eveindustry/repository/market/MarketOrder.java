package com.kaldie.eveindustry.repository.market;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaldie.eveindustry.repository.type_id.TypeId;
import com.kaldie.eveindustry.repository.universe.SolarSystem;
import com.kaldie.eveindustry.serializers.MarketOrderSerializer;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;
import net.troja.eve.esi.model.MarketOrdersResponse;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SqlResultSetMapping(
    name="MarketItemsStates",
    classes={
      @ConstructorResult(
        targetClass=MarketItemState.class,
        columns={
          @ColumnResult(name="type_id", type=Long.class),
          @ColumnResult(name="buy_price", type=Float.class),
          @ColumnResult(name="sell_price", type=Float.class),
          @ColumnResult(name="margin", type=Float.class),
          @ColumnResult(name="total_isk", type=Float.class),
          @ColumnResult(name="total_isk_bought", type=Float.class),
          @ColumnResult(name="total_isk_sold", type=Float.class),
          @ColumnResult(name="total_buy_order", type=Integer.class),
          @ColumnResult(name="total_sell_order", type=Integer.class),
          @ColumnResult(name="avg_buy", type=Float.class),
          @ColumnResult(name="avg_sell", type=Float.class)
        })
    })

@NamedNativeQuery(name = "MarketItemState.findOrderVolumes",
    query = "" +
    " select type_id, buy_price, sell_price, margin, total_isk, total_isk_bought, " +
    " total_isk_sold , total_buy_order, " +
    " total_sell_order , avg_buy , avg_sell " +
    " from find_market_around(:system_name, :range) ",
    resultSetMapping = "MarketItemsStates"
)


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
    @JoinColumn(name = "system_id")
    private SolarSystem systemId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private TypeId typeId;

    public boolean almostEqualToResponse(MarketOrdersResponse response) {
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
            isEqual = isEqual && response.getTypeId().equals(typeId.getId().intValue());
            isEqual = isEqual && response.getSystemId().equals(systemId.getSolarSystemID().intValue());
            Long responseEpoch = response.getIssued().toInstant().toEpochMilli();
            Long orderEpoch = issued.getMillis();
            isEqual = isEqual && responseEpoch.equals(orderEpoch);
            logger.debug("{}: {} ?= {}",isEqual, responseEpoch, orderEpoch);

            return isEqual;
    }
}

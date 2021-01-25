package com.kaldie.eveindustry.repository.market;

import javax.annotation.concurrent.Immutable;
import javax.persistence.Entity;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import org.hibernate.annotations.Subselect;

import javax.persistence.EntityResult;

import lombok.Data;

@Data
@SqlResultSetMapping(
    name = "find_order_volumes",
    entities=@EntityResult(
        entityClass=MarketInformation.class,
        fields = {
                @FieldResult(name="typeId", column = "type_id"),
                @FieldResult(name="totalIsk", column = "total_isk"),
                @FieldResult(name="totalIskBought", column = "total_isk_bought"),
                @FieldResult(name="totalIskSold", column = "total_isk_sold"),
                @FieldResult(name="totalBuyOrders", column = "total_buy_order"),
                @FieldResult(name="totalSellOrders", column = "total_sell_order"),
                @FieldResult(name="averageBuyPrice", column = "avg_buy"),
                @FieldResult(name="averageSellPrice", column = "avg_sell")
            }
    )
)
@NamedNativeQuery(name = "find_order_volumes",
    query = "" +
    " select type_id, total_isk, total_isk_bought, " +
    " total_isk_sold , total_buy_order, " +
    " total_sell_order , avg_buy , avg_sell " +
    " from find_order_volumes(:system_name, :range) ",
    resultSetMapping = "getRequiredMaterials"
)
public class MarketInformation {
    @Id
    private Long id;
    private Long typeId;
    private Float totalIsk;
    private Float totalIskBought;
    private Float totalIskSold;
    private Integer totalBuyOrders;
    private Integer totalSellOrders;
    private Float averageBuyPrice;
    private Float averageSellPrice;   
}

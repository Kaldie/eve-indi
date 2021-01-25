package com.kaldie.eveindustry.repository.market;

import lombok.Data;

@Data
public class MarketItemState {
    private final Long typeId;
    private final Float buyPrice;
    private final Float sellPrice;
    private final Float margin;
    private final Float totalIsk;
    private final Float totalIskBought;
    private final Float totalIskSold;
    private final Integer totalBuyOrder;
    private final Integer totalSellOrder;
    private final Float avgBuy;
    private final Float avgSell;
}

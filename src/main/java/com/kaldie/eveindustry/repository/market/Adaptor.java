package com.kaldie.eveindustry.repository.market;

import com.kaldie.eveindustry.repository.type_id.TypeId;

import net.troja.eve.esi.model.MarketOrdersResponse;
import net.troja.eve.esi.model.MarketOrdersResponse.RangeEnum;

public final class Adaptor {

    private Adaptor() {
        throw new AssertionError();
    }

    public static java.time.OffsetDateTime from(org.joda.time.DateTime jodaTime) {
        java.time.Instant instant = java.time.Instant.ofEpochMilli(jodaTime.getMillis());
        return java.time.OffsetDateTime.ofInstant(instant, java.time.ZoneId.of(jodaTime.getZone().getID()));
    }

    public static org.joda.time.DateTime from(java.time.OffsetDateTime timeOffset) {
        return new org.joda.time.DateTime(timeOffset.toInstant().toEpochMilli(),
                org.joda.time.DateTimeZone.forID(timeOffset.getOffset().getId()));
    }

    public static MarketOrdersResponse from(MarketOrder marketOrder) {
        int typeId = marketOrder.getTypeId() != null ? marketOrder.getTypeId().getId().intValue() : null;

        MarketOrdersResponse response = new MarketOrdersResponse();
        response.duration(marketOrder.getDuration()).isBuyOrder(marketOrder.getIsBuy())
                .issued(Adaptor.from(marketOrder.getIssued())).locationId(marketOrder.getLocation())
                .minVolume(marketOrder.getMinVolume()).orderId(marketOrder.getOrderId()).price(marketOrder.getPrice())
                .range(RangeEnum.fromValue(marketOrder.getRange())).typeId(typeId)
                .volumeRemain(marketOrder.getVolumeRemain()).volumeTotal(marketOrder.getVolumeTotal());
        return response;
    }

    public static MarketOrder from(MarketOrdersResponse marketOrder) {

        MarketOrder response = new MarketOrder();
        response.setDuration(marketOrder.getDuration());
        response.setIsBuy(marketOrder.getIsBuyOrder());
        response.setIssued(Adaptor.from(marketOrder.getIssued()));
        response.setLocation( marketOrder.getLocationId());
        response.setMinVolume(marketOrder.getMinVolume());
        response.setOrderId(marketOrder.getOrderId());
        response.setPrice(marketOrder.getPrice());
        response.setRange(marketOrder.getRange().getValue());
        response.setTypeId(new TypeId(marketOrder.getTypeId()));
        response.setVolumeRemain(marketOrder.getVolumeRemain());
        response.setVolumeTotal(marketOrder.getVolumeTotal());
        return response;
    }

}

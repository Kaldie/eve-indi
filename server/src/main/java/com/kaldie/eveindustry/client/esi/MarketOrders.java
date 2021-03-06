package com.kaldie.eveindustry.client.esi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;

import lombok.Data;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.ApiResponse;
import net.troja.eve.esi.api.MarketApi;
import net.troja.eve.esi.model.MarketHistoryResponse;
import net.troja.eve.esi.model.MarketOrdersResponse;

@Data
public class MarketOrders {

    static Logger logger = LoggerFactory.getLogger(MarketOrders.class);

    private MarketOrders() {
    }

    public static List<MarketHistoryResponse> getRegionalItemHistory(long typeId, long regionalId) throws ApiException {
        logger.info("requesting Regional history for item: {}, region:{}", typeId, regionalId);
        return new MarketApi().getMarketsRegionIdHistory((int) regionalId, (int) typeId, null, null);
    }

    public static List<MarketOrdersResponse> getRegionalItemOrders(@Nullable Long typeId, Long regionalId) throws ApiException {
        MarketApi api = new MarketApi();
        List<MarketOrdersResponse> orders = new ArrayList<MarketOrdersResponse>() {
            private static final long serialVersionUID = -6262036476817492616L;
        };
        int currentPage = 1;
        int pages = Integer.MAX_VALUE;
        int allowedFailures = 10;
        ApiResponse<List<MarketOrdersResponse>> response;

        do {
            logger.debug("Requesting market info with regional id: {} and typeId: {} and page {}", 
                regionalId, typeId, currentPage);
            try {
                response = api.getMarketsRegionIdOrdersWithHttpInfo(
                    "all", regionalId.intValue(), null, null, currentPage, 
                    typeId != null ? typeId.intValue() : null);

                logger.debug("recieved a response: {}", response);
                orders.addAll(response.getData());
                pages = Integer.parseInt(response.getHeaders().get("x-pages").get(0));
            } catch( Exception err) {
                logger.warn("Error while requesting market orders");
                logger.warn("Error: {}", err.toString());
                --allowedFailures;
                continue;
            }
            logger.debug("Number of pages here: {}", pages);
            ++currentPage;
        } while (currentPage <= pages && allowedFailures > 0);

        logger.info("Found {} orders", orders.size());
        return orders;
    }

    public static List<MarketOrdersResponse> getRegionalItemOrders(long regionalId) throws ApiException {
        Long empty = null;
        return getRegionalItemOrders(empty, regionalId);
    }

    public static void exampleParsing(List<MarketOrdersResponse> orders) {
        Predicate<MarketOrdersResponse> isBuyOrder = MarketOrdersResponse::getIsBuyOrder;
        Pair<Double, Double> xx = orders.stream().parallel().filter(isBuyOrder.negate()).reduce(
                Pair.of(Double.MIN_VALUE, Double.MAX_VALUE),
                (pair, order) -> Pair.of(pair.getFirst() < order.getPrice() ? order.getPrice() : pair.getFirst(),
                        pair.getSecond() > order.getPrice() ? order.getPrice() : pair.getSecond()),
                (pair1, pair2) -> Pair.of(pair1.getFirst() < pair2.getFirst() ? pair2.getFirst() : pair1.getFirst(),
                        pair1.getSecond() > pair2.getSecond() ? pair2.getSecond() : pair1.getSecond()));
        logger.info("min: {}, max: {}", xx.getFirst(), xx.getSecond());
    }

}
package com.kaldie.eveindustry.controller;

import java.util.List;
import java.util.Optional;

import com.kaldie.eveindustry.repository.market.MarketItemState;
import com.kaldie.eveindustry.repository.market.MarketOrder;
import com.kaldie.eveindustry.repository.market.MarketOrderRepository;
import com.kaldie.eveindustry.repository.universe.SolarSystem;
import com.kaldie.eveindustry.repository.universe.SolarSystemRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Value;


@Controller
@Value
public class MarketOrderController {

    private final MarketOrderRepository marketOrderRepository;
    private final SolarSystemRepository solarSystemRepository;
    private Logger logger = LoggerFactory.getLogger(MarketOrderController.class);

    @PostMapping("/market")
    @ResponseBody
    public Page<MarketOrder> marketOrders(Pageable pageable, @RequestParam Long systemId) {
        Optional<SolarSystem> system = solarSystemRepository.findById(systemId);
        logger.info("system: {}", system.get().getName().getItemName());

        Page<MarketOrder> page = marketOrderRepository.getMarketOrderBySystemId(system.get(), pageable);
        logger.info("first entry in list: {}", page.toList().get(0).getTypeId().getName().getEn());
        return page;

    }

    @PostMapping("/marketState")
    @ResponseBody
    public List<MarketItemState> getMaketItemStates(@RequestBody MarketAroundRequest marketAroundRequest ) {
        return marketOrderRepository.findMarketAround(
            marketAroundRequest.getSystemName(), marketAroundRequest.getRange());
    }
    
}

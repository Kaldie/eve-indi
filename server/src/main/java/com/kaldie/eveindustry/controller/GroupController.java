package com.kaldie.eveindustry.controller;

import com.kaldie.eveindustry.repository.groups.MarketGroup;
import com.kaldie.eveindustry.repository.groups.MarketGroupLineage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.Data;

@Data
@Controller
public class GroupController {
    
    private final com.kaldie.eveindustry.repository.groups.MarketGroupRepository marketGroupRepository;

    @GetMapping("/MarkgetGroup")
    @ResponseBody
    public Iterable<MarketGroup> markgetGroups() {
        return marketGroupRepository.findAllWithName();
    }

    @GetMapping("/MarkgetGroupAndChildren")
    @ResponseBody
    public Iterable<MarketGroupLineage> markgetGroupAndChildren(@RequestBody Long id ) {
        return marketGroupRepository.findMarketGroupLineage(id);
    }



}

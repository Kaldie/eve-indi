package com.kaldie.eveindustry.repository.groups;

import lombok.Value;

@Value
public class MarketGroupLineage {
    private Long id;
    private String name;
    private Long parent;
    private String lineage;
}

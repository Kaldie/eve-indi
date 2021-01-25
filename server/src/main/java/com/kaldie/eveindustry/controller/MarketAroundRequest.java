package com.kaldie.eveindustry.controller;

import lombok.Value;

@Value
public class MarketAroundRequest {
    private Integer range;
    private String systemName;
}

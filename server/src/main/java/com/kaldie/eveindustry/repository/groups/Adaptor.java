package com.kaldie.eveindustry.repository.groups;

public class Adaptor {
    private Adaptor(){}

    public static SimpleMarketGroup from(MarketGroup marketGroup) {
        SimpleMarketGroup simpleGroup = new SimpleMarketGroup();
        simpleGroup.setId(marketGroup.getId());
        simpleGroup.setDescription(marketGroup.getDescription());
        simpleGroup.setName(marketGroup.getName());
        simpleGroup.setHasTypes(marketGroup.getHasTypes());
        simpleGroup.setIconID(marketGroup.getIconID());
        if (marketGroup.getParentGroup() != null) {
            simpleGroup.setParentGroupID(marketGroup.getParentGroup().getId());
        }
        return simpleGroup;
    }
}

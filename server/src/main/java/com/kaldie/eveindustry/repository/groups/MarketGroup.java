package com.kaldie.eveindustry.repository.groups;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaldie.eveindustry.repository.type_id.TranslatedString;

import lombok.Data;

@SqlResultSetMapping(
    name="MarketGroupLineage",
    classes={
        @ConstructorResult(
        targetClass=MarketGroupLineage.class,
        columns={
            @ColumnResult(name="id", type=Long.class),
            @ColumnResult(name="name", type=String.class),
            @ColumnResult(name="parent", type=Long.class),
            @ColumnResult(name="lineage", type=String.class)
        }
    )}
)

@NamedNativeQuery(name = "MarketGroupLineage.findMarketGroupLineage",
    query = "SELECT * from get_market_group_lineage(:id)",
    resultSetMapping = "MarketGroupLineage"
)

@Data
@Entity(name = "market_group")
public class MarketGroup {

    @Id
    private Long id;

    @JsonProperty("descriptionID")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="description")
    private TranslatedString description;
    
    @JsonProperty("nameID")
    @JoinColumn(name = "name")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private TranslatedString name;

    @Column(name="has_types")
    private Boolean hasTypes;

    @Column(name="icon_id")
    private Long iconID;
    
    @JsonProperty("parentGroupID")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parent_group")
    private MarketGroup parentGroup;

    @OneToMany(mappedBy="parentGroup")
    private List<MarketGroup> children;

    public MarketGroup(){}
    
    public MarketGroup(Integer id) {
        this.id = Long.valueOf(id);
    }
    
}

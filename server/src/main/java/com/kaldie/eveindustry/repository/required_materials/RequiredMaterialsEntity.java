package com.kaldie.eveindustry.repository.required_materials;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;

import com.kaldie.eveindustry.repository.type_id.TypeId;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor // we use data so no all arg constructor is created by default
@Entity
@SqlResultSetMapping(
    name = "getRequiredMaterials",
    entities=@EntityResult(
        entityClass=RequiredMaterialsEntity.class,
        fields = {
                @FieldResult(name="unusedId", column = "unused_id"),
                @FieldResult(name="requiredMaterial", column = "requiredMaterialID"),
                @FieldResult(name="requiredMaterialName", column = "requiredMaterialName"),
                @FieldResult(name="relativeDepth", column = "relativeDepth"),
                @FieldResult(name="quantity", column = "quantity")
            }
    )
)
@NamedNativeQuery(name = "getRequiredMaterials",
    query = "" +
    " with cte_lala as ( " +
    "   select " +
    "       requiredMaterialID, " +
    "       requiredMaterialName, " +
    "       quantity, " +
    "       outcomeID, " +
    "       wantedItem, " +
    "       1 as relativeDepth " +
    "   from " +
    "       RequiredMaterials " +
    "   where outcomeID = :id " +
    "   union all " +
    "   select " +
    "       rm.requiredMaterialID, " +
    "       rm.requiredMaterialName, " +
    "       rm.quantity * cte_lala.quantity, " +
    "       rm.outcomeID, " +
    "       rm.wantedItem," +
    "       relativeDepth + 1" +
    "   from RequiredMaterials as rm, cte_lala " +
    "   where cte_lala.requiredMaterialID = rm.outcomeID" +
    " ) " +
    " select " +
    "    ROW_NUMBER() OVER(order by requiredMaterialID, relativeDepth) as unused_id,  " +
    "    requiredMaterialID, " +
    "    requiredMaterialName, " +
    "    relativeDepth, " +
    "    sum(quantity) as quantity " +
    " from cte_lala " +
    " group by requiredMaterialID, requiredMaterialName, relativeDepth ",
    resultSetMapping = "getRequiredMaterials"
)
@Immutable
@Subselect("select * from RequiredMiniralMaterialsEntity")
public class RequiredMaterialsEntity implements Serializable {

    static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long unusedId;

    @OneToOne
    private TypeId requiredMaterial;

    private String requiredMaterialName;
    private int relativeDepth;
    private long quantity;

}



package com.kaldie.eveindustry.repository.required_materials;

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

@Data
@AllArgsConstructor // we use data so no all arg constructor is created by default
@Entity
@SqlResultSetMapping(
    name = "getRequiredMinirals",
    entities=@EntityResult(
        entityClass=RequiredMaterialsEntity.class,
        fields = {
                @FieldResult(name="unusedId", column = "id"),
                @FieldResult(name="requiredMaterial", column = "requiredMaterialID"),
                @FieldResult(name="requiredMaterialName", column = "requiredMaterialName"),
                @FieldResult(name="quantity", column = "quantity")
            }
    )
)
@NamedNativeQuery(name = "getRequiredMinirals",
    query = "" +
        " with cte_lala as ( " +
        " select " +
        "    requiredMaterialID, " +
        "    requiredMaterialName, " +
        "    quantity, " +
        "    outcomeID, " +
        "    wantedItem," +
        "    1 as relativeDepth " +
        " from " +
        "    RequiredMaterials " +
        " where outcomeID = :id " +
        " union all " +
        " select " +
        "    rm.requiredMaterialID, " +
        "    rm.requiredMaterialName, " +
        "    rm.quantity * cte_lala.quantity, " +
        "    rm.outcomeID, " +
        "    rm.wantedItem," +
        "    relativeDepth + 1" +
        " from RequiredMaterials as rm, cte_lala " +
        " where cte_lala.requiredMaterialID = rm.outcomeID" +
        " ) " +
        " select ROW_NUMBER() OVER(order by requiredMaterialID, relativeDepth)  as id, requiredMaterialID, requiredMaterialName, SUM(quantity) as quantity" +
        " from cte_lala " +
        " join ( " +
        "    select ts.en, ti.id" +
        "    from type_id ti " +
        "    join tanslated_string ts on ts.id = ti.name_id " +
        "    left join (" +
        "        select * " +
        "        from blueprint_materials bm " +
        "        join activity_products ap on ap.products_id = bm.id ) as producedMaterials on producedMaterials.typeid = ti.id " +
        "    where " +
        "        producedMaterials.typeid is null and ti.published = 1) as rawMaterials on rawMaterials.id =  requiredMaterialID" +
        " group by requiredMaterialID, requiredMaterialName",
    resultClass = RequiredMiniralMaterialsEntity.class,
    resultSetMapping = "getRequiredMinirals")
@Immutable
@Subselect("select * from RequiredMiniralMaterialsEntity")
public class RequiredMiniralMaterialsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @OneToOne
    private TypeId requiredMaterialID;
    
    private String requiredMaterialName;
    private long quantity;
}

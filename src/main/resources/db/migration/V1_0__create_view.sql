create or alter view RequiredMaterials as 
SELECT 
		ti_required.id as requiredMaterialID, 
		ts_required.en as requiredMaterialName, 
		bm2.quantity, 
		fa.typeid as outcomeID, 
		ts_wanted.en as wantedItem from (
	select a2.id as activityID, bm.typeid from blueprint b 
	join activities a on b.activities_id =a.id 
	join activity a2 on a.manufacturing_id =a2.id 
	join activity_products ap on ap.activity_id = a2.id 
	join blueprint_materials bm on ap.products_id = bm.id
	) as fa
join activity_required_materials arm on fa.activityID = arm.activity_id
join blueprint_materials bm2 on arm.required_materials_id = bm2.id
join type_id ti_required on ti_required.id = bm2.typeid
join type_id ti_wanted on ti_wanted.id = fa.typeid
join tanslated_string ts_required on ts_required.id = ti_required.name_id
join tanslated_string ts_wanted on ts_wanted.id = ti_wanted.name_id;
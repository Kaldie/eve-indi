-- activity definition

-- Drop table

-- DROP TABLE activity GO

CREATE TABLE activity (
	id BIGINT IDENTITY(1,1) NOT NULL,
	[time] int NOT NULL,
	CONSTRAINT PK__activity PRIMARY KEY (id)
) GO


-- masteries definition

-- Drop table

-- DROP TABLE masteries GO

CREATE TABLE masteries (
	id BIGINT IDENTITY(1,1) NOT NULL,
	CONSTRAINT PK__masteries PRIMARY KEY (id)
) GO


-- material definition

-- Drop table

-- DROP TABLE material GO

CREATE TABLE material (
	material_typeid BIGINT IDENTITY(1,1) NOT NULL,
	quantity BIGINT NULL,
	CONSTRAINT PK__material PRIMARY KEY (material_typeid)
) GO

-- skill definition

-- Drop table

-- DROP TABLE skill GO

CREATE TABLE skill (
	id BIGINT IDENTITY(1,1) NOT NULL,
	[level] int NOT NULL,
	typeid BIGINT NOT NULL,
	CONSTRAINT PK__skill PRIMARY KEY (id)
) GO


-- translated_string definition

-- Drop table

-- DROP TABLE translated_string GO

CREATE TABLE translated_string (
	id BIGINT IDENTITY(1,1) NOT NULL,
	de VARCHAR(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	en VARCHAR(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	es nVARCHAR(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	fr VARCHAR(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	it nVARCHAR(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ja nVARCHAR(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ru nVARCHAR(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	zh nVARCHAR(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__tanslate PRIMARY KEY (id)
) GO


-- traits definition

-- Drop table

-- DROP TABLE traits GO

CREATE TABLE traits (
	id BIGINT IDENTITY(1,1) NOT NULL,
	iconid BIGINT NULL,
	CONSTRAINT PK__traits PRIMARY KEY (id)
) GO


-- type_material definition

-- Drop table

-- DROP TABLE type_material GO

CREATE TABLE type_material (
	id BIGINT IDENTITY(1,1) NOT NULL,
	CONSTRAINT PK__type_matial PRIMARY KEY (id)
) GO


-- activities definition

-- Drop table

-- DROP TABLE activities GO

CREATE TABLE activities (
	id BIGINT IDENTITY(1,1) NOT NULL,
	copying_id BIGINT NULL,
	invention_id BIGINT NULL,
	manufacturing_id BIGINT NULL,
	research_material_id BIGINT NULL,
	research_time_id BIGINT NULL,
	CONSTRAINT PK__activities PRIMARY KEY (id),
	CONSTRAINT FK_activies_manufacturing FOREIGN KEY (manufacturing_id) REFERENCES activity(id),
	CONSTRAINT FK68f70f2pg0nbwgquc07ste718 FOREIGN KEY (invention_id) REFERENCES activity(id),
	CONSTRAINT FK7rew2dqa7d7h65w99c2wrs4i FOREIGN KEY (research_time_id) REFERENCES activity(id),
	CONSTRAINT FKiv3g1u9rid1wfnqvhfhvlmw69 FOREIGN KEY (research_material_id) REFERENCES activity(id),
	CONSTRAINT FKjirl8t883h61tc7cdvkox1d9j FOREIGN KEY (copying_id) REFERENCES activity(id)
) GO


-- bonus definition

-- Drop table

-- DROP TABLE bonus GO

CREATE TABLE bonus (
	unitid BIGINT NOT NULL,
	bonus_amount FLOAT NOT NULL,
	importance int NOT NULL,
	is_positive BIT NOT NULL,
	bonus_text_id BIGINT NULL,
	CONSTRAINT PK__bonus PRIMARY KEY (unitid),
	CONSTRAINT FKke5tls94mrc5wm4yfwbwewy0a FOREIGN KEY (bonus_text_id) REFERENCES translated_string(id)
) GO


-- catagory definition

-- Drop table

-- DROP TABLE catagory GO

CREATE TABLE catagory (
	id BIGINT IDENTITY(1,1) NOT NULL,
	published BIT NOT NULL,
	name_id BIGINT NULL,
	CONSTRAINT PK__catagory PRIMARY KEY (id),
	CONSTRAINT FKqwtvmla6ip6a19kwgvq5dcbr6 FOREIGN KEY (name_id) REFERENCES translated_string(id)
) GO


-- masteries_first definition

-- Drop table

-- DROP TABLE masteries_first GO

CREATE TABLE masteries_first (
	masteries_id BIGINT NOT NULL,
	[first] BIGINT NULL,
	CONSTRAINT FKq02k5fxjx7wyqkkr186wigl5u FOREIGN KEY (masteries_id) REFERENCES masteries(id)
) GO


-- masteries_four definition

-- Drop table

-- DROP TABLE masteries_four GO

CREATE TABLE masteries_four (
	masteries_id BIGINT NOT NULL,
	four BIGINT NULL,
	CONSTRAINT FKpsgmqonuhp8drhyfnrlun4a0l FOREIGN KEY (masteries_id) REFERENCES masteries(id)
) GO


-- masteries_three definition

-- Drop table

-- DROP TABLE masteries_three GO

CREATE TABLE masteries_three (
	masteries_id BIGINT NOT NULL,
	three BIGINT NULL,
	CONSTRAINT FKkaoumog4o9kfwngt5jdi5x47s FOREIGN KEY (masteries_id) REFERENCES masteries(id)
) GO


-- masteries_two definition

-- Drop table

-- DROP TABLE masteries_two GO

CREATE TABLE masteries_two (
	masteries_id BIGINT NOT NULL,
	two BIGINT NULL,
	CONSTRAINT FKruvk0fu6hjv1k02hyya46g2fj FOREIGN KEY (masteries_id) REFERENCES masteries(id)
) GO


-- masteries_zero definition

-- Drop table

-- DROP TABLE masteries_zero GO

CREATE TABLE masteries_zero (
	masteries_id BIGINT NOT NULL,
	zero BIGINT NULL,
	CONSTRAINT FK69lt3mojktv0hmhuurga1fc6v FOREIGN KEY (masteries_id) REFERENCES masteries(id)
) GO


-- type_id definition

-- Drop table

-- DROP TABLE type_id GO

CREATE TABLE type_id (
	id BIGINT NOT NULL,
	base_price BIGINT NULL,
	capacity FLOAT NULL,
	factionid BIGINT NULL,
	graphicid BIGINT NULL,
	groupid BIGINT NULL,
	iconid BIGINT NULL,
	market_groupid BIGINT NULL,
	mass FLOAT NULL,
	meta_groupid BIGINT NULL,
	portion_size int NULL,
	published BIT NOT NULL,
	raceid BIGINT NULL,
	radius FLOAT NULL,
	sof_faction_name VARCHAR(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	sof_material_setid BIGINT NULL,
	soundid BIGINT NULL,
	variation_parent_typeid BIGINT NULL,
	volume FLOAT NULL,
	description_id BIGINT NULL,
	masteries_id BIGINT NULL,
	name_id BIGINT NULL,
	traits_id BIGINT NULL,
	CONSTRAINT PK__type_id__3213E83F8E000C3E PRIMARY KEY (id),
	CONSTRAINT FK8mxy3qihvbq9intfa2m06t36f FOREIGN KEY (name_id) REFERENCES translated_string(id),
	CONSTRAINT FKbdyk9l2tnko39c861stqax7fj FOREIGN KEY (traits_id) REFERENCES traits(id),
	CONSTRAINT FKegl80gkiw1jqyxp89qh2jsp64 FOREIGN KEY (masteries_id) REFERENCES masteries(id),
	CONSTRAINT FKocdy9p84isucoaceeqykmdh3 FOREIGN KEY (description_id) REFERENCES translated_string(id)
) GO


-- blueprint_materials definition

-- Drop table

-- DROP TABLE blueprint_materials GO

CREATE TABLE blueprint_materials (
	id BIGINT IDENTITY(1,1) NOT NULL,
	probability FLOAT NOT NULL,
	quantity int NOT NULL,
	type_id BIGINT NOT NULL,
	CONSTRAINT PK__blueprint_materials PRIMARY KEY (id),
    CONSTRAINT FK_type_id FOREIGN KEY (type_id) REFERENCES type_id(id),
) GO

-- activity_products definition

-- Drop table

-- DROP TABLE activity_products GO

CREATE TABLE activity_products (
	activity_id BIGINT NOT NULL,
	products_id BIGINT NOT NULL,
	CONSTRAINT UK_gqsf24gghavngflo59vpk82jw UNIQUE (products_id),
	CONSTRAINT FKojn5od5gmbtg1q7g8nscgmbyh FOREIGN KEY (products_id) REFERENCES blueprint_materials(id),
	CONSTRAINT FKsyvi71urkawd754qcovvqcxht FOREIGN KEY (activity_id) REFERENCES activity(id)
) GO


-- activity_required_materials definition

-- Drop table

-- DROP TABLE activity_required_materials GO

CREATE TABLE activity_required_materials (
	activity_id BIGINT NOT NULL,
	required_materials_id BIGINT NOT NULL,
	CONSTRAINT UK_902rteef200teyplffe2ximi8 UNIQUE (required_materials_id),
	CONSTRAINT FK19yje4lttpkw7vwmn2ul8s1wm FOREIGN KEY (activity_id) REFERENCES activity(id),
	CONSTRAINT FKr19gn34tccjb40eavvswjkoa3 FOREIGN KEY (required_materials_id) REFERENCES blueprint_materials(id)
) GO

-- activity_skills definition

-- Drop table

-- DROP TABLE activity_skills GO

CREATE TABLE activity_skills (
	activity_id BIGINT NOT NULL,
	skills_id BIGINT NOT NULL,
	CONSTRAINT UK_mhmm2aa98v82eocoi8sc1kloj UNIQUE (skills_id),
	CONSTRAINT FK6cb6lxh529u18cg3lh6xpydn8 FOREIGN KEY (activity_id) REFERENCES activity(id),
	CONSTRAINT FKj0w2nsff3eg06441f8i16agxt FOREIGN KEY (skills_id) REFERENCES skill(id)
) GO


-- blueprint definition

-- Drop table

-- DROP TABLE blueprint GO

CREATE TABLE blueprint (
	blueprint_typeid BIGINT NOT NULL,
	production_limit int NOT NULL,
	activities_id BIGINT NULL,
	CONSTRAINT PK__blueprin__1CA402AFABBA4E7C PRIMARY KEY (blueprint_typeid),
	CONSTRAINT FKhu0ik7w2rupd6esnhlcfokcjx FOREIGN KEY (activities_id) REFERENCES activities(id)
) GO

-- type_material_materials definition

-- Drop table

-- DROP TABLE type_material_materials GO

CREATE TABLE type_material_materials (
	type_material_id BIGINT NOT NULL,
	materials_material_typeid BIGINT NOT NULL,
	CONSTRAINT UK_fwj4tqqgs8v58i6oig019fsp2 UNIQUE (materials_material_typeid),
	CONSTRAINT FK46i2jkswblhodnwh6kx47g9uk FOREIGN KEY (type_material_id) REFERENCES type_material(id),
	CONSTRAINT FKsgf0dcpyk5rxcsgr32getprmq FOREIGN KEY (materials_material_typeid) REFERENCES material(material_typeid)
) GO


-- [character] definition

-- Drop table

-- DROP TABLE [character] GO

CREATE TABLE [character] (
	id BIGINT IDENTITY(1,1) NOT NULL,
	alliance_id BIGINT NOT NULL,
	character_id BIGINT NOT NULL,
	corporation_id BIGINT NOT NULL,
	damage_done BIGINT NOT NULL,
	damage_taken BIGINT NOT NULL,
	faction_id BIGINT NOT NULL,
	final_blow BIT NOT NULL,
	x FLOAT NULL,
	y FLOAT NULL,
	z FLOAT NULL,
	security_status BIGINT NOT NULL,
	ship_type_id_id BIGINT NULL,
	weapon_type_id_id BIGINT NULL,
	CONSTRAINT PK__characte__3213E83FD0FD7C9F PRIMARY KEY (id),
	CONSTRAINT FK8o3qi7rngg5dgs4oichjybvko FOREIGN KEY (weapon_type_id_id) REFERENCES type_id(id),
	CONSTRAINT FKg2i0ge8u8a89qn842doxp8a1w FOREIGN KEY (ship_type_id_id) REFERENCES type_id(id)
) GO


-- destroyed_content definition

-- Drop table

-- DROP TABLE destroyed_content GO

CREATE TABLE destroyed_content (
	id BIGINT IDENTITY(1,1) NOT NULL,
	[date] datetime2(7) NULL,
	quentity BIGINT NULL,
	type_id BIGINT NULL,
	CONSTRAINT PK__destroye__3213E83F2A5B4837 PRIMARY KEY (id),
	CONSTRAINT FKajvac8lkgtnf13d29i0pxfrqi FOREIGN KEY (type_id) REFERENCES type_id(id)
) GO


-- item definition

-- Drop table

-- DROP TABLE item GO

CREATE TABLE item (
	id BIGINT IDENTITY(1,1) NOT NULL,
	destroyed BIGINT NOT NULL,
	dropped BIGINT NOT NULL,
	flag int NOT NULL,
	singleton int NOT NULL,
	item_id_id BIGINT NULL,
	CONSTRAINT PK__item__3213E83F62E1E3A7 PRIMARY KEY (id),
	CONSTRAINT FKhfx5igsjf76uyud52ns0alcky FOREIGN KEY (item_id_id) REFERENCES type_id(id)
) GO


-- message definition

-- Drop table

-- DROP TABLE message GO

CREATE TABLE message (
	id BIGINT IDENTITY(1,1) NOT NULL,
	killmail_id BIGINT NOT NULL,
	killmail_time datetime2(7) NULL,
	solar_system_id BIGINT NOT NULL,
	victim_id BIGINT NULL,
	CONSTRAINT PK__message__3213E83F8F583D01 PRIMARY KEY (id),
	CONSTRAINT FKnspcuk34s6hviv57od3nrurvh FOREIGN KEY (victim_id) REFERENCES [character](id)
) GO


-- message_attackers definition

-- Drop table

-- DROP TABLE message_attackers GO

CREATE TABLE message_attackers (
	message_id BIGINT NOT NULL,
	attackers_id BIGINT NOT NULL,
	CONSTRAINT UK_kfv8u9jotuksfxf6b7d8om6k9 UNIQUE (attackers_id),
	CONSTRAINT FK425idfsrxjgtp5v2abjdfuvas FOREIGN KEY (message_id) REFERENCES message(id),
	CONSTRAINT FKh9u35106thclx5rywqg34sev5 FOREIGN KEY (attackers_id) REFERENCES [character](id)
) GO


CREATE TABLE unique_name (
	id BIGINT NOT NULL,
	group_id int,
	[name] VARCHAR(255),
	CONSTRAINT PK__unique_name PRIMARY KEY (id),
	INDEX IX_unique_name NONCLUSTERED (id, name)
)

CREATE TABLE region (
	id BIGINT,
	CONSTRAINT PK__region PRIMARY KEY (id),
	CONSTRAINT FK_region_unique_name FOREIGN KEY (id) REFERENCES unique_name(id)
)

CREATE TABLE solar_system (
	id BIGINT NOT NULL,
	border BIT,
	hub BIT,
	region_id BIGINT,
	security FLOAT,
	CONSTRAINT PK__solar_system PRIMARY KEY (id),
	CONSTRAINT FK_region_solar_system FOREIGN KEY (region_id) REFERENCES region(id),
	CONSTRAINT FK_solar_system_unique_name FOREIGN KEY (id) REFERENCES unique_name(id)
)

CREATE TABLE planet (
	id BIGINT NOT NULL,
	celestial_index int,
	solar_system_id BIGINT,
	type_id BIGINT,
	CONSTRAINT PK__planet PRIMARY KEY (id),
	CONSTRAINT FK_solar_system FOREIGN KEY (solar_system_id) REFERENCES solar_system(id),
	CONSTRAINT FK_planet_type_id FOREIGN KEY (type_id) REFERENCES type_id(id),
	CONSTRAINT FK_planet_unique_name FOREIGN KEY (id) REFERENCES unique_name(id)
)

CREATE TABLE moon (
	id BIGINT,
	planet_id BIGINT,
	type_id BIGINT,
	CONSTRAINT PK__moon PRIMARY KEY (id),
	CONSTRAINT FK_moon_planet FOREIGN KEY (planet_id) REFERENCES planet(id),
	CONSTRAINT FK_moon_unique_name FOREIGN KEY (id) REFERENCES unique_name(id)
)

CREATE TABLE station (
	id BIGINT NOT NULL,
	graphic_id BIGINT,
	is_conquerable BIT,
	owner_id BIGINT,
	reprocessing_efficiency FLOAT,
	type_id BIGINT,
	solar_system_id BIGINT,
	moon_id BIGINT,
	graphical_id BIGINT,
	CONSTRAINT PK__station PRIMARY KEY (id),
	CONSTRAINT FK_station_moon FOREIGN KEY (moon_id) REFERENCES moon(id),
	CONSTRAINT FK_station_type_id FOREIGN KEY (type_id) REFERENCES type_id(id),
	CONSTRAINT FK_station_unique_name FOREIGN KEY (id) REFERENCES unique_name(id)
)

CREATE TABLE stargate (
	id BIGINT NOT NULL,
	destination BIGINT NOT NULL,
	[location] BIGINT NOT NULL,
	CONSTRAINT PK__stargate PRIMARY KEY (id)
)

CREATE TABLE catagory (
	id BIGINT NOT NULL,
	[name] BIGINT NOT NULL,
	published BIT NOT NULL,
	icon BIGINT NOT NULL,
	CONSTRAINT PK__catagory PRIMARY KEY (id),
	CONSTRAINT FK_catagory_translated_string FOREIGN KEY ([name]) REFERENCES translated_string(id),
)

CREATE TABLE market_group (
	id BIGINT NOT NULL,
	[name] BIGINT NOT NULL,
	[description] BIGINT,
	has_types BIT NOT NULL,
	[icon_id] BIGINT,
	[parent_group] BIGINT,
	CONSTRAINT PK__market_group PRIMARY KEY (id),
	CONSTRAINT FK_market_group_name_translated_string FOREIGN KEY ([name]) REFERENCES translated_string(id),
	CONSTRAINT FK_market_group_description_translated_string FOREIGN KEY ([description]) REFERENCES translated_string(id),
)

CREATE TABLE [type_group] (
	id BIGINT NOT NULL,
	[name] BIGINT NOT NULL,
	anchorable BIT NOT NULL,
	anchored BIT NOT NULL,
	category BIGINT,
	fittable_non_singleton BIT NOT NULL,
	icon BIGINT,
	published BIT NOT NULL,
	use_base_price BIT NOT NULL,
	CONSTRAINT PK__type_group PRIMARY KEY (id),
	CONSTRAINT FK_type_group_name_translated_string FOREIGN KEY ([name]) REFERENCES translated_string(id),
	CONSTRAINT FK_type_group_category_category FOREIGN KEY ([category]) REFERENCES catagory(id),
)

-- character_items definition

-- Drop table

-- DROP TABLE character_items GO

CREATE TABLE character_items (
	character_id BIGINT NOT NULL,
	items_id BIGINT NOT NULL,
	CONSTRAINT UK_fsup3366jljjss1s4inw01equ UNIQUE (items_id),
	CONSTRAINT FKc06jl88rgp6sscdvoiokjop40 FOREIGN KEY (items_id) REFERENCES item(id),
	CONSTRAINT FKsc040en3ye4m0sbxnny4evx2n FOREIGN KEY (character_id) REFERENCES [character](id)
) GO

CREATE TABLE market_order (
	id BIGINT NOT NULL,
	duration INT,
	is_buy BIT,
	min_volume int,
	price FLOAT,
	[range] VARCHAR(255),
	volume_remain INT,
	volume_total INT,
	issued datetime2,
	[type_id] BIGINT,
	[location] BIGINT,
	system_id BIGINT,
	CONSTRAINT PK__market_order PRIMARY KEY (id),
	CONSTRAINT FK_MO_solar_system FOREIGN KEY (system_id) REFERENCES solar_system(id),
) GO

CREATE TABLE fulfilled_market_order (
	id BIGINT IDENTITY(1,1) NOT NULL,
	is_buy BIT,
	price FLOAT,
	volume INT,
	fulfilled_recorded datetime2,
	[type_id] BIGINT,
	system_id BIGINT,
	[location] BIGINT,
	CONSTRAINT PK__fufilled_market_order PRIMARY KEY (id)	
) GO

CREATE NONCLUSTERED INDEX fulfilled_market_order_system_id_IDX ON fulfilled_market_order (  system_id ASC  )  
	WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	ON [PRIMARY ]

CREATE NONCLUSTERED INDEX market_order_system_id_IDX ON dbo.market_order (  system_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ];
GO

CREATE OR ALTER TRIGGER market_order_fufillment_update on market_order
AFTER UPDATE, DELETE
NOT FOR REPLICATION
AS
BEGIN

	INSERT fulfilled_market_order 
		(is_buy, price, fulfilled_recorded, volume, type_id, system_id, [location])
	select 
		deleted.is_buy, 
		deleted.price,
		GETDATE(),
		deleted.volume_remain - coalesce(inserted.volume_remain, 0), 
		deleted.type_id, 
		deleted.system_id, 
		deleted.[location]
	FROM deleted
	left join inserted on deleted.id = inserted.id
	WHERE deleted.duration != 365
END
GO

CREATE OR ALTER view RequiredMaterials as 
SELECT 
		ti_required.id as requiredMaterialID, 
		ts_required.en as requiredMaterialName, 
		bm2.quantity, 
		fa.type_id as outcomeID, 
		ts_wanted.en as wantedItem from (
	select a2.id as activityID, bm.type_id from blueprint b 
	join activities a on b.activities_id =a.id 
	join activity a2 on a.manufacturing_id =a2.id 
	join activity_products ap on ap.activity_id = a2.id 
	join blueprint_materials bm on ap.products_id = bm.id
	) as fa
join activity_required_materials arm on fa.activityID = arm.activity_id
join blueprint_materials bm2 on arm.required_materials_id = bm2.id
join type_id ti_required on ti_required.id = bm2.type_id
join type_id ti_wanted on ti_wanted.id = fa.type_id
join translated_string ts_required on ts_required.id = ti_required.name_id
join translated_string ts_wanted on ts_wanted.id = ti_wanted.name_id;
GO

create or alter view destroyed_content_per_day as
select 
    type_id, 
    sum(quentity) as quentity, 
    cast(date as Date) as date
from destroyed_content dc 
group by 
    cast(date as Date), 
    type_id
GO

CREATE TYPE LongTableType as TABLE(ID BIGINT);
GO

CREATE OR ALTER FUNCTION find_margin(@input_systems LongTableType readonly)
RETURNS @output TABLE(type_id BIGINT, buy_price FLOAT, sell_price FLOAT,  margin FLOAT)
AS BEGIN
	insert into @output (type_id, buy_price, sell_price, margin) 
	(
		select 
			type_id , 
			max(CASE WHEN is_buy=1 THEN price END) as buy_price,
			min(CASE WHEN is_buy=0 THEN price END) as sell_price,
			min(CASE WHEN is_buy=0 THEN price END) / max(CASE WHEN is_buy=1 THEN price END) as margin
		from market_order mo
		join @input_systems as i_s on i_s.ID = mo.system_id
		group by type_id	 
	)
	RETURN;
END 
GO

CREATE OR ALTER FUNCTION find_fufilled_order_volumes(@input_systems LongTableType readonly)
RETURNS @output TABLE(type_id BIGINT, total_isk FLOAT, total_isk_bought FLOAT, 
				      total_isk_sold FLOAT, total_buy_order int, 
				 	  total_sell_order int, avg_buy FLOAT, avg_sell FLOAT)
AS BEGIN
	insert into @output (type_id, total_isk, total_isk_bought, 
						 total_isk_sold, total_buy_order, 
						 total_sell_order, avg_buy, avg_sell) (
		select 
			type_id, 
			sum(price) total_isk, 
			sum(CASE WHEN is_buy=1 THEN price END) as total_isk_bought,
			sum(CASE WHEN is_buy=0 THEN price END) as total_isk_sold,
			sum(CASE WHEN is_buy=1 THEN 1 END) as total_buy_order,
			sum(CASE WHEN is_buy=0 THEN 1 END) as total_sell_order,
			avg(CASE WHEN is_buy=1 THEN price END) as avg_buy,
			avg(CASE WHEN is_buy=0 THEN price END) as avg_sell
		from fulfilled_market_order fmo 
		join @input_systems as i_s on i_s.ID = fmo.system_id 
		where 
			fulfilled_recorded > DATEADD(day,-7, GETDATE())
		group by type_id
	)
	RETURN;
END 
GO

CREATE OR ALTER FUNCTION get_daytrade_info(@input_system VARCHAR(255), @range int) 
RETURNS @output TABLE(type_id BIGINT, buy_price FLOAT, sell_price FLOAT,  margin FLOAT,
					  total_isk FLOAT, total_isk_bought FLOAT, 
				      total_isk_sold FLOAT, total_buy_order int, 
				 	  total_sell_order int, avg_buy FLOAT, avg_sell FLOAT)
AS BEGIN
	
	declare @solar_system_id BIGINT;
	declare @solar_systems LongTableType
	
	select @solar_system_id = ss.id 
	from solar_system ss 
	join unique_name un on ss.id = un.id
	where un.name = @input_system
	
	insert into @solar_systems
	select id from find_neighbours(@solar_system_id , @range)
	
	insert into @output (
		   type_id, buy_price, sell_price, margin, 
		   total_isk, total_isk_bought, total_isk_sold, total_buy_order, 
		   total_sell_order, avg_buy, avg_sell
		   )
	select 
		   fm.type_id, buy_price, sell_price, margin,
		   total_isk, total_isk_bought, 
		   total_isk_sold, total_buy_order, 
		   total_sell_order, avg_buy, avg_sell
    from find_margin(@solar_systems) as fm
	join find_fufilled_order_volumes(@solar_systems) as fov on fm.type_id = fov.type_id
	
	return
END
GO

CREATE OR ALTER FUNCTION get_market_group_lineage(@market_group_id BIGINT) 
RETURNS @output TABLE(id BIGINT, name nvarchar(100), parent BIGINT, lineage nvarchar(100))
BEGIN
	with cte_market_group_children as (
	SELECT
		mg.id,
		mg.name,
		mg.parent_group
	FROM market_group mg
	WHERE mg.id = @market_group_id
	UNION ALL 
	select 
		mg2.id,
		mg2.name,
		mg2.parent_group
	from market_group AS mg2, cte_market_group_children AS mgc
	where mg2.parent_group = mgc.id ),
	cte_market_group_parents as (
		SELECT mg.id,
			mg.name,
			mg.parent_group
		FROM market_group mg 
		WHERE mg.id = @market_group_id
		UNION ALL 
		select 
			mg2.id,
			mg2.name,
			mg2.parent_group
		from market_group AS mg2, cte_market_group_parents AS mgp
		where mg2.id = mgp.parent_group 
	)
	insert into @output (id, name, parent, lineage) 
	select id, en, parent_group, lineage from (
	select cte.id, ts.en, parent_group, 'child' as lineage
	from cte_market_group_children as cte
	join translated_string ts on ts.id = cte.name
	where cte.id != @market_group_id
	UNION ALL 
	select cte.id, ts.en, parent_group, 'parent' as lineage
	from cte_market_group_parents as cte
	join translated_string ts on ts.id = cte.name
	where cte.id != @market_group_id
	) as full_market_group_lineage
	UNION ALL
	SELECT mg.id, ts.en, parent_group, 'source' from market_group as mg
	join translated_string ts on ts.id = mg.name 
	where mg.id = @market_group_id

	RETURN 
END
GO
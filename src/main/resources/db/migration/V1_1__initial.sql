-- activity definition

-- Drop table

-- DROP TABLE activity GO

CREATE TABLE activity (
	id bigint IDENTITY(1,1) NOT NULL,
	[time] int NOT NULL,
	CONSTRAINT PK__activity__3213E83F197BF131 PRIMARY KEY (id)
) GO


-- masteries definition

-- Drop table

-- DROP TABLE masteries GO

CREATE TABLE masteries (
	id bigint IDENTITY(1,1) NOT NULL,
	CONSTRAINT PK__masterie__3213E83FBD7EDA5E PRIMARY KEY (id)
) GO


-- material definition

-- Drop table

-- DROP TABLE material GO

CREATE TABLE material (
	material_typeid bigint IDENTITY(1,1) NOT NULL,
	quantity bigint NULL,
	CONSTRAINT PK__material__D9E4AE62327A8308 PRIMARY KEY (material_typeid)
) GO

-- skill definition

-- Drop table

-- DROP TABLE skill GO

CREATE TABLE skill (
	id bigint IDENTITY(1,1) NOT NULL,
	[level] int NOT NULL,
	typeid bigint NOT NULL,
	CONSTRAINT PK__skill__3213E83F71A7C3C6 PRIMARY KEY (id)
) GO


-- tanslated_string definition

-- Drop table

-- DROP TABLE tanslated_string GO

CREATE TABLE tanslated_string (
	id bigint IDENTITY(1,1) NOT NULL,
	de varchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	en varchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	es nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	fr varchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	it nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ja nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ru nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	zh nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__tanslate__3213E83F5CC98932 PRIMARY KEY (id)
) GO


-- traits definition

-- Drop table

-- DROP TABLE traits GO

CREATE TABLE traits (
	id bigint IDENTITY(1,1) NOT NULL,
	iconid bigint NULL,
	CONSTRAINT PK__traits__3213E83F8EB9CCFB PRIMARY KEY (id)
) GO


-- type_material definition

-- Drop table

-- DROP TABLE type_material GO

CREATE TABLE type_material (
	id bigint IDENTITY(1,1) NOT NULL,
	CONSTRAINT PK__type_mat__3213E83FA5E8D088 PRIMARY KEY (id)
) GO


-- activities definition

-- Drop table

-- DROP TABLE activities GO

CREATE TABLE activities (
	id bigint IDENTITY(1,1) NOT NULL,
	copying_id bigint NULL,
	invention_id bigint NULL,
	manufacturing_id bigint NULL,
	research_material_id bigint NULL,
	research_time_id bigint NULL,
	CONSTRAINT PK__activiti__3213E83F771F104F PRIMARY KEY (id),
	CONSTRAINT FK4qktn1rxtiqwad45gvquyovnm FOREIGN KEY (manufacturing_id) REFERENCES activity(id),
	CONSTRAINT FK68f70f2pg0nbwgquc07ste718 FOREIGN KEY (invention_id) REFERENCES activity(id),
	CONSTRAINT FK7rew2dqa7d7h65w99c2wrs4i FOREIGN KEY (research_time_id) REFERENCES activity(id),
	CONSTRAINT FKiv3g1u9rid1wfnqvhfhvlmw69 FOREIGN KEY (research_material_id) REFERENCES activity(id),
	CONSTRAINT FKjirl8t883h61tc7cdvkox1d9j FOREIGN KEY (copying_id) REFERENCES activity(id)
) GO


-- bonus definition

-- Drop table

-- DROP TABLE bonus GO

CREATE TABLE bonus (
	unitid bigint NOT NULL,
	bonus_amount float NOT NULL,
	importance int NOT NULL,
	is_positive bit NOT NULL,
	bonus_text_id bigint NULL,
	CONSTRAINT PK__bonus__55289E4D8A787E06 PRIMARY KEY (unitid),
	CONSTRAINT FKke5tls94mrc5wm4yfwbwewy0a FOREIGN KEY (bonus_text_id) REFERENCES tanslated_string(id)
) GO


-- catagory definition

-- Drop table

-- DROP TABLE catagory GO

CREATE TABLE catagory (
	id bigint IDENTITY(1,1) NOT NULL,
	published bit NOT NULL,
	name_id bigint NULL,
	CONSTRAINT PK__catagory__3213E83F445B799A PRIMARY KEY (id),
	CONSTRAINT FKqwtvmla6ip6a19kwgvq5dcbr6 FOREIGN KEY (name_id) REFERENCES tanslated_string(id)
) GO


-- masteries_first definition

-- Drop table

-- DROP TABLE masteries_first GO

CREATE TABLE masteries_first (
	masteries_id bigint NOT NULL,
	[first] bigint NULL,
	CONSTRAINT FKq02k5fxjx7wyqkkr186wigl5u FOREIGN KEY (masteries_id) REFERENCES masteries(id)
) GO


-- masteries_four definition

-- Drop table

-- DROP TABLE masteries_four GO

CREATE TABLE masteries_four (
	masteries_id bigint NOT NULL,
	four bigint NULL,
	CONSTRAINT FKpsgmqonuhp8drhyfnrlun4a0l FOREIGN KEY (masteries_id) REFERENCES masteries(id)
) GO


-- masteries_three definition

-- Drop table

-- DROP TABLE masteries_three GO

CREATE TABLE masteries_three (
	masteries_id bigint NOT NULL,
	three bigint NULL,
	CONSTRAINT FKkaoumog4o9kfwngt5jdi5x47s FOREIGN KEY (masteries_id) REFERENCES masteries(id)
) GO


-- masteries_two definition

-- Drop table

-- DROP TABLE masteries_two GO

CREATE TABLE masteries_two (
	masteries_id bigint NOT NULL,
	two bigint NULL,
	CONSTRAINT FKruvk0fu6hjv1k02hyya46g2fj FOREIGN KEY (masteries_id) REFERENCES masteries(id)
) GO


-- masteries_zero definition

-- Drop table

-- DROP TABLE masteries_zero GO

CREATE TABLE masteries_zero (
	masteries_id bigint NOT NULL,
	zero bigint NULL,
	CONSTRAINT FK69lt3mojktv0hmhuurga1fc6v FOREIGN KEY (masteries_id) REFERENCES masteries(id)
) GO


-- type_id definition

-- Drop table

-- DROP TABLE type_id GO

CREATE TABLE type_id (
	id bigint NOT NULL,
	base_price bigint NULL,
	capacity float NOT NULL,
	factionid bigint NOT NULL,
	graphicid bigint NOT NULL,
	groupid bigint NOT NULL,
	iconid bigint NOT NULL,
	market_groupid bigint NOT NULL,
	mass float NOT NULL,
	meta_groupid bigint NOT NULL,
	portion_size int NOT NULL,
	published bit NOT NULL,
	raceid bigint NOT NULL,
	radius float NOT NULL,
	sof_faction_name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	sof_material_setid bigint NOT NULL,
	soundid bigint NOT NULL,
	variation_parent_typeid bigint NOT NULL,
	volume float NOT NULL,
	description_id bigint NULL,
	masteries_id bigint NULL,
	name_id bigint NULL,
	traits_id bigint NULL,
	CONSTRAINT PK__type_id__3213E83F8E000C3E PRIMARY KEY (id),
	CONSTRAINT FK8mxy3qihvbq9intfa2m06t36f FOREIGN KEY (name_id) REFERENCES tanslated_string(id),
	CONSTRAINT FKbdyk9l2tnko39c861stqax7fj FOREIGN KEY (traits_id) REFERENCES traits(id),
	CONSTRAINT FKegl80gkiw1jqyxp89qh2jsp64 FOREIGN KEY (masteries_id) REFERENCES masteries(id),
	CONSTRAINT FKocdy9p84isucoaceeqykmdh3 FOREIGN KEY (description_id) REFERENCES tanslated_string(id)
) GO


-- blueprint_materials definition

-- Drop table

-- DROP TABLE blueprint_materials GO

CREATE TABLE blueprint_materials (
	id bigint IDENTITY(1,1) NOT NULL,
	probability float NOT NULL,
	quantity int NOT NULL,
	type_id bigint NOT NULL,
	CONSTRAINT PK__blueprint_materials PRIMARY KEY (id),
    CONSTRAINT FK_type_id FOREIGN KEY (type_id) REFERENCES type_id(id),
) GO

-- activity_products definition

-- Drop table

-- DROP TABLE activity_products GO

CREATE TABLE activity_products (
	activity_id bigint NOT NULL,
	products_id bigint NOT NULL,
	CONSTRAINT UK_gqsf24gghavngflo59vpk82jw UNIQUE (products_id),
	CONSTRAINT FKojn5od5gmbtg1q7g8nscgmbyh FOREIGN KEY (products_id) REFERENCES blueprint_materials(id),
	CONSTRAINT FKsyvi71urkawd754qcovvqcxht FOREIGN KEY (activity_id) REFERENCES activity(id)
) GO


-- activity_required_materials definition

-- Drop table

-- DROP TABLE activity_required_materials GO

CREATE TABLE activity_required_materials (
	activity_id bigint NOT NULL,
	required_materials_id bigint NOT NULL,
	CONSTRAINT UK_902rteef200teyplffe2ximi8 UNIQUE (required_materials_id),
	CONSTRAINT FK19yje4lttpkw7vwmn2ul8s1wm FOREIGN KEY (activity_id) REFERENCES activity(id),
	CONSTRAINT FKr19gn34tccjb40eavvswjkoa3 FOREIGN KEY (required_materials_id) REFERENCES blueprint_materials(id)
) GO

-- activity_skills definition

-- Drop table

-- DROP TABLE activity_skills GO

CREATE TABLE activity_skills (
	activity_id bigint NOT NULL,
	skills_id bigint NOT NULL,
	CONSTRAINT UK_mhmm2aa98v82eocoi8sc1kloj UNIQUE (skills_id),
	CONSTRAINT FK6cb6lxh529u18cg3lh6xpydn8 FOREIGN KEY (activity_id) REFERENCES activity(id),
	CONSTRAINT FKj0w2nsff3eg06441f8i16agxt FOREIGN KEY (skills_id) REFERENCES skill(id)
) GO


-- blueprint definition

-- Drop table

-- DROP TABLE blueprint GO

CREATE TABLE blueprint (
	blueprint_typeid bigint NOT NULL,
	production_limit int NOT NULL,
	activities_id bigint NULL,
	CONSTRAINT PK__blueprin__1CA402AFABBA4E7C PRIMARY KEY (blueprint_typeid),
	CONSTRAINT FKhu0ik7w2rupd6esnhlcfokcjx FOREIGN KEY (activities_id) REFERENCES activities(id)
) GO

-- type_material_materials definition

-- Drop table

-- DROP TABLE type_material_materials GO

CREATE TABLE type_material_materials (
	type_material_id bigint NOT NULL,
	materials_material_typeid bigint NOT NULL,
	CONSTRAINT UK_fwj4tqqgs8v58i6oig019fsp2 UNIQUE (materials_material_typeid),
	CONSTRAINT FK46i2jkswblhodnwh6kx47g9uk FOREIGN KEY (type_material_id) REFERENCES type_material(id),
	CONSTRAINT FKsgf0dcpyk5rxcsgr32getprmq FOREIGN KEY (materials_material_typeid) REFERENCES material(material_typeid)
) GO


-- [character] definition

-- Drop table

-- DROP TABLE [character] GO

CREATE TABLE [character] (
	id bigint IDENTITY(1,1) NOT NULL,
	alliance_id bigint NOT NULL,
	character_id bigint NOT NULL,
	corporation_id bigint NOT NULL,
	damage_done bigint NOT NULL,
	damage_taken bigint NOT NULL,
	faction_id bigint NOT NULL,
	final_blow bit NOT NULL,
	x float NULL,
	y float NULL,
	z float NULL,
	security_status bigint NOT NULL,
	ship_type_id_id bigint NULL,
	weapon_type_id_id bigint NULL,
	CONSTRAINT PK__characte__3213E83FD0FD7C9F PRIMARY KEY (id),
	CONSTRAINT FK8o3qi7rngg5dgs4oichjybvko FOREIGN KEY (weapon_type_id_id) REFERENCES type_id(id),
	CONSTRAINT FKg2i0ge8u8a89qn842doxp8a1w FOREIGN KEY (ship_type_id_id) REFERENCES type_id(id)
) GO


-- destroyed_content definition

-- Drop table

-- DROP TABLE destroyed_content GO

CREATE TABLE destroyed_content (
	id bigint IDENTITY(1,1) NOT NULL,
	[date] datetime2(7) NULL,
	quentity bigint NULL,
	type_id bigint NULL,
	CONSTRAINT PK__destroye__3213E83F2A5B4837 PRIMARY KEY (id),
	CONSTRAINT FKajvac8lkgtnf13d29i0pxfrqi FOREIGN KEY (type_id) REFERENCES type_id(id)
) GO


-- item definition

-- Drop table

-- DROP TABLE item GO

CREATE TABLE item (
	id bigint IDENTITY(1,1) NOT NULL,
	destroyed bigint NOT NULL,
	dropped bigint NOT NULL,
	flag int NOT NULL,
	singleton int NOT NULL,
	item_id_id bigint NULL,
	CONSTRAINT PK__item__3213E83F62E1E3A7 PRIMARY KEY (id),
	CONSTRAINT FKhfx5igsjf76uyud52ns0alcky FOREIGN KEY (item_id_id) REFERENCES type_id(id)
) GO


-- message definition

-- Drop table

-- DROP TABLE message GO

CREATE TABLE message (
	id bigint IDENTITY(1,1) NOT NULL,
	killmail_id bigint NOT NULL,
	killmail_time datetime2(7) NULL,
	solar_system_id bigint NOT NULL,
	victim_id bigint NULL,
	CONSTRAINT PK__message__3213E83F8F583D01 PRIMARY KEY (id),
	CONSTRAINT FKnspcuk34s6hviv57od3nrurvh FOREIGN KEY (victim_id) REFERENCES [character](id)
) GO


-- message_attackers definition

-- Drop table

-- DROP TABLE message_attackers GO

CREATE TABLE message_attackers (
	message_id bigint NOT NULL,
	attackers_id bigint NOT NULL,
	CONSTRAINT UK_kfv8u9jotuksfxf6b7d8om6k9 UNIQUE (attackers_id),
	CONSTRAINT FK425idfsrxjgtp5v2abjdfuvas FOREIGN KEY (message_id) REFERENCES message(id),
	CONSTRAINT FKh9u35106thclx5rywqg34sev5 FOREIGN KEY (attackers_id) REFERENCES [character](id)
) GO

-- character_items definition

-- Drop table

-- DROP TABLE character_items GO

CREATE TABLE character_items (
	character_id bigint NOT NULL,
	items_id bigint NOT NULL,
	CONSTRAINT UK_fsup3366jljjss1s4inw01equ UNIQUE (items_id),
	CONSTRAINT FKc06jl88rgp6sscdvoiokjop40 FOREIGN KEY (items_id) REFERENCES item(id),
	CONSTRAINT FKsc040en3ye4m0sbxnny4evx2n FOREIGN KEY (character_id) REFERENCES [character](id)
) GO

create   view RequiredMaterials as 
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
join tanslated_string ts_required on ts_required.id = ti_required.name_id
join tanslated_string ts_wanted on ts_wanted.id = ti_wanted.name_id;
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



CREATE TABLE unique_name (
	id bigint NOT NULL,
	group_id int,
	[name] varchar(255),
	CONSTRAINT PK__unique_name PRIMARY KEY (id),
	INDEX IX_unique_name NONCLUSTERED (id, name)
)

CREATE TABLE region (
	id bigint,
	CONSTRAINT PK__region PRIMARY KEY (id),
	CONSTRAINT FK_region_unique_name FOREIGN KEY (id) REFERENCES unique_name(id)
)

CREATE TABLE solar_system (
	id bigint NOT NULL,
	border bit,
	hub bit,
	region_id bigint,
	CONSTRAINT PK__solar_system PRIMARY KEY (id),
	CONSTRAINT FK_region_solar_system FOREIGN KEY (region_id) REFERENCES region(id),
	CONSTRAINT FK_solar_system_unique_name FOREIGN KEY (id) REFERENCES unique_name(id)
)

CREATE TABLE planet (
	id bigint NOT NULL,
	celestial_index int,
	solar_system_id bigint,
	type_id bigint,
	CONSTRAINT PK__planet PRIMARY KEY (id),
	CONSTRAINT FK_solar_system FOREIGN KEY (solar_system_id) REFERENCES solar_system(id),
	CONSTRAINT FK_planet_type_id FOREIGN KEY (type_id) REFERENCES type_id(id),
	CONSTRAINT FK_planet_unique_name FOREIGN KEY (id) REFERENCES unique_name(id)
)

CREATE TABLE moon (
	id bigint,
	planet_id bigint,
	type_id bigint,
	CONSTRAINT PK__moon PRIMARY KEY (id),
	CONSTRAINT FK_moon_planet FOREIGN KEY (planet_id) REFERENCES planet(id),
	CONSTRAINT FK_moon_unique_name FOREIGN KEY (id) REFERENCES unique_name(id)
)

CREATE TABLE station (
	id bigint NOT NULL,
	graphic_id bigint,
	is_conquerable bit,
	owner_id bigint,
	reprocessing_efficiency float,
	type_id bigint,
	solar_system_id bigint,
	moon_id bigint,
	graphical_id bigint,
	CONSTRAINT PK__station PRIMARY KEY (id),
	CONSTRAINT FK_station_moon FOREIGN KEY (moon_id) REFERENCES moon(id),
	CONSTRAINT FK_station_type_id FOREIGN KEY (type_id) REFERENCES type_id(id),
	CONSTRAINT FK_station_unique_name FOREIGN KEY (id) REFERENCES unique_name(id)
)


CREATE TABLE Goods(
	id bigserial not null,
	title varchar(255) not null,
	description varchar(2048),
	pledge varchar(2048) not null,
	price_per_hour double precision not null,
	price_per_day double precision not null,
	price_per_week double precision not null,
	status boolean not null,
	author varchar(255) not null,
	phone_author varchar(30) not null
);

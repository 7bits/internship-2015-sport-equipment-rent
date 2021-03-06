CREATE TYPE ROLE as ENUM ('USER', 'ADMIN');
CREATE TABLE users(
	id bigserial PRIMARY KEY,
	first_name varchar(50) not null,
	second_name varchar(50) not null,
	pass varchar(100) not null,
	phone varchar(50),
	email varchar(50) not null,
	users_role ROLE not null,
	unique(email)
);
CREATE TABLE goods(
	id bigserial PRIMARY KEY,
	title varchar(255) not null,
	description varchar(2048),
	pledge varchar(2048) not null,
	price_per_hour double precision not null CHECK (price_per_hour>=0),
	price_per_day double precision not null CHECK (price_per_day>=0),
	price_per_week double precision not null CHECK (price_per_week>=0),
	status boolean not null,
	author_id integer REFERENCES users(id) not null
);
CREATE TABLE deals(
	id bigserial PRIMARY KEY,
	landlord_id integer REFERENCES users(id) not null,
	renting_id integer REFERENCES users(id) not null,
	goods_id integer REFERENCES goods(id) not null,
	is_answered boolean not null,
	estimated_start_date varchar(30) not null,
	estimated_end_date varchar(30) not null,
	real_start_date timestamp,
	real_end_date timestamp,
	is_closed boolean not null,
	handed boolean not null,
	accepted_renting boolean not null,
	accepted_return boolean not null
);
insert into users (first_name, second_name, pass, phone, email, users_role) values
('admin', 'admin', 'pass', '123456', 'admin', 'ADMIN');

CREATE TABLE announcement_image(
	id bigserial PRIMARY KEY,
	goods_id integer REFERENCES goods(id) not null,
	image_url varchar(1024)
);

 grant all privileges on sequence goods_id_seq to root;
 grant all privileges on sequence users_id_seq to root;
 grant all privileges on sequence deals_id_seq to root;
 grant all privileges on sequence announcement_image_id_seq to root;

 grant all privileges on table goods to root;
 grant all privileges on table users to root;
 grant all privileges on table deals to root;
 grant all privileges on table announcement_image to root;

alter table users add column image_url varchar(2048);


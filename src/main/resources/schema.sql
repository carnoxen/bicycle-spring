drop table if exists member;
drop table if exists request;
drop table if exists bicycle;
drop table if exists image;

create table if not exists member(
	username varchar(20) primary key,
	password char(60) not null,
	email varchar(50) unique not null,
	state char(1) not null,
	county decimal(2) not null
);

create table if not exists request (
	reqid varchar(29) primary key,
	createdAt date not null,
	kind char(1) not null check (kind in ('c', 'm', 'r', 'o')),
	accepted decimal(1) not null default 0 check (accepted between 0 and 2),
	username varchar(20) not null,
	foreign key(username) references Member(username)
);

create table if not exists bicycle(
	serial char(9) primary key,
	registeredAt date not null,
	lost boolean not null default false,
	reqid varchar(29) not null,
	foreign key(reqid) references Request(reqid)
);

create table if not exists image(
	uuid uuid primary key,
	reqid varchar(29) not null,
	foreign key(reqid) references Request(reqid)
);

create or replace view bicycleview as select * from request as r natural join bicycle as b;
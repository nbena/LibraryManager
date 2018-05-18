create table seat (
	seat_number integer not null
		constraint seat_number_positive check (seat_number > 0),
	table_number integer not null
		constraint table_number_positive check (table_number > 0),
	free boolean default true,
	primary key (seat_number, table_number)
);

create table user (
	id serial,
	name varchar(100) not null,
	surname varchar(100) not null,
	email varchar(100) not null unique,
	password varchar(100) not null,
	internal boolean not null default false,
	primary key (id)
);

create table book (
	id serial,
	title varchar(200),
	authors varchar(200)[],
	year integer,
	phouse varchar(200),
	main_topic varchar(200),
	primary key (id)
);

create table copy (
	id serial,
	bookid integer,
	for_consultation boolean not null default false,
	primary key (id),
	foreign key (id) references book(id) on update cascade on delete cascade
);

create table loan (
	id serial,
	userid integer not null,
	copyid integer not null,
	start date not null,
	end date not null default start + interval '2 month',
	is_renew_available boolean not null default true,
	restitution_date date default null,
	primary key (id),
	foreign key (userid) references user(id) on update cascade on delete set null,
	foreign key (copyid) references copy(id) on update casacde on delete set null,
);

create table consultation (
	id serial,
	userid integer not null,
	copyid integer not null,
	start timestamp with time zone not null,
	end timestamp with time zone default null,
	primary key (id),
	foreign key (userid) references user(id) on update cascade on delete set null,
	foreign key (copyid) references copy(id) on update casacde on delete set null,	
)

create table seat_reservation (
	id serial,
	seat_number integer not null,
	table_number integer not null,
	reservation_date date not null,
	time_stamp timestamp with time zone not null default current_time,
	primary key (id),
	foreign key (seat_number, table_number) references
		seat(seat_number, table_number) on update cascade on delete cascade,
);

create table loan_reservation (
	id serial,
	userid integer,
	copyid integer,
	time_stamp timestamp with time zone not null default current_time,
	done boolean not null default false,
	primary key (id),
	foreign key (userid) references user(id) on update cascade on delete set null,
	foreign key (copyid) references copy(id) on update casacde on delete set null,	
)

create table consultation_reservation (
	id serial,
	userid integer,
	copyid integer,
	time_stamp timestamp with time zone not null default current_time,
	reservation_date date not null,
	seat_number integer not null,
	table_number integer not null,
	primary key (id),
	foreign key (userid) references user(id) on update cascade on delete set null,
	foreign key (copyid) references copy(id) on update casacde on delete set null,
	foreign key (seat_number, table_number) references
		seat(seat_number, table_number) on update cascade on delete cascade,		
)



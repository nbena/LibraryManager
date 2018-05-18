create table seat (
	seat_number integer not null
		constraint seat_number_positive check (seat_number > 0),
	table_number integer not null
		constraint table_number_positive check (table_number > 0),
	free boolean default true,
	primary key (seat_number, table_number)
);

create table lm_user (
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

create table lm_copy (
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
	start_date date not null default current_date,
	-- end_date date not null default start_date + interval '2 month',
	end_date date,
	renew_available boolean not null default true,
	restitution_date date default null,
	primary key (id),
	foreign key (userid) references lm_user(id) on update cascade on delete set null,
	foreign key (copyid) references lm_copy(id) on update cascade on delete set null
);

create table consultation (
	id serial,
	userid integer not null,
	copyid integer not null,
	start_date timestamp with time zone not null,
	end_date timestamp with time zone default null,
	primary key (id),
	foreign key (userid) references lm_user(id) on update cascade on delete set null,
	foreign key (copyid) references lm_copy(id) on update cascade on delete set null
);

create table seat_reservation (
	id serial,
	seat_number integer not null,
	table_number integer not null,
	reservation_date date not null,
	time_stamp timestamp with time zone not null default current_timestamp,
	primary key (id),
	foreign key (seat_number, table_number) references
		seat(seat_number, table_number) on update cascade on delete cascade
);

create table loan_reservation (
	id serial,
	userid integer,
	copyid integer,
	time_stamp timestamp with time zone not null default current_timestamp,
	done boolean not null default false,
	primary key (id),
	foreign key (userid) references lm_user(id) on update cascade on delete set null,
	foreign key (copyid) references lm_copy(id) on update cascade on delete set null
);

create table consultation_reservation (
	id serial,
	userid integer,
	copyid integer,
	time_stamp timestamp with time zone not null default current_timestamp,
	reservation_date date not null,
	seat_number integer not null,
	table_number integer not null,
	primary key (id),
	foreign key (userid) references lm_user(id) on update cascade on delete set null,
	foreign key (copyid) references lm_copy(id) on update cascade on delete set null,
	foreign key (seat_number, table_number) references
		seat(seat_number, table_number) on update cascade on delete cascade
);

create or replace function trigger_function_set_end_loans () returns trigger as $$
begin
	update loan set end_date = start + interval '2 month'
    where id=new.id;
	return new;
end
$$ language plpgsql;


create trigger trigger_set_end_loans
before insert on loan
for each row
execute procedure trigger_function_set_end_loans();

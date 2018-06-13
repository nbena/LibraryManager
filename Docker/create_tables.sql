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
	password varchar(500) not null,
	internal boolean not null default false,
	primary key (id)
);

create table librarian (
	id serial,
	email varchar(100) not null unique,
	password varchar(500) not null,
	primary key(id)
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
	status varchar(50) default 'free'
		constraint status_check check (status = 'free' or status = 'reserved'),
	primary key (id),
	foreign key (bookid) references book(id) on update cascade on delete cascade
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
	foreign key (userid) references lm_user(id) on update cascade on delete cascade,
	foreign key (copyid) references lm_copy(id) on update cascade on delete cascade
);

create table consultation (
	id serial,
	userid integer not null,
	copyid integer not null,
	start_date timestamp with time zone not null default current_timestamp,
	end_date timestamp with time zone default null,
	primary key (id),
	foreign key (userid) references lm_user(id) on update cascade on delete cascade,
	foreign key (copyid) references lm_copy(id) on update cascade on delete cascade
);

create table seat_reservation (
	id serial,
	userid integer,
	seat_number integer not null,
	table_number integer not null,
	reservation_date date not null,
	time_stamp timestamp with time zone not null default current_timestamp,
	primary key (id),
	foreign key (userid) references lm_user(id) on update cascade on delete cascade,
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
	foreign key (userid) references lm_user(id) on update cascade on delete cascade,
	foreign key (copyid) references lm_copy(id) on update cascade on delete cascade
);

create table consultation_reservation (
	id serial,
	userid integer,
	copyid integer,
	time_stamp timestamp with time zone not null default current_timestamp,
	reservation_date date not null,
	seat_number integer not null,
	table_number integer not null,
	constraint cr_unique_1 unique(reservation_date, seat_number, table_number),
	constraint cr_unique_2 unique(reservation_date, userid, copyid),
	primary key (id),
	foreign key (userid) references lm_user(id) on update cascade on delete cascade,
	foreign key (copyid) references lm_copy(id) on update cascade on delete cascade,
	foreign key (seat_number, table_number) references
		seat(seat_number, table_number) on update cascade on delete cascade
);

create or replace function trigger_function_set_end_loans () returns trigger as $$
begin
	update loan set end_date = start_date + interval '2 month'
    where id=new.id;
	return new;
end
$$ language plpgsql;


create or replace function trigger_function_check_copy_can_be_reserved () returns trigger as $$
declare
	got_status varchar;
begin
	got_status := (select status
						from lm_copy
						where id=new.copyid);

	if got_status = 'reserved' then
		raise exception 'this copy is already reserved';
	end if;
	return new;
end
$$ language plpgsql;

create or replace function trigger_function_update_copy_status_after_ins_loan_res () returns trigger as $$
begin
	update lm_copy set status = 'reserved'
	where id = new.copyid;

	return new;
end
$$ language plpgsql;


create or replace function trigger_function_update_copy_status_after_delete_loan_res() returns trigger as $$
begin
	update lm_copy set status = 'free'
	where id = old.copyid;

	return new;
end
$$ language plpgsql;


create trigger trigger_set_end_loans
before insert on loan
for each row
execute procedure trigger_function_set_end_loans();

create trigger trigger_new_loan_reservation
before insert on loan_reservation
for each row
execute procedure trigger_function_check_copy_can_be_reserved();

create trigger trigger_update_copy_status_after_ins
after insert on loan_reservation
for each row
execute procedure trigger_function_update_copy_status_after_ins_loan_res();

create trigger trigger_update_copy_status_after_del
after delete on loan_reservation
for each row
execute procedure trigger_function_update_copy_status_after_delete_loan_res();

-- hash of 'password' ;)
insert into lm_user(name, surname, email, internal, password) values
('user1', 'user1', 'user1@example.com', true,
'e9a75486736a550af4fea861e2378305c4a555a05094dee1dca2f68afea49cc3a50e8de6ea131ea521311f4d6fb054a146e8282f8e35ff2e6368c1a62e909716'
);

insert into librarian(email, password) values (
	'librarian@library.com',
	'e9a75486736a550af4fea861e2378305c4a555a05094dee1dca2f68afea49cc3a50e8de6ea131ea521311f4d6fb054a146e8282f8e35ff2e6368c1a62e909716'
	);

insert into book(title, authors, year, phouse, main_topic) values
('Title0', '{"Author1", "Author2"}', 2010, 'oreilly', 'IT'),
('Title2', '{"Author1"}', 2001, 'McGraw Hill', 'Networking'),
('Title3', '{"Author2"}', 2018, 'oxford', 'Programming'),
('Title4', '{"Author5"}', 1999, 'oreilly', 'DBMS');


insert into lm_copy(bookid, for_consultation)
select id, false from book where title = 'Title0'
union
select id, true from book where title = 'Title0'
union
select id, false from book where title = 'Title2'
union
select id, true from book where title = 'Title2'
union
select id, false from book where title = 'Title3'
union
select id, true from book where title = 'Title3'
union
select id, false from book where title = 'Title4'
union
select id, true from book where title = 'Title4';

insert into loan(userid, copyid)
select 1, lm_copy.id
from lm_copy join book on lm_copy.bookid = book.id
where title='Title0'
 and for_consultation = false;

insert into seat (table_number, seat_number)  values
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9),
(3, 10),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(4, 5),
(4, 6),
(4, 7),
(4, 8),
(4, 9),
(4, 10),
(5, 1),
(5, 2),
(5, 3),
(5, 4),
(5, 5),
(5, 6),
(5, 7),
(5, 8),
(5, 9),
(5, 10),
(6, 1),
(6, 2),
(6, 3),
(6, 4),
(6, 5),
(6, 6),
(6, 7),
(6, 8),
(6, 9),
(6, 10),
(7, 1),
(7, 2),
(7, 3),
(7, 4),
(7, 5),
(7, 6),
(7, 7),
(7, 8),
(7, 9),
(7, 10),
(8, 1),
(8, 2),
(8, 3),
(8, 4),
(8, 5),
(8, 6),
(8, 7),
(8, 8),
(8, 9),
(8, 10),
(9, 1),
(9, 2),
(9, 3),
(9, 4),
(9, 5),
(9, 6),
(9, 7),
(9, 8),
(9, 9),
(9, 10),
(10, 1),
(10, 2),
(10, 3),
(10, 4),
(10, 5),
(10, 6),
(10, 7),
(10, 8),
(10, 9),
(10, 10),
(11, 1),
(11, 2),
(11, 3),
(11, 4),
(11, 5),
(11, 6),
(11, 7),
(11, 8),
(11, 9),
(11, 10),
(12, 1),
(12, 2),
(12, 3),
(12, 4),
(12, 5),
(12, 6),
(12, 7),
(12, 8),
(12, 9),
(12, 10),
(13, 1),
(13, 2),
(13, 3),
(13, 4),
(13, 5),
(13, 6),
(13, 7),
(13, 8),
(13, 9),
(13, 10),
(14, 1),
(14, 2),
(14, 3),
(14, 4),
(14, 5),
(14, 6),
(14, 7),
(14, 8),
(14, 9),
(14, 10),
(15, 1),
(15, 2),
(15, 3),
(15, 4),
(15, 5),
(15, 6),
(15, 7),
(15, 8),
(15, 9),
(15, 10);

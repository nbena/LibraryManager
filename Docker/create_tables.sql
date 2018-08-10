  	-- LibraryManager a toy library manager
    -- Copyright (C) 2018 nbena

    -- This program is free software: you can redistribute it and/or modify
    -- it under the terms of the GNU General Public License as published by
    -- the Free Software Foundation, either version 3 of the License, or
    -- (at your option) any later version.

    -- This program is distributed in the hope that it will be useful,
    -- but WITHOUT ANY WARRANTY; without even the implied warranty of
    -- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    -- GNU General Public License for more details.

    -- You should have received a copy of the GNU General Public License
    -- along with this program.  If not, see <https://www.gnu.org/licenses/>.


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
	seat_number integer not null,
	table_number integer not null,
	primary key (id),
	foreign key (userid) references lm_user(id) on update cascade on delete cascade,
	foreign key (copyid) references lm_copy(id) on update cascade on delete cascade,
	foreign key (seat_number, table_number) references
		seat(seat_number, table_number) on update cascade on delete cascade
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
		seat(seat_number, table_number) on update cascade on delete cascade,
	constraint cr_date_gte check (reservation_date >= current_date)
);

create table study(
	id serial,
	userid integer,
	seat_number integer not null,
	table_number integer not null,
	primary key(id),
	foreign key (userid) references lm_user(id) on update cascade on delete cascade,
	foreign key (seat_number, table_number) references
		seat(seat_number, table_number) on update cascade on delete cascade,
	unique(userid, seat_number, table_number)	
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
	done boolean not null default false,
	constraint cr_unique_1 unique(reservation_date, seat_number, table_number),
	constraint cr_unique_2 unique(reservation_date, userid, copyid),
	constraint cr_date_gte check(reservation_date >= current_date),
	primary key (id),
	foreign key (userid) references lm_user(id) on update cascade on delete cascade,
	foreign key (copyid) references lm_copy(id) on update cascade on delete cascade,
	foreign key (seat_number, table_number) references
		seat(seat_number, table_number) on update cascade on delete cascade
);

-- when a new study begins the seat is occupied
create or replace function trigger_function_after_ins_study () returns trigger as $$
begin
	update seat
	set free = false
	where seat.seat_number = new.seat_number and
	seat.table_number = new.table_number;

	return new;
end
$$ language plpgsql;

-- when a new study ends the seat is not occupied
create or replace function trigger_function_after_del_study () returns trigger as $$
begin
	update seat
	set free = true
	where seat.seat_number = old.seat_number and
	seat.table_number = old.table_number;

	return old;
end
$$ language plpgsql;

create or replace function trigger_function_set_end_loans () returns trigger as $$
begin
	if new.start_date is null then
		new.start_date := current_date;
	end if;
	new.end_date := new.start_date + interval '2 month';
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

-- function run when a new loan reservation is inserted. The copy is marked as reserved.
create or replace function trigger_function_update_copy_status_after_ins_res () returns trigger as $$
begin
	update lm_copy set status = 'reserved'
	where id = new.copyid;

	return new;
end
$$ language plpgsql;

-- set a copy status to free when the consultation is finished (end date is not null)
create or replace function trigger_function_update_copy_status_after_consultation_completed() returns trigger as $$
begin
	update lm_copy set status = 'free'
	where id = old.copyid
	and new.end_date is not null;

	return new;
end
$$ language plpgsql;


-- set a copy status to free when the loan is finished (restitution date is not null)
create or replace function trigger_function_update_copy_status_after_loan_completed() returns trigger as $$
begin
	update lm_copy set status = 'free'
	where id = old.copyid
	and new.restitution_date is not null;

	return new;
end
$$ language plpgsql;

-- when the reservation is cancelled, update the copy status too.
create or replace function trigger_function_update_copy_status_after_res_deleted() returns trigger as $$
begin
	update lm_copy set status = 'free'
	where id = old.copyid;

	return old;
end
$$ language plpgsql;


-- when a consultation starts, grab the seat and mark it as reserved.
create or replace function trigger_function_update_seat_status_on_consultation_start() returns trigger as $$
begin
	update seat set free = false
	where seat.seat_number = new.seat_number and
	seat.table_number = new.table_number;

	return new;
end
$$ language plpgsql;


-- when it ends, we free the seat.
create or replace function trigger_function_update_seat_status_on_consultation_end() returns trigger as $$
begin
	update seat set free = true
	where seat.seat_number = old.seat_number and
	seat.table_number = old.table_number
	-- end_date is not null is the only way we can
	-- see if it's finished.
	and new.end_date is not null;

	return new;
end
$$ language plpgsql;

create trigger trigger_study_start
after insert on study
for each row
execute procedure trigger_function_after_ins_study();

create trigger trigger_study_end
after delete on study
for each row
execute procedure trigger_function_after_del_study();

create trigger trigger_set_end_loans
before insert on loan
for each row
execute procedure trigger_function_set_end_loans();

create trigger trigger_new_loan_reservation
before insert on loan_reservation
for each row
execute procedure trigger_function_check_copy_can_be_reserved();

create trigger trigger_update_copy_status_after_ins_loan
after insert on loan_reservation
for each row
execute procedure trigger_function_update_copy_status_after_ins_res();

create trigger trigger_update_copy_status_after_loan_end
after update on loan
for each row
execute procedure trigger_function_update_copy_status_after_loan_completed();

-- same as the triggers after, but they are executed on consultation_reservation
create trigger trigger_update_copy_status_after_ins_consultation
after insert on consultation_reservation
for each row
execute procedure trigger_function_update_copy_status_after_ins_res();

create trigger trigger_update_copy_status_after_consultation_end
after update on consultation
for each row
execute procedure trigger_function_update_copy_status_after_consultation_completed();

create trigger trigger_update_copy_status_after_cons_res_delete
after delete on consultation_reservation
for each row
execute procedure trigger_function_update_copy_status_after_res_deleted();


create trigger trigger_update_copy_status_after_loan_res_delete
after delete on loan_reservation
for each row
execute procedure trigger_function_update_copy_status_after_res_deleted();


create trigger trigger_update_seat_on_consultation_start
after insert on consultation
for each row
execute procedure trigger_function_update_seat_status_on_consultation_start();

create trigger trigger_update_seat_on_consultation_end
after update on consultation
for each row
execute procedure trigger_function_update_seat_status_on_consultation_end();


-- this function add more copies for a given book.
create or replace function add_more_copies (bookid integer, change integer, for_consultation boolean) returns void as $$
declare
	counter integer;
begin
	counter := 0;

	-- adding more copies till we're done
	while counter < $2 loop
		insert into lm_copy(bookid, for_consultation)
		values ($1, $3);

		counter := counter + 1;
	end loop;

end;

$$ language plpgsql;

-- have to change:
-- userid -> user_id
-- seat_number -> seatnumber
-- table_number -> tablenumber
create or replace function try_add_study(user_id integer, seatnumber integer,
	tablenumber integer) returns void as $$
declare
	counter integer;
begin
	counter := (select count (*) from study where userid=$1);

	if counter = 0 then
		insert into study(userid, seat_number, table_number)
		values ($1, $2, $3);
	end if;

end; 
$$ language plpgsql;


create or replace function delete_copies (bookid integer, change integer) returns integer as $$
declare
	-- the number of effectively deletable copies.
	max_available integer;

	-- the array of copies that can be
	-- effectively deleted.
	can_be_deletable integer[];

begin
	-- selecting the copies that can be deleted.
	can_be_deletable := (select array_agg(id) from
					(select lm_copy.id
					from lm_copy join book on lm_copy.bookid = book.id
					where lm_copy.bookid = $1
					and lm_copy.id not in
						(select copyid
						from loan where restitution_date is null)
					and lm_copy.id not in
						(select copyid
						from loan_reservation where done = false)
					and lm_copy.id not in
						(select copyid
						from consultation_reservation
						where reservation_date >= current_date and done = false)
					and lm_copy.id not in
						(select copyid
						from consultation where end_date is null)
					limit $2
					) as available);

	
	max_available = array_length(can_be_deletable, 1);

	if max_available > 0 then
		delete from lm_copy where
		lm_copy.id = any(can_be_deletable);
	end if;

	-- every other copy you want to delete
	-- will be marked as 'reserved' so nobody can
	-- reserve it another time
	-- #genius
	update lm_copy set status = 'reserved'
	where lm_copy.id in
	(	select lm_copy.id
		from lm_copy join book on lm_copy.bookid = book.id
		where lm_copy.bookid = $1
	);

	-- can't renew, sorry
	update loan set renew_available = false
	where copyid in 
	(	select lm_copy.id
		from lm_copy join book on lm_copy.bookid = book.id
		where lm_copy.bookid = $1
	);

	return max_available;
	end
$$ language plpgsql;


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
('Title4', '{"Author5"}', 1999, 'oreilly', 'DBMS'),
('Deletable', '{"Author-nil"}', 1900, 'oreilly', 'DBMS');


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

 -- a late loan
 insert into loan(userid, copyid)
 select 1, lm_copy.id
 from lm_copy join book on lm_copy.bookid = book.id
 where title = 'Title3' and for_consultation = false;

 update loan set start_date = current_date  - interval '3 month',
 end_date = current_date - interval '1 day', restitution_date = null
 where userid = 1 and copyid in
 (
	 select lm_copy.id
	 from lm_copy join book on lm_copy.bookid = book.id
	 where title = 'Title3' and for_consultation = false
 );

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

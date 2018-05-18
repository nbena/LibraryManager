create table seat (
	seat_number integer not null
		constraint seat_number_positive check (seat_number > 0),
	table_number integer not null
		constraint table_number_positive check (table_number > 0),
	free boolean default true,
	primary key (seat_number, table_number)
);


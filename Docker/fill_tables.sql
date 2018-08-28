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

 update loan set start_date = current_date,
 -- end_date = current_date + interval '2 month',
 end_date = current_date - interval '1 day',
 restitution_date = null
 where userid = 1 and copyid in
 (
	 select lm_copy.id
	 from lm_copy join book on lm_copy.bookid = book.id
	 where title = 'Title3' and for_consultation = false
 );
 -- end late loan
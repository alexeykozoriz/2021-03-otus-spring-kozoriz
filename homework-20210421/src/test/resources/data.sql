--inserting genres
insert into genres(id, title)
values (1, 'Jdbc fiction');
--inserting authors
insert into authors(id, full_name)
values (1, 'Jane Doe');
--inserting books
insert into books(id, title, publication_year, author_id, genre_id)
values (1, 'Two driven jocks help fax my big quiz', 2021, 1, 1);
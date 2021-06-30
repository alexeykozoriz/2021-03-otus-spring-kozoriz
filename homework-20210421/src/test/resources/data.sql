--inserting genres
insert into genres(id, title)
values (1, 'Testing fiction');
--inserting authors
insert into authors(id, full_name)
values (1, 'Jane Doe');
--inserting books
insert into books(id, title, publication_year, author_id, genre_id)
values (1, 'The quick brown fox jumps over the lazy dog', 2021, 1, 1);
--inserting genres
insert into genres(id, title)
values (1, 'Science fiction');
insert into genres(id, title)
values (2, 'Detective fiction');
insert into genres(id, title)
values (3, 'Fantasy');
insert into genres(id, title)
values (4, 'Nonfiction');
--inserting authors
insert into authors(id, full_name)
values (1, 'Frank Herbert');
insert into authors(id, full_name)
values (2, 'Isaac Asimov');
insert into authors(id, full_name)
values (3, 'Arthur Conan Doyle');
insert into authors(id, full_name)
values (4, 'John Ronald Reuel Tolkien');
insert into authors(id, full_name)
values (5, 'Martin Fowler');
insert into authors(id, full_name)
values (6, 'Stephen William Hawking');
--inserting books
insert into books(id, title, publication_year, author_id, genre_id)
values (1, 'Dune', 1965, 1, 1);
insert into books(id, title, publication_year, author_id, genre_id)
values (2, 'Foundation', 1951, 2, 1);
insert into books(id, title, publication_year, author_id, genre_id)
values (3, 'Foundation and Empire', 1952, 2, 1);
insert into books(id, title, publication_year, author_id, genre_id)
values (4, 'Second Foundation', 1953, 2, 1);
insert into books(id, title, publication_year, author_id, genre_id)
values (5, 'A Study in Scarlet', 1887, 3, 2);
insert into books(id, title, publication_year, author_id, genre_id)
values (6, 'The Sign of the Four', 1890, 3, 2);
insert into books(id, title, publication_year, author_id, genre_id)
values (7, 'The Fellowship of the Ring', 1954, 4, 3);
insert into books(id, title, publication_year, author_id, genre_id)
values (8, 'The Two Towers', 1954, 4, 3);
insert into books(id, title, publication_year, author_id, genre_id)
values (9, 'The Return of the King', 1955, 4, 3);
insert into books(id, title, publication_year, author_id, genre_id)
values (10, 'Refactoring: Improving the Design of Existing Code', 1999, 5, 4);
insert into books(id, title, publication_year, author_id, genre_id)
values (11, 'Refactoring: Ruby Edition', 2013, 5, 4);
insert into books(id, title, publication_year, author_id, genre_id)
values (12, 'Refactoring: Improving the Design of Existing Code, Second Edition', 2018, 5, 4);
insert into books(id, title, publication_year, author_id, genre_id)
values (13, 'A Brief History of Time', 1988, 6, 4);
insert into books(id, title, publication_year, author_id, genre_id)
values (14, 'Black Holes and Baby Universes and Other Essays', 1993, 6, 4);
insert into books(id, title, publication_year, author_id, genre_id)
values (15, 'The Universe in a Nutshell', 2001, 6, 4);
insert into books(id, title, publication_year, author_id, genre_id)
values (16, 'A Briefer History of Time', 2012, 6, 4);
--inserting book comments
insert into book_comments(id, author, text, book_id)
values (1, 'John Doe', 'Best book ever!', 1);
insert into book_comments(id, author, text, book_id)
values (2, 'Jane Doe', 'Worst book ever!', 1);
insert into book_comments(id, author, text, book_id)
values (3, 'John Doe', 'Best book ever!', 2);
insert into book_comments(id, author, text, book_id)
values (4, 'Jane Doe', 'Worst book ever!', 3);
--inserting users
insert into users(id, user_name, password, role)
values (1, 'John Doe', 'J0hnw0rd', 'USER');
insert into users(id, user_name, password, role)
values (2, 'Jane Doe', 'J@n3w0rd', 'USER');
--creating books
drop table if exists books;
create table books
(
    id               bigint primary key auto_increment,
    title            varchar(256),
    publication_year int,
    author_id        bigint,
    genre_id         bigint
);
--creating authors
drop table if exists authors;
create table authors
(
    id        bigint primary key auto_increment,
    full_name varchar(256)
);
--creating genres
drop table if exists genres;
create table genres
(
    id    bigint primary key auto_increment,
    title varchar(256)
);
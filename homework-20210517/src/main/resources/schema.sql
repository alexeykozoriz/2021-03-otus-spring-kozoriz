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
--creating books
drop table if exists books;
create table books
(
    id               bigint primary key auto_increment,
    title            varchar(256),
    publication_year int,
    author_id        bigint,
    foreign key (author_id) references authors (id),
    genre_id         bigint,
    foreign key (genre_id) references genres (id)
);
--creating book comments
drop table if exists book_comments;
create table book_comments
(
    id      bigint primary key auto_increment,
    author  varchar(256),
    text    varchar(1024),
    book_id bigint,
    foreign key (book_id) references books (id)
);
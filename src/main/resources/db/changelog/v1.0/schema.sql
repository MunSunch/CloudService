create table users (
    id serial
       constraint PK_users primary key,
    login text not null
        constraint UQ_users_login unique,
    password text not null,
    role text not null
);
GO

create table files (
    id serial
       constraint PK_files primary key,
    name text not null,
    content bytea not null,
    type text not null
);
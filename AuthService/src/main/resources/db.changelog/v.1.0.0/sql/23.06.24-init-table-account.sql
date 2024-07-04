create table if not exists account
(
    id       uuid         not null,
    status   varchar(255) not null,
    role     varchar(255) not null,
    password varchar(255) not null,
    email    varchar(255) not null,
    primary key (id)
);

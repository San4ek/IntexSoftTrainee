create table if not exists credentials
(
    id         uuid         not null,
    email      varchar(320) not null,
    password   varchar(255) not null,
    primary key (id),
    constraint email_unique
        unique (email)
);

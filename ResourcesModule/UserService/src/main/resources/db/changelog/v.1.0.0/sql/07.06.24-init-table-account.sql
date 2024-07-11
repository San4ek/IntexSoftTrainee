create table if not exists account
(
    id       uuid         not null,
    status   varchar(255) not null,
    role     varchar(255) not null,
    email    varchar(255) not null,
    primary key (id),
    constraint unique_email unique(email),
    created_at timestamp not null default current_timestamp,
    deleted_at timestamp,
    blocked_at timestamp,
    blocked_by uuid
);

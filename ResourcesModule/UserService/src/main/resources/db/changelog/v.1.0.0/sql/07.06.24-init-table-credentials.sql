create table if not exists credentials
(
    account_id uuid         not null,
    email      varchar(320) not null,
    password   varchar(255) not null,
    primary key (account_id),
    constraint email_unique
        unique (email),
    constraint credentials_account_fk
        foreign key (account_id) references account
);
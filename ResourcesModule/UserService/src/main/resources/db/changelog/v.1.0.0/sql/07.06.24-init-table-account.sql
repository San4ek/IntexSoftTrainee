create table if not exists account
(
    credentials_id      uuid not null,
    status  varchar(255) not null,
    role varchar(255) not null,
    primary key (credentials_id),

    constraint account_credentials_fk
        foreign key (credentials_id) references credentials
            on update cascade on delete restrict
);

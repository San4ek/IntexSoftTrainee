create table if not exists account
(
    id      uuid not null,
    status  varchar(255),
    role_id uuid not null,
    primary key (id),
    constraint account_role_fk
        foreign key (role_id) references role
            on update cascade on delete restrict
);

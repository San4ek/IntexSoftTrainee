create table if not exists account
(
    id      uuid not null,
    status  status,
    role_id uuid not null,
    primary key (id),
    constraint account_role_fk
        foreign key (role_id) references role
);
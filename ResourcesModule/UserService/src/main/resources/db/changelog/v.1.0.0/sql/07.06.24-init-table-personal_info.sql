create table if not exists personal_info
(
    account_id   uuid         not null,
    name         varchar(255),
    patronymic   varchar(255),
    phone_number varchar(255),
    surname      varchar(255),
    primary key (account_id),
    constraint personal_info_account_fk
        foreign key (account_id) references account
            on update cascade on delete restrict
);

alter table account
    add created_at timestamp not null default '2024-06-07 00:00:00',
    add deleted_at timestamp,
    add blocked_at timestamp,
    add blocked_by uuid;
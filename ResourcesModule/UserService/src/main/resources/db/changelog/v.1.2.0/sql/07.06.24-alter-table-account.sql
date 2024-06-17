alter table account
    add created_at timestamp not null default current_timestamp,
    add deleted_at timestamp,
    add blocked_at timestamp,
    add blocked_by uuid;
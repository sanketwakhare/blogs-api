insert into roles (created_at, updated_at, role, id)
values (current_timestamp(),
        current_timestamp(),
        'ADMIN',
        UUID_TO_BIN(uuid()));

insert into roles (created_at, updated_at, role, id)
values (current_timestamp(),
        current_timestamp(),
        'USER',
        UUID_TO_BIN(uuid()));

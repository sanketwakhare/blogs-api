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

/*create super admin user*/
insert into users (created_at, updated_at, username, email, password, id, auth_provider)
values (current_timestamp(),
        current_timestamp(),
        'superadmin',
        'superadmin@example.com',
        ('$2a$10$mxjorpLyLHaLdqVQNphhNuLqXRDVR1TIVUKeYiIjVr92jvj3mI.cm'),
        UUID_TO_BIN(uuid()),
        'local');

select id, username into @SuperAdminUserId, @SuperAdminUsername from users where username = 'superadmin';
select @SuperAdminUserid, @SuperAdminUsername;

select id, role into @AdminRoleId, @AdminRole from roles where role = 'ADMIN';
select @AdminRoleId, @AdminRole;

/*add ADMIN role for a superadmin user*/
insert into users_roles (users_id, roles_id)
values (@SuperAdminUserId, @AdminRoleId);

/*get assigned roles for a user*/
select r.role from roles r, users u, users_roles ur
              where r.id = ur.roles_id
                and u.id = ur.users_id
                and u.username = 'superadmin';
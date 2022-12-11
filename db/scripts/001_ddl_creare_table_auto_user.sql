create table if not exists auto_user
(
    id       serial primary key,
    login    varchar not null unique,
    password varchar not null
);

comment on table auto_user is 'Пользователь';
comment on column auto_user.id is 'Идентификатор пользователя';
comment on column auto_user.login is 'Логин пользователя';
comment on column auto_user.password is 'Пароль пользователя';
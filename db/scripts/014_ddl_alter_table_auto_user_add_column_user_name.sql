alter table auto_user
    add column user_name varchar;

comment on column auto_user.user_name is 'Имя пользователя';
alter table auto_user
    add column user_zone varchar;

comment on column auto_user.user_zone is 'Часовой пояс пользователя';
create table if not exists auto_post
(
    id           serial primary key,
    text         varchar   not null,
    created      timestamp not null,
    auto_user_id integer references auto_user (id)
);

comment on table auto_post is 'Объявление';
comment on column auto_post.id is 'Идентификатор пользователя';
comment on column auto_post.text is 'Текст объявления';
comment on column auto_post.created is 'Дата публикации';
comment on column auto_post.auto_user_id is 'Внешний ключ на пользователя подавшего объявление';
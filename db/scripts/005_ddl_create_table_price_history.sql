create table price_history
(
    id           serial primary key,
    before       bigint not null,
    after        bigint not null,
    created      timestamp without time zone default now(),
    auto_post_id int references auto_post (id)
);

comment on table price_history is 'История изменений цены';
comment on column price_history.before is 'Старая цена';
comment on column price_history.after is 'Новая цена';
comment on column price_history.created is 'Дата внесения изменений';
comment on column price_history.auto_post_id is 'Внешний ключ связи истории с объявлением';
alter table auto_post
    add column status boolean;

comment on column auto_post.status is 'Статус объявления (true -- продано)';
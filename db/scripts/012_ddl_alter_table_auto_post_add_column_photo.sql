alter table auto_post
    add column photo bytea;

comment on column auto_post.photo is 'Фото автомобиля который продается';
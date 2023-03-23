alter table auto_post
    add column car_id int references car (id);

comment on column auto_post.car_id is 'Внешний ключ на автомобиль который продается';
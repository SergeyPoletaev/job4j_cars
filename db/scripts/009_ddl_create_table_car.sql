create table if not exists car
(
    id        serial primary key,
    name      varchar                           not null,
    engine_id int references engine (id) unique not null,
    driver_id int references driver (id)        not null
);

comment on table car is 'Автомобиль';
comment on column car.id is 'Идентификатор автомобиля';
comment on column car.name is 'Наименование автомобиля';
comment on column car.engine_id is 'Внешний ключ на двигатель автомобиля';
comment on column car.driver_id is 'Внешний ключ на текущего владельца автомобиля';
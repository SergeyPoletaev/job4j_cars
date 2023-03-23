create table if not exists history_owners
(
    id        serial primary key,
    car_id    int references car (id),
    driver_id int references driver (id),
    startAt   timestamp default now(),
    endAt     timestamp default now()
);

comment on table history_owners is 'История владения автомобилем';
comment on column history_owners.id is 'Идентификатор события';
comment on column history_owners.car_id is 'Внешний ключ на автомобиль';
comment on column history_owners.driver_id is 'Внешний ключ на владельца';
comment on column history_owners.startAt is 'Дата начала владения';
comment on column history_owners.endAt is 'Дата окончания владения';
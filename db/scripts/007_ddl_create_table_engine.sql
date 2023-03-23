create table if not exists engine
(
    id     serial primary key,
    name   varchar not null,
    number int     not null
);

comment on table engine is 'Двигатель автомобиля';
comment on column engine.id is 'Идентификатор двигателя';
comment on column engine.name is 'Наименование двигателя';
comment on column engine.number is 'Серийный номер двигателя';
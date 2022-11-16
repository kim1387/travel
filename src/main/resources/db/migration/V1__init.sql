create table if not exists city
(
    id            bigint auto_increment
    primary key,
    created_date  datetime(6)  null,
    is_activated  bit          not null,
    updated_date  datetime(6)  null,
    intro_content varchar(255) not null,
    name          varchar(255) not null,
    view          int          not null
    );

create table if not exists users
(
    id           bigint auto_increment
    primary key,
    created_date datetime(6)  null,
    is_activated bit          not null,
    updated_date datetime(6)  null,
    name         varchar(255) not null,
    role         varchar(255) not null
    );

create table if not exists travel
(
    id           bigint auto_increment
    primary key,
    created_date datetime(6) null,
    is_activated bit         not null,
    updated_date datetime(6) null,
    end_at       datetime(6) not null,
    start_at     datetime(6) not null,
    city_id      bigint      not null,
    users_id     bigint      not null,
    constraint FK2id8skhl6al08h9dl0dhlxbtv
    foreign key (users_id) references users (id),
    constraint FKph1xcctqbrb4abh8xc4vgwk38
    foreign key (city_id) references city (id)
    );


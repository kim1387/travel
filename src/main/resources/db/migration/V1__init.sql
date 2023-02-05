create table users
(
    id           bigint auto_increment
        primary key,
    created_date datetime(6)  null,
    is_activated bit          not null,
    updated_date datetime(6)  null,
    name         varchar(255) not null,
    role         varchar(255) not null
);

create table city
(
    id             bigint auto_increment
        primary key,
    created_date   datetime(6)  null,
    is_activated   bit          not null,
    updated_date   datetime(6)  null,
    intro_content  varchar(255) not null,
    latest_view_at datetime(6)  not null,
    name           varchar(255) not null,
    view           int          not null,
    users_id       bigint       not null,
    constraint FK65g4goi0irnxod66s81skqmqx
        foreign key (users_id) references users (id)
);

create table travel
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
    constraint FK36b1qaev7utmwhejci1s0sk5q
        foreign key (city_id) references city (id),
    constraint FK434o3stjxjjg4n82osnx6rao2
        foreign key (users_id) references users (id)
);


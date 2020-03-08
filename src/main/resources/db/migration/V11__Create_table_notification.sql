create table notification
(
    id bigint auto_increment,
    creator bigint not null,
    receiver bigint not null,
    type int not null,
    parent_id bigint not null,
    status int default 0 not null,
    gmt_create bigint not null,
    constraint notification_pk
        primary key (id)
);

comment on column notification.type is '通知的类型';
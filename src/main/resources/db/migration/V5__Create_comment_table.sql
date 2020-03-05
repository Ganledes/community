create table comment
(
    id bigint auto_increment,
    content varchar(1024) not null,
    parent_type int not null,
    parent_id bigint not null,
    commenter int not null,
    gmt_create bigint not null,
    gmt_modified bigint not null,
    like_count bigint default 0,
    constraint comment_pk
        primary key (id)
);

comment on column comment.content is '评论内容';

comment on column comment.parent_type is '父类类型';

comment on column comment.parent_id is '父类ID';

comment on column comment.commenter is '评论者ID';

comment on column comment.like_count is '点赞数';
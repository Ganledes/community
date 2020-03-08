alter table NOTIFICATION alter column CREATOR rename to "CREATOR_id";

alter table NOTIFICATION
    add outer_tittle varchar(1024) not null;

alter table NOTIFICATION
    add creator_name varchar(50) not null;

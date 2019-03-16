drop table if exists hibernate_sequence;
drop table if exists task_list;
drop table if exists user_task;
create table hibernate_sequence (next_val bigint) engine=MyISAM;
insert into hibernate_sequence values ( 1 );
create table task_list (id bigint not null, checker bool default 0, task_date datetime, task varchar(255), primary key (id)) engine=MyISAM;
create table user_task (id varchar(25) not null, user_email varchar(255), user_name varchar(255), primary key (id)) engine=MyISAM;

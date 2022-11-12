CREATE DATABASE elp_dev;
-------------------------------------------------------
--CREATE USERS
-------------------------------------------------------
CREATE USER 'elp_dev_user'@'%' IDENTIFIED BY 'appuser';

GRANT SELECT ON elp_dev.* to 'elp_dev_user'@'%';
GRANT INSERT ON elp_dev.* to 'elp_dev_user'@'%';
GRANT DELETE ON elp_dev.* to 'elp_dev_user'@'%';
GRANT UPDATE ON elp_dev.* to 'elp_dev_user'@'%';

------------------------------------------------------
--CREATE TABLES
------------------------------------------------------
create table roles (role_id bigint not null auto_increment, name varchar(255), primary key (role_id)) engine=InnoDB;
create table users (user_id bigint not null auto_increment, enabled bit not null, password varchar(255), username varchar(255), primary key (user_id)) engine=InnoDB;
create table users_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB;
alter table users_roles add constraint FK_role foreign key (role_id) references roles (role_id) ON DELETE CASCADE ON UPDATE NO ACTION;
alter table users_roles add constraint FK_user foreign key (user_id) references users (user_id) ON DELETE CASCADE ON UPDATE NO ACTION;

create table answer (id bigint not null auto_increment, content varchar(255), correct bit not null, question_id bigint not null, primary key (id)) engine=InnoDB;
create table question (id bigint not null auto_increment, content varchar(255) not null, quiz_id bigint not null, primary key (id)) engine=InnoDB;
create table quiz (id bigint not null auto_increment, end_time datetime(6), is_open bit not null, start_time datetime(6), primary key (id)) engine=InnoDB;

alter table answer add constraint FK_question foreign key (question_id) references question (id);
alter table question add constraint FK_quiz foreign key (quiz_id) references quiz (id);


-----------------------------------------------------
--Insert initial data
-----------------------------------------------------
INSERT INTO elp_dev.roles (role_id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO elp_dev.roles (role_id, name) VALUES (3, 'ROLE_SUPERVISOR');
INSERT INTO elp_dev.roles (role_id, name) VALUES (3, 'ROLE_MEMBER');
-----
INSERT INTO `elp_dev`.`users`(`enabled`, `password`, `username`)
VALUES (true,'pass', 'memberUser'), (true,'pass', 'supervisorUser'), (true,'pass', 'adminUser');
-----
INSERT INTO `elp_dev`.`users_roles` (`user_id`, `role_id`)
VALUES (1, 1), (2, 2), (3, 3);

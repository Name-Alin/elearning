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
create table users (user_id bigint not null auto_increment, enabled bit not null, first_name varchar(255), is_supervised_by bigint, last_name varchar(255), password varchar(255), username varchar(255), primary key (user_id)) engine=InnoDB;
create table users_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB;
alter table users_roles add constraint FK_role foreign key (role_id) references roles (role_id) ON DELETE CASCADE ON UPDATE NO ACTION;
alter table users_roles add constraint FK_user foreign key (user_id) references users (user_id) ON DELETE CASCADE ON UPDATE NO ACTION;

create table answer (id bigint not null auto_increment, content varchar(255), correct bit not null, question_id bigint not null, primary key (id)) engine=InnoDB;
create table question (id bigint not null auto_increment, content varchar(255) not null, quiz_id bigint not null, primary key (id)) engine=InnoDB;
create table quiz (id bigint not null auto_increment, created_date datetime(6), description varchar(255), expected_percent_correct float, name varchar(255), training_id bigint, primary key (id)) engine=InnoDB;

alter table answer add constraint FK_question foreign key (question_id) references question (id);
alter table question add constraint FK_quiz foreign key (quiz_id) references quiz (id);


create table evaluation_details (id bigint not null auto_increment, end_time datetime(6), graduated bit not null, quiz_duration bigint, quiz_id bigint, quiz_percent_correct float not null, start_time datetime(6), training_id bigint, user_id bigint, primary key (id)) engine=InnoDB;
create table training (id bigint not null auto_increment, description varchar(255), path_to_image varchar(255), path_to_training varchar(255), training_title varchar(255), primary key (id)) engine=InnoDB;

alter table quiz add constraint FK_quiz_training foreign key (training_id) references training (id) ON DELETE SET NULL ON UPDATE NO ACTION;
alter table evaluation_details add constraint FK_eval_training foreign key (training_id) references training (id) ON DELETE SET NULL ON UPDATE NO ACTION;
alter table evaluation_details add constraint FK_eval_user foreign key (user_id) references users (user_id);

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

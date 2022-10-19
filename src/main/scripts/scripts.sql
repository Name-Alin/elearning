CREATE DATABASE elp_dev;

create table roles (role_id bigint not null auto_increment, name varchar(255), primary key (role_id)) engine=InnoDB;
create table users (user_id bigint not null auto_increment, enabled bit not null, password varchar(255), username varchar(255), primary key (user_id)) engine=InnoDB;
create table users_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB;
alter table users_roles add constraint FK_role foreign key (role_id) references roles (role_id) ON DELETE CASCADE ON UPDATE NO ACTION;
alter table users_roles add constraint FK_user foreign key (user_id) references users (user_id) ON DELETE CASCADE ON UPDATE NO ACTION;


CREATE USER 'elp_dev_user'@'%' IDENTIFIED BY 'appuser';

GRANT SELECT ON elp_dev.* to 'elp_dev_user'@'%';
GRANT INSERT ON elp_dev.* to 'elp_dev_user'@'%';
GRANT DELETE ON elp_dev.* to 'elp_dev_user'@'%';
GRANT UPDATE ON elp_dev.* to 'elp_dev_user'@'%';
-----------------------------------------------------
--Insert data
INSERT INTO elp_dev.roles (role_id, name) VALUES (1, 'administrator');
INSERT INTO elp_dev.roles (role_id, name) VALUES (3, 'supervisor');
INSERT INTO elp_dev.roles (role_id, name) VALUES (3, 'member');
-----
INSERT INTO `elp_dev`.`users`(`enabled`, `password`, `username`)
VALUES (true,'pass', 'memberUser'), (true,'pass', 'supervisorUser'), (true,'pass', 'adminUser');
-----
INSERT INTO `elp_dev`.`users_roles` (`user_id`, `role_id`)
VALUES (1, 1), (2, 2), (3, 3);

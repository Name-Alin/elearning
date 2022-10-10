CREATE DATABASE elp_dev;

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

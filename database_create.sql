create table roles (role_id bigint not null auto_increment, name varchar(255), primary key (role_id)) engine=InnoDB;
create table users (user_id bigint not null auto_increment, enabled bit not null, password varchar(255), username varchar(255), primary key (user_id)) engine=InnoDB;
create table users_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB;
alter table users_roles add constraint FKj6m8fwv7oqv74fcehir1a9ffy foreign key (role_id) references roles (role_id);
alter table users_roles add constraint FK2o0jvgh89lemvvo17cbqvdxaa foreign key (user_id) references users (user_id);

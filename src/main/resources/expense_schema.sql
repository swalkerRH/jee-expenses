create table expense_user (
id serial primary key,
username text unique not null,
password text
);

create table expense_user_role (
id serial primary key,
role text unique not null,
user_id int references expense_user(id) not null
);

create table expense_category (
id serial primary key,
name text unique not null,
description text not null,
user_id int references expense_user(id) not null
);

create table expense (
id serial primary key,
description text not null,
category int references expense_category(id),
cost real not null,
entered date not null,
user_id int references expense_user(id) not null
);

create table expense_image (
id serial primary key,
expense_id int references expense(id),
image_data bytea
);
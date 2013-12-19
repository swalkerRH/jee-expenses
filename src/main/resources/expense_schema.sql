create table expense_category (
id serial primary key,
name text unique,
description text
);

create table expense (
id serial primary key,
description text,
category int references expense_category(id),
cost real,
entered date
);

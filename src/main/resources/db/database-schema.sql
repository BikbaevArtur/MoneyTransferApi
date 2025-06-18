CREATE TABLE IF NOT EXISTS users
(
    id               serial primary key,
    name             varchar(500) not null,
    date_of_birthday date         not null,
    password         varchar(500) not null check ( char_length(password) >= 8 )
);

CREATE TABLE IF NOT EXISTS account
(
    id      serial primary key,
    user_id bigint unique  not null,
    balance decimal(19, 2) not null check ( balance >= 0 ),
    initial_balance decimal (19,2) not null check ( balance >=0 ),
    foreign key (user_id) references users (id) on delete restrict
);

CREATE TABLE IF NOT EXISTS email_data
(
    id      serial primary key,
    user_id bigint              not null,
    email   varchar(200) unique not null,
    foreign key (user_id) references users (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS phone_data
(
    id      SERIAL PRIMARY KEY,
    user_id BIGINT      NOT NULL,
    phone   VARCHAR(13) NOT NULL UNIQUE,
    foreign key (user_id) references users(id) on delete cascade
);
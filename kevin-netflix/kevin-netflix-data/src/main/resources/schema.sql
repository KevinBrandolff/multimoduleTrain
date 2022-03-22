create table if not exists netflix_plan_kevin (
    id int not null primary key AUTO_INCREMENT,
    plan_name varchar(255) unique not null,
    value double not null
);

create table if not exists user_netflix_kevin (
    id int not null primary key AUTO_INCREMENT,
    username varchar(255),
    password varchar(255),
    role enum('ADMIN', 'USER'),
    id_netflix_plan int,
    constraint fk_id_netflix_plan foreign key ( id_netflix_plan ) references netflix_plan_kevin( id )
);


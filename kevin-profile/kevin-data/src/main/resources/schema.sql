create table if not exists profile_kevin (
    id int not null primary key AUTO_INCREMENT,
    name varchar(255),
    age int,
    interests varchar(255),
    created_at date,
    updated_at date
);

create table if not exists service_accounts_kevin (
    id int not null primary key AUTO_INCREMENT,
    service varchar(255),
    plan varchar(255),
    username varchar(255),
    password varchar(255),
    id_user_netflix int,
    id_profile int,
    constraint fk_id_profile foreign key ( id_profile ) references profile_kevin( id )
);

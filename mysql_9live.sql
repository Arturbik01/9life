create database minecraft;
use minecraft;
create table lives(
	id int primary key auto_increment,
    nickname varchar(32) unique not null,
    count_lives int default 9
);
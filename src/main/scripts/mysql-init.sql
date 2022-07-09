-- drop existed database & users
drop database if exists beerservice;
drop user if exists `beer_service`@`%`;


-- create database for beer service
create database if not exists beerservice character set utf8mb4 collate utf8mb4_unicode_ci;

-- create a user for managing beer service
create user if not exists `beer_service`@`%` identified with mysql_native_password by 'mysqlpw';

-- grant user privileges
grant select, insert, update, delete, create, drop, references, index, alter, execute, create view, show view,
create routine, alter routine, event, trigger on `beerservice`.* to `beer_service`@`%`;


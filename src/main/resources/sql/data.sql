
drop table if exists broker_error_logs;
drop table if exists sensor_error_logs;
drop table if exists topics;
drop table if exists sensors;
drop table if exists brokers;

drop table if exists topic_types;
drop table if exists sensor_types;
drop table if exists protocol_types;

create table topic_types (
    topic_type_id integer primary key auto_increment,
    topic_type varchar(255) not null
);

create table sensor_types (
    sensor_type_id integer primary key auto_increment,
    sensor_type varchar(255) not null
);

create table protocol_types (
    protocol_type_id integer primary key auto_increment,
    protocol_type varchar(255) not null
);

create table brokers (
    broker_id int primary key auto_increment,
    broker_ip varchar(255) not null,
    broker_port int not null,
    broker_name varchar(255) not null,
    protocol_type varchar(255) not null,
    foreign key (protocol_type) references protocol_types(protocol_type)
);

create table sensors (
    sensor_id int primary key auto_increment,
    broker_id int not null,
    sensor_name varchar(255) not null,
    sensor_registered_at date,
    sensor_type varchar(255) not null,
    foreign key (broker_id) references brokers(broker_id) on delete cascade
);

create table topics (
    topic_id int primary key auto_increment,
    topic varchar(255),
    topic_registered_at date,
    sensor_id int not null,
    topic_type varchar(255) not null,
    foreign key (sensor_id) references sensors(sensor_id) on delete cascade
);

create table sensor_error_logs (
    sensor_error_log_id int primary key auto_increment,
    sensor_error_type varchar(255) not null,
    sensor_error_created_at datetime not null,
    sensor_error_value double not null,
    sensor_id int not null,
    topic_id int,
    foreign key (sensor_id) references sensors(sensor_id) on delete cascade,
    foreign key (topic_id) references topics(topic_id) on delete cascade
);

create table broker_error_logs (
    broker_error_log_id int primary key auto_increment,
    broker_id int not null,
    broker_error_type varchar(255) not null,
    broker_error_created_at datetime not null,
    broker_error_solved_at datetime,
    foreign key (broker_id) references brokers(broker_id) on delete cascade
);

insert into topic_types values (null, '전력');
insert into topic_types values (null, '전압');

insert into sensor_types values (null, '전기');
insert into sensor_types values (null, '온도');

insert into protocol_types values (null, 'MQTT');

insert into brokers values (null, '133.186.153.19', 1883, '전력', 'MQTT');

insert into sensors values (null, 1, 'test','2024-04-21','전기');

insert into topics values (null,'#',null,1,'전력');

insert into sensor_error_logs values (null, '전압', '2024-04-21 00:00:00', 100.0, 1, 1);
insert into broker_error_logs values (null, 1, '전압', '2024-04-21 00:00:00', '2024-04-21 00:00:00');
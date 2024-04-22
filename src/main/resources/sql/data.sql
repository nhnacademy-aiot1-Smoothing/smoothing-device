
drop table if exists topics;
drop table if exists sensors;
drop table if exists brokers;

drop table if exists topic_types;
drop table if exists sensor_types;
drop table if exists protocol_types;

create table topic_types (
    topic_type varchar(255) not null primary key
);

create table sensor_types (
    sensor_type varchar(255) not null primary key
);

create table protocol_types (
    protocol_type varchar(255) not null primary key
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
    foreign key (broker_id) references brokers(broker_id)
);

create table topics (
    topic varchar(255) primary key,
    sensor_id int not null,
    topic_type varchar(255) not null,
    foreign key (sensor_id) references sensors(sensor_id)
);

insert into topic_types values ('haha');

insert into sensor_types values ('haha');

insert into protocol_types values ('MQTT');

insert into brokers values (null, '133.186.153.19', 1883, 'test', 'MQTT');

insert into sensors values (null, 1, 'test',null,'haha');

insert into topics values ('#',1,'haha');
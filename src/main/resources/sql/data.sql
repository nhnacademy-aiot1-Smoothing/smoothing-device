drop table if exists sensor_tags;
drop table if exists tags;
drop table if exists users;

drop table if exists broker_error_logs;
drop table if exists sensor_error_logs;
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

create table users (
    user_id varchar(255) primary key,
    user_password varchar(255) not null,
    user_name varchar(255) not null,
    user_email varchar(255) not null,
    user_state varchar(20) not null,
    last_access date null
);

create table tags (
      tag_id int primary key auto_increment,
      user_id varchar(255) not null,
      tag_name varchar(255) not null,
      foreign key (user_id) references users(user_id) on delete cascade
);

create table sensor_tags (
    sensor_tag_id int primary key auto_increment,
    tag_id int not null,
    sensor_id int not null,
    foreign key (tag_id) references tags(tag_id) on delete cascade,
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

insert into topic_types values ('전력');
insert into topic_types values ('전압');
insert into topic_types values ('전력량');
insert into topic_types values ('온도');
insert into topic_types values ('습도');
insert into topic_types values ('co2');

insert into sensor_types values ('전기');
insert into sensor_types values ('온도');
insert into sensor_types values ('습도');
insert into sensor_types values ('co2');

insert into protocol_types values ('MQTT');

insert into brokers values (1, '133.186.153.19', 1883, 'NHN Academy', 'MQTT');

insert into sensors values (1, 1, '에어컨',null,'전기');
insert into topics values (null, 'data/s/nhnacademy/b/gyeongnam/p/office/d/gems-3500/e/electrical_energy/t/air_conditioner/ph/total/de/w',null,1,'전력');
insert into topics values (null, 'data/s/nhnacademy/b/gyeongnam/p/office/d/gems-3500/e/electrical_energy/t/air_conditioner/ph/kwh/de/sum',null,1,'전력량');

insert into sensors values (2, 1, '복사기',null,'전기');
insert into topics values (null, 'data/s/nhnacademy/b/gyeongnam/p/office/d/gems-3500/e/electrical_energy/t/office_copier/ph/total/de/w',null,2,'전력');
insert into topics values (null, 'data/s/nhnacademy/b/gyeongnam/p/office/d/gems-3500/e/electrical_energy/t/office_copier/ph/kwh/de/sum',null,2,'전력량');

insert into users values ('haha', '1234', 'admin', 'test@gmail.com', 'ACTIVE', null);
insert into tags values (1, 'haha', 'NHN');
insert into tags values (2, 'haha', 'Office');
insert into sensor_tags values (1, 1, 1);
insert into sensor_tags values (2, 2, 2);
insert into sensor_tags values (3, 1, 2);
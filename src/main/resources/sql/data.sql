create table `user` (
    `id` serial primary key,
    `name` varchar(255) not null,
    `email` varchar(255) not null,
    `password` varchar(255) not null
);
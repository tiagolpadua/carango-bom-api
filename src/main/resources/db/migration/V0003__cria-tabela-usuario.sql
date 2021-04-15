create table usuario (
    username varchar(50) not null,
    password varchar(30) not null,
    primary key (username)
) engine=innodb default charset=latin1;
create table marca (
    id bigint(20) not null auto_increment,
    nome varchar(255) not null,
    primary key (id),
    unique(nome)
) engine=innodb default charset=latin1;
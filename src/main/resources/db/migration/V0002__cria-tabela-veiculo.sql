create table veiculo (
    id bigint(20) not null auto_increment,
    modelo varchar(255) not null,
    ano int not null,
    valor decimal(12,2) not null,
    marca_id bigint(20) not null,
    primary key (id),
    constraint veiculo_marca foreign key (marca_id) references marca(id)
) engine=innodb default charset=latin1;
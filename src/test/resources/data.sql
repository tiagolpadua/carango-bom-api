/* Carga de Marcas */
insert into marca (nome) values ('Fiat');
insert into marca (nome) values ('Volkswagen');
insert into marca (nome) values ('GM');
insert into marca (nome) values ('Ford');
insert into marca (nome) values ('Audi');
insert into marca (nome) values ('Mercedes');
insert into marca (nome) values ('BMW');

/* Veículos da Fiat */
insert into veiculo (modelo, ano, valor, marca_id) values ('Palio', 2015, 25000.00, 1);
insert into veiculo (modelo, ano, valor, marca_id) values ('Argo', 2021, 50000.00, 1);

/* Veículos da Volksvagen */
insert into veiculo (modelo, ano, valor, marca_id) values ('Polo', 2021, 78000.00, 2);
insert into veiculo (modelo, ano, valor, marca_id) values ('Jetta', 2021, 110000.00, 2);

/* Veículos da Audi */
insert into veiculo (modelo, ano, valor, marca_id) values ('Q3', 2019, 189000.00, 6);
insert into veiculo (modelo, ano, valor, marca_id) values ('A4', 2020, 145000.00, 6);
insert into veiculo (modelo, ano, valor, marca_id) values ('A3', 2017, 122000.00, 6);

/* Veículos da Mercedes */
insert into veiculo (modelo, ano, valor, marca_id) values ('GLA 250', 2018, 250000.00, 7);
insert into veiculo (modelo, ano, valor, marca_id) values ('A200', 2020, 153000.00, 7);

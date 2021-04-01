package br.com.caelum.carangobom.testcontainer;

import org.testcontainers.containers.MySQLContainer;

public class MySQLCustomContainer extends MySQLContainer<MySQLCustomContainer> {

    private static MySQLCustomContainer instancia;

    private MySQLCustomContainer(String imagem) {
        super(imagem);
        withEnv("MYSQL_ROOT_PASSWORD", "root");
    }

    public static synchronized MySQLCustomContainer getInstance() {
        if (instancia == null) {
            instancia = new MySQLCustomContainer("mysql:8");
        }

        return instancia;
    }

    public static synchronized MySQLCustomContainer getInstance(String imagem) {
        if (instancia == null) {
            instancia = new MySQLCustomContainer(imagem);
        }

        return instancia;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", getJdbcUrl());
        System.setProperty("DB_PASSWORD", getPassword());
    }

    @Override
    public void stop() {

    }
}

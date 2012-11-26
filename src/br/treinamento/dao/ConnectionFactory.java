package br.treinamento.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static Connection conn;

    public static Connection getConnection() throws Exception {
        String localBanco = System.getProperty("user.dir") + File.separator + "banco" + File.separator + "entidade";
        Class.forName("org.hsqldb.jdbcDriver");
        conn = DriverManager.getConnection("jdbc:hsqldb:file:" + localBanco, "sa", "");
        return conn;
    }
}

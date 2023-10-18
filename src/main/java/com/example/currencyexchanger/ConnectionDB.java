package com.example.currencyexchanger;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private final String driver = "org.sqlite.JDBC";
    private Connection connection = null;

    public ConnectionDB() {
        try {
            URL resource = ConnectionDB.class.getClassLoader().getResource("identifier.sqlite");
            String path = null;
            try {
                path = new File(resource.toURI()).getAbsolutePath();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            Class.forName(driver);
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", path));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    public Connection getConnection() {
        return connection;
    }
    public void closeDB() throws SQLException {
        connection.close();
    }
}

package com.arturbik.database;

import com.arturbik.config.LoadProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseFactory {
    private Connection connection;
    private Statement statement;
    private static DatabaseFactory databaseFactory = DatabaseFactory.getInstance();

    public DatabaseFactory(String path, String user, String password){
        try {
             connection = DriverManager.getConnection(path, user, password);
             statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static DatabaseFactory getInstance(){
        if (databaseFactory == null){
            String path = LoadProperties.getInstance().getPath();
            String user = LoadProperties.getInstance().getUser();
            String password = LoadProperties.getInstance().getPassword();

            databaseFactory = new DatabaseFactory(path, user, password);
        }
        return databaseFactory;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public static DatabaseFactory getDatabaseFactory() {
        return databaseFactory;
    }

    public static void setDatabaseFactory(DatabaseFactory databaseFactory) {
        DatabaseFactory.databaseFactory = databaseFactory;
    }
}

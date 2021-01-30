package com.arturbik.config;

import com.arturbik.database.DatabaseFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {
    private String path;
    private String password;
    private String user;
    private String table;

    private String nameColumnId;
    private String nameColumnNickname;
    private String nameColumnCountLives;

    private static LoadProperties loadProperties;

    public LoadProperties(Properties properties){
        //LOAD MAIN CONFIG
        setPath(properties.getProperty("path"));
        setPassword(properties.getProperty("password"));
        setUser(properties.getProperty("user"));
        setTable(properties.getProperty("table"));

        //LOAD CONFIG
        setNameColumnId(properties.getProperty("id", "id"));
        setNameColumnNickname(properties.getProperty("nickname", "nickname"));
        setNameColumnCountLives(properties.getProperty("count_lives", "count_lives"));
    }

    public static LoadProperties getInstance(){
        if (loadProperties == null){
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(new File("dbConnect.properties")));

            } catch (IOException e) {
                e.printStackTrace();
            }
            loadProperties = new LoadProperties(properties);
        }
        return loadProperties;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getNameColumnId() {
        return nameColumnId;
    }

    public void setNameColumnId(String nameColumnId) {
        this.nameColumnId = nameColumnId;
    }

    public String getNameColumnNickname() {
        return nameColumnNickname;
    }

    public void setNameColumnNickname(String nameColumnNickname) {
        this.nameColumnNickname = nameColumnNickname;
    }

    public String getNameColumnCountLives() {
        return nameColumnCountLives;
    }

    public void setNameColumnCountLives(String nameColumnCountLives) {
        this.nameColumnCountLives = nameColumnCountLives;
    }
}

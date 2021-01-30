package com.arturbik.database;

import com.arturbik.config.LoadProperties;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController{
    private static Player player;
    private static DatabaseFactory databaseFactory;
    private static ResultSet resultSet;
    private static String nameColumnNickname;
    private static String nameColumnTable;
    private static String mainQuery;

    public DatabaseController(Player player, DatabaseFactory databaseFactory){
        DatabaseController.player = player;
        DatabaseController.databaseFactory = databaseFactory;
        nameColumnNickname = LoadProperties.getInstance().getNameColumnNickname();
        nameColumnTable = LoadProperties.getInstance().getTable();

        mainQuery = String.format("SELECT * FROM %s WHERE %s = '%s'",
                LoadProperties.getInstance().getTable(),
                LoadProperties.getInstance().getNameColumnNickname(),
                player.getName());

        try {
            if (isExist()) {
                resultSet = databaseFactory
                        .getStatement()
                        .executeQuery(
                                String.format("SELECT * FROM %s WHERE %s = '%s'",
                                        LoadProperties.getInstance().getTable(),
                                        LoadProperties.getInstance().getNameColumnNickname(),
                                        player.getName()));
                resultSet.next();
                if (resultSet.getString(LoadProperties.getInstance().getNameColumnNickname()).isEmpty()) {
                    Bukkit.getServer().getLogger().warning("Значение не найдено");
                    throw new NullPointerException();
                }
            }else {
                registerPlayer();
            }
            } catch(SQLException exception){
                exception.printStackTrace();
            }

    }



    public int getLives(){
        try {
            ResultSet resultSet = DatabaseFactory.getInstance().getStatement().executeQuery(mainQuery);
            resultSet.next();
            return resultSet.getInt(LoadProperties.getInstance().getNameColumnCountLives());
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new NullPointerException();
        }
    }



    public boolean isExist(){
        Statement checkPlayer = null;
        ResultSet resultCheckPlayer;
        try {
            checkPlayer = DatabaseFactory.getInstance().getConnection().createStatement();
            resultCheckPlayer = checkPlayer
                    .executeQuery(String.format("SELECT EXISTS (SELECT %s FROM %s WHERE %s = '%s');",
                            LoadProperties.getInstance().getNameColumnNickname(),
                            LoadProperties.getInstance().getTable(),
                            LoadProperties.getInstance().getNameColumnNickname(),
                            player.getName()));
            resultCheckPlayer.next();
            return resultCheckPlayer.getObject(1, Boolean.TYPE);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }


    public boolean registerPlayer() {
        Statement checkPlayer = null;
        try {
            checkPlayer = DatabaseFactory.getInstance().getConnection().createStatement();
            checkPlayer.execute(String.format("INSERT %s(%s) VALUE ('%s')",
                    LoadProperties.getInstance().getTable(),
                    LoadProperties.getInstance().getNameColumnNickname(),
                    player.getName()));
            player.sendMessage("Вы зарегестрированы. У вас 9 жизней");
            return isExist();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public void decrementLives(){
        try {
            Statement statement = databaseFactory.getConnection().createStatement();
            statement.execute(String.format("UPDATE %s SET %s = %s-1 WHERE %s = '%s'",
                    LoadProperties.getInstance().getTable(),
                    LoadProperties.getInstance().getNameColumnCountLives(),
                    LoadProperties.getInstance().getNameColumnCountLives(),
                    LoadProperties.getInstance().getNameColumnNickname(),
                    player.getName()));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void addLives(int count){
        try {
            Statement statement = databaseFactory.getConnection().createStatement();
            statement.execute(String.format("UPDATE %s SET %s = %s+%d WHERE %s = '%s'",
                    LoadProperties.getInstance().getTable(),
                    LoadProperties.getInstance().getNameColumnCountLives(),
                    LoadProperties.getInstance().getNameColumnCountLives(),
                    count,
                    LoadProperties.getInstance().getNameColumnNickname(),
                    player.getName()));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void removeLives(int count){
        try {
            Statement statement = databaseFactory.getConnection().createStatement();
            statement.execute(String.format("UPDATE %s SET %s = %s-%d WHERE %s = '%s'",
                    LoadProperties.getInstance().getTable(),
                    LoadProperties.getInstance().getNameColumnCountLives(),
                    LoadProperties.getInstance().getNameColumnCountLives(),
                    count,
                    LoadProperties.getInstance().getNameColumnNickname(),
                    player.getName()));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void setLives(int count){
        try {
            Statement statement = databaseFactory.getConnection().createStatement();
            statement.execute(String.format("UPDATE %s SET %s = %d WHERE %s = '%s'",
                    LoadProperties.getInstance().getTable(),
                    LoadProperties.getInstance().getNameColumnCountLives(),
                    count,
                    LoadProperties.getInstance().getNameColumnNickname(),
                    player.getName()));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}

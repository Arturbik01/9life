package com.arturbik;

import com.arturbik.config.LoadProperties;
import com.arturbik.database.DatabaseFactory;
import com.arturbik.event.DeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class Live extends JavaPlugin {
    private LiveCommand liveCommand = new LiveCommand();
    private static Properties properties = new Properties();
    private static DatabaseFactory databaseFactory;


    @Override
    public void onEnable() {

        databaseFactory = DatabaseFactory.getInstance();
        Bukkit.getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        getCommand("live").setExecutor(liveCommand);
        getCommand("live").setTabCompleter(new TabCompleter() {
            @Override
            public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
                List<String> list = new ArrayList<String>();

                if (args.length == 1){
                    return null;
                }
                if (args.length == 2){
                    list.add("set");
                    list.add("get");
                    list.add("add");
                    list.add("remove");
                    return list;
                }
                if (args.length == 3){
                    list.add("[number]");
                    return list;
                }
                return list;
            }
        });

    }


    @Override
    public void onDisable() {

    }

    public LiveCommand getLifeCommand() {
        return liveCommand;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static DatabaseFactory getDatabaseConnection() {
        return databaseFactory;
    }
}

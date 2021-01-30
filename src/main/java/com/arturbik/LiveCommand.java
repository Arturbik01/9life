package com.arturbik;

import com.arturbik.database.DatabaseController;
import com.arturbik.database.DatabaseFactory;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class LiveCommand implements CommandExecutor {
    private Player target;
    private String action;
    private int count;
    private static DatabaseController controller;
    private final Effect NEGATIVE_EFFECT = Effect.ENDEREYE_LAUNCH;
    private final Effect POSITIVE_EFFECT = Effect.VILLAGER_PLANT_GROW;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2){
            target = Bukkit.getPlayer(args[0]);
            controller = new DatabaseController(target, DatabaseFactory.getInstance());
            action = args[1];
        }else if (args.length >= 3){
            target = Bukkit.getPlayer(args[0]);
            controller = new DatabaseController(target, DatabaseFactory.getInstance());
            action = args[1];
            try {
                count = Integer.parseInt(args[2]);
            }catch (NumberFormatException e){
                return false;
            }
        }else {
            return false;
        }
        if (controller == null && controller.isExist() ){
            return false;
        }
        switch (action){
            case "get":
                getAction(sender, target);
                break;
            case "set":
                setAction(sender, target, count);
                break;
            case "add":
                addAction(sender, target, count);
                break;
            case "remove":
                removeAction(sender, target, count);
                break;
            default:
                return false;
        }
        return true;
    }

    private void removeAction(CommandSender sender, Player target, int count) {
        controller.removeLives(count);
        if (controller.getLives() <= 0){
            target.setGameMode(GameMode.SPECTATOR);
        }else if (controller.getLives() > 0 & target.getGameMode() == GameMode.SPECTATOR){
            target.setGameMode(GameMode.SURVIVAL);
        }

        sender.sendMessage("Теперь у " + ChatColor.GREEN + target.getName() + " " + ChatColor.RED
                + controller.getLives() + ChatColor.WHITE + " жизней");
        target.sendMessage("У вас забрали " + ChatColor.RED
                + controller.getLives() + ChatColor.WHITE + " жизней");
    }

    private void addAction(CommandSender sender, Player target, int count) {
        controller.addLives(count);
        if (controller.getLives() <= 0){
            target.setGameMode(GameMode.SPECTATOR);
        }else if (controller.getLives() > 0 & target.getGameMode() == GameMode.SPECTATOR){
            target.setGameMode(GameMode.SURVIVAL);
        }

        sender.sendMessage("Теперь у " + ChatColor.GREEN + target.getName() + " " + ChatColor.RED
                + controller.getLives() + ChatColor.WHITE + " жизней");
        target.sendMessage("Вам добавили " + ChatColor.RED
                + controller.getLives() + ChatColor.WHITE + " жизней");
    }

    private void setAction(CommandSender sender, Player target, int count) {
        controller.setLives(count);
        if (controller.getLives() <= 0){
            target.setGameMode(GameMode.SPECTATOR);
        }else if (controller.getLives() > 0 & target.getGameMode() == GameMode.SPECTATOR){
            target.setGameMode(GameMode.SURVIVAL);
        }

        sender.sendMessage("Теперь у " + ChatColor.GREEN + target.getName() + " " + ChatColor.RED
                + controller.getLives() + ChatColor.WHITE + " жизней");
        target.sendMessage("Вам установили " + ChatColor.RED
                + controller.getLives() + ChatColor.WHITE + " жизней");
        target.getWorld().strikeLightningEffect(target.getLocation());
    }

    private void getAction(CommandSender sender, Player player){
        sender.sendMessage("У " + ChatColor.GREEN + player.getName() + " " + ChatColor.RED
                + controller.getLives() + ChatColor.WHITE + " жизней");
    }

}

package com.arturbik.event;

import com.arturbik.database.DatabaseController;
import com.arturbik.database.DatabaseFactory;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class DeathEvent implements Listener {


    @EventHandler
    public void calcDeathEvent(PlayerDeathEvent event){
        Player player = event.getEntity();
        DatabaseController controller = new DatabaseController(player, DatabaseFactory.getInstance());
        if (controller.isExist()) {
            controller.decrementLives();
            if (controller.getLives() <= 0) {
                event.setDeathMessage("Вы потратили все жизни. Вы будете находится на сервере в режиме спектатора");
                player.setGameMode(GameMode.SPECTATOR);
            }else {
                event.setDeathMessage(String.format("У вас осталось %d жизней", controller.getLives()));
            }
        }else {
            event.setDeathMessage("Просто умер.. Жалуйся разработчику, если это произошло");
        }
        player.getWorld().strikeLightningEffect(player.getLocation());
    }

    @EventHandler
    public void PlayerJoinHandler(PlayerJoinEvent event){
        Player player = event.getPlayer();
        double MAX_HEALTH = 2.0;
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(MAX_HEALTH);
        DatabaseController controller = new DatabaseController(player, DatabaseFactory.getInstance());
        try {
            if (controller.isExist()){
                player.sendMessage(String.format("Добро пожаловать, %s. Напоминаем, что у вас %d жизней",
                        player.getName(),
                        controller.getLives()));
            }else {
                if (controller.registerPlayer()) {
                    player.sendMessage(String.format("Вы зарегестрированы. Вам выдано %d жизней", 9));
                }else {
                    player.sendMessage("Проблема. Вы не зарегестрированы. Жалуйтесь.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}

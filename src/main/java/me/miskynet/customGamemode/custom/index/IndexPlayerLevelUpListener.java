package me.miskynet.customGamemode.custom.index;

import me.miskynet.customGamemode.custom.config.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class IndexPlayerLevelUpListener implements Listener {

    @EventHandler
    public void playerLevelUp(PlayerLevelChangeEvent event) {

        Player player = event.getPlayer();

        if (event.getNewLevel() > event.getOldLevel()) {

            Integer newLevel = event.getNewLevel() - event.getOldLevel();
            Integer currentXP = (Integer) PlayerData.get(PlayerData.FileType.INDEX, player.getUniqueId()).get("currentXP");
            PlayerData.get(PlayerData.FileType.INDEX, player.getUniqueId()).set("currentXP", currentXP + newLevel);
            PlayerData.save(PlayerData.FileType.INDEX, player.getUniqueId());

            Integer currentLevel = (Integer) PlayerData.get(PlayerData.FileType.INDEX, player.getUniqueId()).get("currentLevel");
            if (currentXP >= currentLevel) {
                PlayerData.get(PlayerData.FileType.INDEX, player.getUniqueId()).set("currentLevel", newLevel);
                PlayerData.get(PlayerData.FileType.INDEX, player.getUniqueId()).set("currentXP", 0);
                PlayerData.save(PlayerData.FileType.INDEX, player.getUniqueId());
            }
        }
    }

}

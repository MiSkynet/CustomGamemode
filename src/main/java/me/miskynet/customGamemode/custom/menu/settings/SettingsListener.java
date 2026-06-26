package me.miskynet.customGamemode.custom.menu.settings;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.scoreboard.ScoreboardManager;
import me.miskynet.customGamemode.utils.Utils;
import me.miskynet.customGamemode.utils.customConfig.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SettingsListener implements Listener {

    /**
     * Listener to check for clicks in the {@link SettingsMenu}
     * */
    @EventHandler
    public void clickListener(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof SettingsMenu settingsMenu) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            if (!Utils.checkForAllowedClick(player)) return;

            if (event.getSlot() == 10) {

                if ((Boolean) PlayerData.get(PlayerData.FileType.SETTINGS, player.getUniqueId(), "settings.scoreboardStatus")) {
                    PlayerData.set(PlayerData.FileType.SETTINGS, player.getUniqueId(), "settings.scoreboardStatus", false);
                    settingsMenu.buildSettingsPage(player);
                }else {
                    PlayerData.set(PlayerData.FileType.SETTINGS, player.getUniqueId(), "settings.scoreboardStatus", true);
                    settingsMenu.buildSettingsPage(player);
                }
                Main.scoreboardManager.updateScoreboard(player);
            }

            Utils.createClickCooldown(player);
        }
    }

}

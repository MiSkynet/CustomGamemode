package me.miskynet.customGamemode.custom.menu.settings;

import me.miskynet.customGamemode.utils.Utils;
import me.miskynet.customGamemode.utils.customConfig.PlayerSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SettingsListener implements Listener {

    @EventHandler
    public void clickListener(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof SettingsMenu settingsMenu) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            if (event.getSlot() == 10) {

                if ((Boolean) PlayerSettings.get(player, "settings.scoreboardStatus")) {
                    PlayerSettings.set((Player) event.getWhoClicked(), "settings.scoreboardStatus", false);
                    settingsMenu.buildSettingsPage(player);
                }else {
                    PlayerSettings.set((Player) event.getWhoClicked(), "settings.scoreboardStatus", true);
                    settingsMenu.buildSettingsPage(player);
                }
            }
        }
    }

}

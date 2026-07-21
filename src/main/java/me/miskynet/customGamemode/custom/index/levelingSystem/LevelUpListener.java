package me.miskynet.customGamemode.custom.index.levelingSystem;

import me.miskynet.customGamemode.utils.ComponentUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LevelUpListener implements Listener {

    @EventHandler
    public void onLevelUp(PlayerLevelUpEvent event) {
        event.getPlayer().sendMessage(ComponentUtils.component("&aYou leveled up to level &e" + event.getNewLevel() + "&a!"));
        event.getPlayer().sendMessage(ComponentUtils.component("&aYou can now claim the rewards in the index for your new level!"));
    }

}

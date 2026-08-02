package me.miskynet.customGamemode.experimental.structures.overworld;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class StructurePlaceListener implements Listener {

    /**
     * Listener for the {@link WorldInitEvent} that adds the custom structures into the world
     * */
    @EventHandler
    public void onWorldInit(WorldInitEvent event) {
        event.getWorld().getPopulators().add(new CustomTestStructure());
    }

}

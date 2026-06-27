package me.miskynet.customGamemode.custom.entity.npc;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

public class NPCMoveEvent implements Listener {

    // cancel the move event so the NPC can't be moved
    @EventHandler
    public void move(EntityMoveEvent event) {
        if (event.getEntity().getPersistentDataContainer().get(NPC.interactTypeKey, PersistentDataType.STRING) != null) {
            event.setCancelled(true);
        }
    }
}

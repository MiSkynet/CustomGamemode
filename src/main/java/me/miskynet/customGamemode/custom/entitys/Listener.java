package me.miskynet.customGamemode.custom.entitys;

import me.miskynet.customGamemode.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.persistence.PersistentDataType;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void entityClick(PlayerInteractAtEntityEvent event) {

        NamespacedKey customEntityIdNamespaceKey = new NamespacedKey(Main.getInstance(), "custom_entity_id");
        /*
        * 26001 -> custom_entity_id set in the InteractEntity class
        * */
        if (event.getRightClicked().getPersistentDataContainer().get(customEntityIdNamespaceKey, PersistentDataType.INTEGER) == 26001) {
            InteractEntity entity = (InteractEntity) event.getRightClicked();
            entity.getInteractionMenu().openForPlayer(event.getPlayer());
        }

    }

}

package me.miskynet.customGamemode.custom.entitys;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.menu.Menu;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class InteractEntity {

    private EntityType entityType;
    private Component customName = null;
    private Menu menu = null;
    private final Integer CUSTOM_ENTITY_ID = 26001;

    public InteractEntity(EntityType entityType) {
        this.entityType = entityType;
    }

    public void setCustomName(Component customName) {
        this.customName = customName;
    }

    public void setInteractionMenu(Menu menu) {
        this.menu = menu;
    }

    public Menu getInteractionMenu() {
        return this.menu;
    }

    public Entity spawnEntity(Player player) {
        Location spawnLocation = player.getLocation();
        Entity entity = Bukkit.getWorld(spawnLocation.getWorld().getKey()).spawnEntity(spawnLocation, this.entityType);

        entity.setSilent(true);
        entity.setInvulnerable(true);
        entity.setGravity(true);

        NamespacedKey customEntityIdNamespaceKey = new NamespacedKey(Main.getInstance(), "custom_entity_id");
        entity.getPersistentDataContainer().set(customEntityIdNamespaceKey, PersistentDataType.INTEGER, CUSTOM_ENTITY_ID);

        NamespacedKey customEntityIdNamespaceKey = new NamespacedKey(Main.getInstance(), "interaction_menu");
        entity.getPersistentDataContainer().set(customEntityIdNamespaceKey, PersistentDataType.);

        if (this.customName != null) entity.customName(this.customName);
        return entity;
    }
}

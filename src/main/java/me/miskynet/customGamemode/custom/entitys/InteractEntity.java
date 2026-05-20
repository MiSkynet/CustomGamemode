package me.miskynet.customGamemode.custom.entitys;

import me.miskynet.customGamemode.custom.menu.Menu;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class InteractEntity {

    private EntityType entityType;
    private Component customName = null;
    private Menu menu = null;

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

        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.setAI(false);
        }

        if (this.customName != null) entity.customName(this.customName);
        return entity;
    }
}

package me.miskynet.customGamemode.custom.entitys;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class CustomEntity {

    private EntityType entityType;
    private Component customName = null;
    private Boolean customNameVisible;
    private boolean silent = true;
    private boolean invulnerable = true;
    private boolean gravity = false;
    private boolean ai = false;

    /*
     * constructor
     * */
    public CustomEntity(EntityType entityType) {
        this.entityType = entityType;
    }

    /*
     * setter, getter and everything else
     * */
    public void setCustomName(Component customName) {
        this.customName = customName;
    }

    public CustomEntity customNameVisible(Boolean visible) {
        this.customNameVisible = visible;
        return this;
    }

    public CustomEntity setSilent(boolean silent) {
        this.silent = true;
        return this;
    }

    public boolean isSilent() {
        return silent;
    }

    public CustomEntity setInvulnereable(boolean invulnerable) {
        this.invulnerable = invulnerable;
        return this;
    }

    public boolean isInvulnerable() {
        return this.invulnerable;
    }

    public CustomEntity setGravity(boolean gravity) {
        this.gravity = gravity;
        return this;
    }

    public boolean hasGravity() {
        return this.gravity;
    }

    public CustomEntity setAi(boolean ai) {
        this.ai = ai;
        return this;
    }

    public boolean hasAi() {
        return this.ai;
    }

    public Entity spawnEntity(Player player) {
        Location spawnLocation = player.getLocation();
        Entity entity = Bukkit.getWorld(spawnLocation.getWorld().getKey()).spawnEntity(spawnLocation, this.entityType);

        entity.setSilent(silent);
        entity.setInvulnerable(invulnerable);
        entity.setGravity(gravity);

        if (!(this.ai)) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.setAI(this.ai);
            }
        }

        if (this.customName != null) entity.customName(this.customName);
        if (this.customNameVisible != null) entity.setCustomNameVisible(this.customNameVisible);

        return entity;
    }

}

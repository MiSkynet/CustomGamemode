package me.miskynet.customGamemode.custom.entitys;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.menu.Menu;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MenuEntity extends CustomEntity {

    private Menu menu;

    public MenuEntity(EntityType entityType, Menu menu) {
        super(entityType);
        this.menu = menu;
    }

    public void setInteractionMenu(Menu menu) {
        this.menu = menu;
    }

    public Menu getInteractionMenu() {
        return this.menu;
    }

    public Entity spawnEntity(Player player) {

        Entity entity = super.spawnEntity(player);

        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.setAI(false);
        }

        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        NamespacedKey menuLink = new NamespacedKey(Main.getInstance(), "entity_menu_link");
        pdc.set(menuLink, PersistentDataType.INTEGER, this.menu.getId());

        return entity;
    }
}

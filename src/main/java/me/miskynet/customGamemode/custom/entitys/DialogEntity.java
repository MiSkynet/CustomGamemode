package me.miskynet.customGamemode.custom.entitys;


import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class DialogEntity extends CustomEntity{

    HashMap<Integer, Component> dialogList = new HashMap<>();

    public DialogEntity(EntityType entityType) {
        super(entityType);
    }

    public DialogEntity setDialog(int dialogValue, Component dialog) {
        if (!dialogList.containsKey(dialogValue)) {
            this.dialogList.put(dialogValue, dialog);
        }
        return this;
    }

    public DialogEntity addDialog(Component dialog) {
        this.dialogList.put(this.dialogList.size(), dialog);
        return this;
    }

    public Component getDialog(int dialogValue) {
        return this.dialogList.get(dialogValue);
    }

    /**
     * @return Entity, not DialogEntity
     * */
    public Entity spawnEntity(Player player) {
        Entity entity = super.spawnEntity(player);

        return entity;
    }

}

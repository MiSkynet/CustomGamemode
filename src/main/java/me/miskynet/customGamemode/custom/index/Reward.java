package me.miskynet.customGamemode.custom.index;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.item.Item;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class Reward {

    private Object reward;
    private Component name = null;

    public Reward(Object reward, Component name) {
        this.reward = reward;
        this.name = name;
    }

    public Reward(Object reward) {
        this.reward = reward;
    }

    public void setName(Component name) {
        this.name = name;
    }

    public Component getName() {
        return this.name;
    }

    public Object getReward() {
        return this.reward;
    }

    public Boolean giveToPlayer(Player player) {
        if (reward instanceof Item item) {
            player.getInventory().addItem(item.toItemStack());
            return true;
        }else if (reward instanceof Integer level) {
            player.setLevel(player.getLevel() + level);
            return true;
        } else if (reward instanceof Double amount) {
            Main.economyManager.addBalance(player, amount);
            return true;
        }
        return false;
    }

}

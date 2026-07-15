package me.miskynet.customGamemode.custom.index.utils;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.index.IndexMenu;
import me.miskynet.customGamemode.custom.item.Item;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class Reward {

    private Object reward;
    private Component name = null;

    /**
     * @param reward The reward that should be given to the player. Can be an {@link Item}, an {@link Integer} (for levels) or a {@link Double} (for money)
     * @param name The name of the reward as {@link Component}
     * */
    public Reward(Object reward, Component name) {
        this.reward = reward;
        this.name = name;
    }

    /**
     * @param reward The reward that should be given to the player. Can be an {@link Item}, an {@link Integer} (for levels) or a {@link Double} (for money)
     * */
    public Reward(Object reward) {
        this.reward = reward;
    }

    /**
     * Sets the name of the reward as {@link Component} that is being displayed in the {@link IndexMenu}
     *
     * @param name The name of the reward as {@link Component}
     * */
    public void setName(Component name) {
        this.name = name;
    }

    /**
     * Gets the name of the reward as {@link Component}
     *
     * @return The name of the reward as {@link Component}
     * */
    public Component getName() {
        return this.name;
    }

    /**
     * Gets the reward that should be given to the player
     *
     * @return The reward as {@link Object}
     * */
    public Object getReward() {
        return this.reward;
    }

    /**
     * Gives the reward to the player.
     *
     * @param player The {@link Player} that should receive the reward
     * @return True if the reward was given successfully, false otherwise
     * */
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

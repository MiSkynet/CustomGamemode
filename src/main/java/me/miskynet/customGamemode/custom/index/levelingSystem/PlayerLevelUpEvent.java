package me.miskynet.customGamemode.custom.index.levelingSystem;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerLevelUpEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final int currentLevel;
    private final int newLevel;

    public PlayerLevelUpEvent(Player player, int currentLevel, int newLevel) {
        this.player = player;
        this.currentLevel = currentLevel;
        this.newLevel = newLevel;
    }

    /**
     * Gets the {@link Player} the event fired for.
     *
     * @return The {@link Player} that leveled up
     * */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Gets the current level of the player before leveling up
     *
     * @return The current level of the player
     * */
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    /**
     * Gets the new level of the player
     *
     * @return The new level of the player
     * */
    public int getNewLevel() {
        return this.newLevel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}

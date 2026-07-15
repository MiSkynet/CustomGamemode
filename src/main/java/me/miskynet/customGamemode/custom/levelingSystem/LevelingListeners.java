package me.miskynet.customGamemode.custom.levelingSystem;

import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityBreedEvent;

public class LevelingListeners implements Listener {

    private LevelingSystem levelingSystem = new LevelingSystem();

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        // if the broken block is a crop, add 5 xp, else add 2 xp
        if (event.getBlock().getBlockData() instanceof Ageable) levelingSystem.addPlayerXP(event.getPlayer(), 5);
        else levelingSystem.addPlayerXP(event.getPlayer(), 2);
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        // if the placed block is a crop, add 5 xp, else add 2 xp
        if (event.getBlock().getBlockData() instanceof Ageable) levelingSystem.addPlayerXP(event.getPlayer(), 5);
        else levelingSystem.addPlayerXP(event.getPlayer(), 2);
    }

    @EventHandler
    public void breed(EntityBreedEvent event) {

        Player player = (Player) event.getBreeder();

        levelingSystem.addPlayerXP(player, 5);
    }

}

package me.miskynet.customGamemode.custom.index.utils;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.config.PlayerData;
import me.miskynet.customGamemode.custom.economy.EconomyManager;
import me.miskynet.customGamemode.custom.index.IndexMenu;
import me.miskynet.customGamemode.custom.index.levelingSystem.IndexLevelingSystem;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.item.Relic;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.PDCUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class IndexMenuItem {

    private static final IndexLevelingSystem levelingSystem = Main.getInstance().getLevelingSystem();
    private static final EconomyManager economyManager = Main.getInstance().getEconomyManager();

    /**
     * Builds an {@link ItemStack} representing the reward for a given {@link IndexLevel} and player.
     *
     * @param level The {@link IndexLevel} for which the reward item is being built
     * @param player The player for whom the reward item is being built
     * @return An {@link ItemStack} representing the reward for the given level and player
     * */
    public static ItemStack buildRewardItemStack(IndexLevel level, Player player) {

        Item item;
        List<Component> lore = new ArrayList<>();

        List<Integer> unlockedLevels = PlayerData.get(PlayerData.FileType.INDEX, player.getUniqueId()).getIntegerList("unlockedLevels");

        Integer currentConfigPlayerLevel = levelingSystem.getPlayerLevel(player);

        // check weather a level is unlocked, not unlocked yet or locked
        if (unlockedLevels != null && unlockedLevels.contains(level.getLevel())) {
            item = new Item(Material.LIME_STAINED_GLASS_PANE, ComponentUtils.component(false, "&aLevel " + level.getLevel() + " &8(Unlocked)"));
        } else if (currentConfigPlayerLevel >= level.getLevel()) {
            item = new Item(Material.YELLOW_STAINED_GLASS_PANE, ComponentUtils.component(false, "&eLevel " + level.getLevel() + " &8(Locked)"));
            lore.add(ComponentUtils.component(false, "&eClick to unlock!"));
        }else {
            item = new Item(Material.RED_STAINED_GLASS_PANE, ComponentUtils.component(false, "&cLevel " + level.getLevel() + " &8(Locked)"));
        }

        lore.add(ComponentUtils.component(false, " "));
        lore.add(ComponentUtils.component(false, "&7You'll receive the following rewards"));
        lore.add(ComponentUtils.component(false, "&7when you reach level " + level.getLevel() + ":"));

        for (Reward reward : level.getRewards()) {
            if (reward.getReward() instanceof Item || reward.getReward() instanceof Relic) {
                Item rewardItem = (Item) reward.getReward();
                lore.add(ComponentUtils.component(false, "&7- &6" + ComponentUtils.fromComponent(rewardItem.toItemStack().getItemMeta().displayName())));
            } else if (reward.getReward() instanceof Integer) {
                lore.add(ComponentUtils.component(false, "&7- &6" + reward.getReward() + " Level"));
            } else if (reward.getReward() instanceof Double) {
                lore.add(ComponentUtils.component(false, "&7- &6" + reward.getReward() + economyManager.getEcoSymbol()));
            }
        }

        if (unlockedLevels != null && !(unlockedLevels.contains(level.getLevel()))) {
            lore.add(ComponentUtils.component(" "));
            lore.add(ComponentUtils.component(false, "&8Current Progress: " + levelingSystem.getPlayerLevel(player) + "/" + level.getRequiredLevel()));
        }

        item.setLore(lore);

        ItemStack finalItemStack = item.toItemStack();

        PDCUtils.setPDC(finalItemStack, IndexMenu.currentLevelKey, PersistentDataType.INTEGER, level.getLevel());

        return finalItemStack;
    }

}

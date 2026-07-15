package me.miskynet.customGamemode.custom.index.listener;

import me.miskynet.customGamemode.custom.config.PlayerData;
import me.miskynet.customGamemode.custom.index.IndexMenu;
import me.miskynet.customGamemode.custom.index.utils.IndexMenuItem;
import me.miskynet.customGamemode.custom.index.utils.IndexLevel;
import me.miskynet.customGamemode.custom.index.utils.Reward;
import me.miskynet.customGamemode.custom.levelingSystem.LevelingSystem;
import me.miskynet.customGamemode.custom.menu.TexturedScrollMenu;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.PDCUtils;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class IndexMenuListener implements Listener {

    @EventHandler
    public void invClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof IndexMenu indexMenu) {

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            // create a click cooldown to prevent the player from spamming
            if (!Utils.checkForAllowedClick(player)) return;
            Utils.createClickCooldown(player);

            // handle the next and previous page buttons
            if (event.getSlot() == event.getClickedInventory().getSize() - TexturedScrollMenu.nextPageButtonSlot) {
                indexMenu.increasePage();
                indexMenu.buildMenu(player);
                return;
            }

            if (event.getSlot() == event.getClickedInventory().getSize() - TexturedScrollMenu.previousPageButtonSlot) {
                indexMenu.decreasePage();
                indexMenu.buildMenu(player);
                return;
            }

            Integer clickedSlot = event.getSlot();

            ItemStack clickedStack = indexMenu.getItem(clickedSlot);

            if (event.getClickedInventory().getItem(clickedSlot) == null) return;

            if (PDCUtils.getPDC(clickedStack, IndexMenu.currentLevelKey, PersistentDataType.INTEGER) == null) return;

            Integer clickedLevel = (Integer) PDCUtils.getPDC(clickedStack, IndexMenu.currentLevelKey, PersistentDataType.INTEGER);

            IndexLevel indexLevel = IndexMenu.getLevelByNumber(clickedLevel);

            // check if the player has reached the level to unlock the reward
            if (new LevelingSystem().getPlayerLevel(player) < clickedLevel) {
                player.sendMessage(ComponentUtils.component("&cYou have not unlocked this level yet!"));
                return;
            }

            // check if the player has already unlocked and claimed the reward
            if (PlayerData.get(PlayerData.FileType.INDEX, player.getUniqueId()).getIntegerList("unlockedLevels").contains(clickedLevel)) {
                player.sendMessage(ComponentUtils.component("&cYou have already unlocked and claimed this reward!"));
                return;
            }

            // add the level to the player's unlocked levels
            List<Integer> unlockedLevelsList = PlayerData.get(PlayerData.FileType.INDEX, player.getUniqueId()).getIntegerList("unlockedLevels");
            unlockedLevelsList.add(clickedLevel);
            PlayerData.get(PlayerData.FileType.INDEX, player.getUniqueId()).set("unlockedLevels", unlockedLevelsList);
            PlayerData.save(PlayerData.FileType.INDEX, player.getUniqueId());

            // give the player all the rewards
            for (Reward reward : indexLevel.getRewards()) {
                reward.giveToPlayer(player);
            }

            ItemStack rewardItemStack = IndexMenuItem.buildRewardItemStack(indexLevel, player);
            indexMenu.setItem(clickedSlot, rewardItemStack);
            player.sendMessage(ComponentUtils.component("&aYou have claimed the reward for level " + clickedLevel + "!"));

        }
    }
}

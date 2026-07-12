package me.miskynet.customGamemode.custom.index;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.PlayerData;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.item.Relic;
import me.miskynet.customGamemode.custom.menu.Menu;
import me.miskynet.customGamemode.custom.menu.TexturedScrollMenu;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.Debugger;
import me.miskynet.customGamemode.utils.PDCUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class IndexMenu extends TexturedScrollMenu {

    public static ArrayList<IndexLevel> levels = new ArrayList<>();

    public static NamespacedKey currentLevelKey = new NamespacedKey(Main.getInstance(), "currentLevel");

    /**
     * @param title The title of the {@link Menu}
     * */
    public IndexMenu(Component title) {
        super(title, 54, "\uE003");
        Integer calcMaxPage = (levels.size() + this.getItemsPerPage() - 1) / this.getItemsPerPage();
        this.setMaxPage(calcMaxPage);
    }

    /**
     * Builds the {@link IndexMenu} for a given player, adding the navigation bar and reward items to the inventory.
     *
     * @param player The player for whom the menu is being built
     * */
    public void buildMenu(Player player) {
        this.getInventory().clear();
        this.addNavigationBar();
        addRewardItemsToInventory(player);
    }

    /**
     * Adds the reward items to the {@link IndexMenu} inventory
     * @param player The player the menu is being built for
     * */
    public void addRewardItemsToInventory(Player player) {
        int shopItemStart = this.getCurrentPage() * this.getItemsPerPage();
        int currentShopItem = shopItemStart;

        for (int i = 0; i < this.getInventory().getSize(); i++) {
            if (levels.size() <= currentShopItem) break;
            IndexLevel level = levels.get(currentShopItem);
            this.getInventory().addItem(buildRewardItemStack(level, player));
            currentShopItem++;
        }
    }

    /**
     * <strong>This list is only Temporary for testing!!</strong> <br>
     *
     * Creates a list of {@link IndexLevel} objects, each representing a level in the index.
     *
     * */
    public static void createRewardList() {

        for (int i = 1; i < 50; i++) {

            IndexLevel level = new IndexLevel(i, i);

            level.addReward(new Reward(5000.0d, ComponentUtils.component(false, "&7" + 5000 + "EXP")));

            if ((i % 15) == 0) {
                level.addReward(new Reward(new Relic(), ComponentUtils.component(false, "&6Relic")));
                level.addReward(new Reward(new Item(Material.GHAST_TEAR)));
            }

            if ((75 % i) == 0) {
                level.addReward(new Reward(100, ComponentUtils.component(false, "&7" + 100 + "EXP")));
            }

            levels.add(level);
        }
    }

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

        Integer currentConfigPlayerLevel = PlayerData.get(PlayerData.FileType.INDEX, player.getUniqueId()).getInt("currentLevel");

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
                lore.add(ComponentUtils.component(false, "&7- &6" + reward.getReward() + Main.economyManager.getEcoSymbol()));
            }
        }

        item.setLore(lore);

        ItemStack finalItemStack = item.toItemStack();

        PDCUtils.setPDC(finalItemStack, currentLevelKey, PersistentDataType.INTEGER, level.getLevel());

        return finalItemStack;
    }

    /**
     * Returns the index level for a given level number.
     *
     * @param levelNumber The level number to search for
     * @return The corresponding IndexLevel, or null if not found
     */
    public static IndexLevel getLevelByNumber(int levelNumber) {
        for (IndexLevel level : levels) {
            if (level.getLevel() == levelNumber) {
                return level;
            }
        }
        return null;
    }

}


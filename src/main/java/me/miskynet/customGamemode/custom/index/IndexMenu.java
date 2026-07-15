package me.miskynet.customGamemode.custom.index;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.index.utils.IndexLevel;
import me.miskynet.customGamemode.custom.index.utils.IndexMenuItem;
import me.miskynet.customGamemode.custom.index.utils.Reward;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.item.Relic;
import me.miskynet.customGamemode.custom.menu.Menu;
import me.miskynet.customGamemode.custom.menu.TexturedScrollMenu;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class IndexMenu extends TexturedScrollMenu {

    public static ArrayList<IndexLevel> levels = new ArrayList<>();

    public static NamespacedKey currentLevelKey = new NamespacedKey(Main.getInstance(), "currentLevel");

    public IndexMenu() {
        super(ComponentUtils.component("Index"), 54, "\uE007");
        Integer calcMaxPage = (levels.size() + this.getItemsPerPage() - 1) / this.getItemsPerPage();
        this.setMaxPage(calcMaxPage);
    }

    /**
     * Builds the {@link IndexMenu} for a given player, adding the navigation bar and reward items to the inventory.
     *
     * @param player The player for whom the menu is being built
     * */
    @Override
    public void buildMenu(Player player) {
        this.getInventory().clear();
        this.addNavigationBar();
        addRewardItemsToInventory(player);
    }

    /**
     * Adds the reward items to the {@link IndexMenu} inventory
     *
     * @param player The player the menu is being built for
     * */
    public void addRewardItemsToInventory(Player player) {
        int shopItemStart = this.getCurrentPage() * this.getItemsPerPage();
        int currentShopItem = shopItemStart;

        for (int i = 0; i < this.getInventory().getSize(); i++) {
            if (levels.size() <= currentShopItem) break;
            IndexLevel level = levels.get(currentShopItem);
            this.getInventory().addItem(IndexMenuItem.buildRewardItemStack(level, player));
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

        for (int i = 1; i <= 100; i++) {

            // calculate the required xp for the level using a formula
            int xpRequired = (int) (Math.pow(i, 1.40));

            IndexLevel level = new IndexLevel(i, xpRequired);

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


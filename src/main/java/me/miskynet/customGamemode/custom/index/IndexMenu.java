package me.miskynet.customGamemode.custom.index;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.PlayerData;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.item.Relic;
import me.miskynet.customGamemode.custom.menu.Menu;
import me.miskynet.customGamemode.utils.ComponentManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class IndexMenu extends Menu {

    private int currentPage;

    private ArrayList<IndexLevel> levels = new ArrayList<>();

    /**
     * @param title The title of the {@link Menu}
     * @param size  The size of the Inventory (A multiple of 9 and max 54)
     * */
    public IndexMenu(Component title, int size) {
        super(title, size);
        createRewardList();
    }

    public void buildMenu(Player player) {
        addRewardItems(player);
    }

    /**
     * Increases the current page of the {@link IndexMenu} by 1.
     * */
    public void increasePage() {
        currentPage++;
    }

    /**
     * Decreases the current page of the {@link IndexMenu} by 1, if the current page is greater than 0.
     * */
    public void decreasePage() {
        if (currentPage > 0) {
            currentPage--;
        }
    }

    /**
     * <strong>This list is only Temporary for testing!!</strong> <br>
     *
     * Creates a list of {@link IndexLevel} objects, each representing a level in the index.
     *
     * */
    public void createRewardList() {

        for (int i = 5; i < 50; i+=5) {

            IndexLevel level = new IndexLevel(i);

            level.addReward(new Reward(5000.0d, ComponentManager.component(false, "&7" + 5000 + "EXP")));

            if ((i % 15) == 0) {
                level.addReward(new Reward(new Relic(), ComponentManager.component(false, "&6Relic")));
                level.addReward(new Reward(new Item(Material.GHAST_TEAR)));
            }

            if ((75 % i) == 0) {
                level.addReward(new Reward(100, ComponentManager.component(false, "&7" + 100 + "EXP")));
            }

            levels.add(level);
        }
    }

    /**
     * Adds the reward items to the {@link IndexMenu} inventory
     * @param player The player the menu is being built for
     * */
    public void addRewardItems(Player player) {

        for (IndexLevel level : levels) {

            Item item;
            List<Component> lore = new ArrayList<>();

            List<Integer> unlockedLevels = PlayerData.get(PlayerData.FileType.INDEX, player.getUniqueId()).getIntegerList("unlockedLevels");

            Integer currentConfigPlayerLevel = PlayerData.get(PlayerData.FileType.INDEX, player.getUniqueId()).getInt("currentLevel");

            // check weather a level is unlocked, not unlocked yet or locked
            if (unlockedLevels != null && unlockedLevels.contains(level.getLevel())) {
                item = new Item(Material.LIME_STAINED_GLASS_PANE, ComponentManager.component(false, "&aLevel " + level.getLevel() + " &8(Unlocked)"));
            } else if (currentConfigPlayerLevel >= level.getLevel()) {
                item = new Item(Material.YELLOW_STAINED_GLASS_PANE, ComponentManager.component(false, "&eLevel " + level.getLevel() + " &8(Locked)"));
                lore.add(ComponentManager.component(false, "&eClick to unlock!"));
            }else {
                item = new Item(Material.RED_STAINED_GLASS_PANE, ComponentManager.component(false, "&cLevel " + level.getLevel() + " &8(Locked)"));
            }

            lore.add(ComponentManager.component(false, " "));
            lore.add(ComponentManager.component(false, "&7You'll receive the following rewards"));
            lore.add(ComponentManager.component(false, "&7when you reach level " + level.getLevel() + ":"));

            for (Reward reward : level.getRewards()) {
                if (reward.getReward() instanceof Item || reward.getReward() instanceof Relic) {
                    Item rewardItem = (Item) reward.getReward();
                    lore.add(ComponentManager.component(false, "&7- &6" + ComponentManager.fromComponent(rewardItem.toItemStack().getItemMeta().displayName())));
                } else if (reward.getReward() instanceof Integer) {
                    lore.add(ComponentManager.component(false, "&7- &6" + reward.getReward() + "EXP"));
                } else if (reward.getReward() instanceof Double) {
                    lore.add(ComponentManager.component(false, "&7- &6" + reward.getReward() + Main.economyManager.getEcoSymbol()));
                }
            }

            item.setLore(lore);
            this.getInventory().addItem(item.toItemStack());
        }
    }
}


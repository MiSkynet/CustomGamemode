package me.miskynet.customGamemode.custom.skills;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.TooltipDisplay;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.skills.skillTypes.Skill;
import me.miskynet.customGamemode.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class SkillsMenuItem {

    /**
     * Gets the Copper Golem Item. This wil display basic
     * information about the level
     *
     * @param skillType The type of skill the golem is for
     * @param player The player whose stats should be put onto the item
     * @return {@link ItemStack}
     * */
    public static ItemStack getCopperGolemItem(Skill.SkillType skillType, Player player) {

        Skill currentSkill = Skill.getSkillBySkillType(skillType);

        // if not, return an unavailable item
        if (currentSkill == null) return new Item(Material.BARRIER, Utils.component(false, "&cSkill Unavailable")).toItemStack();

        // else keep creating the item
        int currentLevel = Skill.getCurrentPlayerLevel(skillType, player);
        int currentXP = Skill.getCurrentPlayerXP(skillType, player);
        int levelUpOnLevel = currentSkill.getLevelUpOnLevel();

        int progessPercentage = (currentXP * 100) / levelUpOnLevel;

        Item item = new Item(Material.COPPER_GOLEM_STATUE);

        String displayName = Utils.fromComponent(currentSkill.getName()) + " Skill";

        item.setDisplayName(Utils.component(false, displayName));

        ArrayList<Component> lore = new ArrayList();

        lore.add(Utils.component(false, " "));
        lore.add(Utils.component(false, "&7Current " + displayName + "&7 Level: &a" + currentLevel));
        lore.add(Utils.component(false, " "));
        lore.add(Utils.component(false, "&7Current Progress:"));
        lore.add(Utils.component(false, "&a" + currentXP + "&7/&a" + levelUpOnLevel + " &7EXP"));
        lore.add(Utils.component(false, "&7" + progessPercentage + "% [ " + progessBar(progessPercentage) + " &7]"));
        lore.add(Utils.component(false, " "));
        lore.add(Utils.component(false, "&8Click to increase XP"));
        lore.add(Utils.component(false, "&8Shift-Click to view more"));

        item.setLore(lore);

        // remove the "copper_golem_position: standing"
        TooltipDisplay tooltip = TooltipDisplay.tooltipDisplay()
                .addHiddenComponents(DataComponentTypes.BLOCK_DATA)
                .build();

        ItemStack stack = item.toItemStack();
        stack.setData(DataComponentTypes.TOOLTIP_DISPLAY, tooltip);

        ItemMeta meta = stack.getItemMeta();
        meta.getPersistentDataContainer().set(Skill.skillKey, PersistentDataType.STRING, skillType.toString());

        stack.setItemMeta(meta);

        return stack;
    }

    /**
     * Returns the progress bar for the lore of the copper golem
     *
     * @param percentage The percentage of the progess
     * @return Progressbar as {@link String}
     * */
    private static String progessBar(int percentage) {

        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < 11; i++) {

            if ((i * 10) <= percentage) {
                builder.append("&aI");
            }else {
                builder.append("&cI");
            }
        }

        return builder.toString();
    }

    /**
     * Gets the explanation of the skills item
     *
     * @return {@link Item}
     * */
    public static Item getSkillsExplanationItem() {

        Item item = new Item(Material.PAPER, Utils.component(false, "What are skills?"));

        List<Component> lore = new ArrayList<>();
        lore.add(Utils.component(false, " "));
        lore.add(Utils.component(false, "&7Skills increase different attributes of you."));
        lore.add(Utils.component(false, "&7To view more details about each skill, hover over the"));
        lore.add(Utils.component(false, "&7corresponding Item."));
        lore.add(Utils.component(false, "&7The Skills can be increased with your levels."));
        lore.add(Utils.component(false, "&71XP in the skills is equal to 1 Skill in your XP Bar."));

        item.setLore(lore);

        return item;
    }


    public static Item skillUnavailableItem() {
        Item item = new Item(Material.BARRIER, Utils.component(false, "&cSkill Unavailable"));
        return item;
    }

    public static Item getSkillExplanationItem(Skill skill) {

        if (skill == null) return skillUnavailableItem();

        Item item = new Item(Material.NAME_TAG, Utils.component(false, Utils.fromComponent(skill.getName()) + " Skill"));

        List<Component> lore = new ArrayList<>(skill.getDescription());

        lore.addFirst(Utils.component(" "));

        item.setLore(lore);

        return item;
    }



}

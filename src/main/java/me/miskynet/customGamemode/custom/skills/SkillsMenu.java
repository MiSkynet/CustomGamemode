package me.miskynet.customGamemode.custom.skills;

import me.miskynet.customGamemode.custom.menu.TextureMenu;
import me.miskynet.customGamemode.custom.skills.skillTypes.Skill;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.entity.Player;

public class SkillsMenu extends TextureMenu {

    /**
     * @param player The player to build the menu for
     *
     */
    public SkillsMenu(Player player) {
        super(Utils.component("Skills"), 36, "\uE006");
        this.buildMenu(player);
    }

    /**
     * @param player The player to build the menu for
     *
     */
    public void buildMenu(Player player) {

        this.getInventory().setItem(0, SkillsMenuItem.getSkillsExplanationItem().toItemStack());

        this.getInventory().setItem(11, SkillsMenuItem.getCopperGolemItem(Skill.SkillType.FIGHTING, player));

        this.getInventory().setItem(20, SkillsMenuItem.getSkillExplanationItem(Skill.getSkillBySkillType(Skill.SkillType.FIGHTING)).toItemStack());

    }
}

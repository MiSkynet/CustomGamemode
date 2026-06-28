package me.miskynet.customGamemode.custom.menu.skillsMenu;

import me.miskynet.customGamemode.custom.item.skillsMenu.SkillsMenuItem;
import me.miskynet.customGamemode.custom.menu.TextureMenu;
import me.miskynet.customGamemode.custom.skills.Skill;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.entity.Player;

public class SkillsMenu extends TextureMenu {

    public SkillsMenu(Player player) {
        super(Utils.component("Skills"), 36, "\uE006");
        this.buildMenu(player);
    }


    public void buildMenu(Player player) {
        this.getInventory().setItem(0, SkillsMenuItem.getSkillsExplanationItem().toItemStack());

        this.getInventory().setItem(11, SkillsMenuItem.getCopperGolemItem(Skill.SkillType.FIGHTING, player));
    }
}

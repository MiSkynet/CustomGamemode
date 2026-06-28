package me.miskynet.customGamemode.custom.menu.skillsMenu;

import me.miskynet.customGamemode.custom.skills.FightingSkill;
import me.miskynet.customGamemode.custom.skills.Skill;
import me.miskynet.customGamemode.utils.Debugger;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class SkillsMenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof SkillsMenu skillsMenu) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            if (!Utils.checkForAllowedClick(player)) return;

            if (event.getSlot() == 11) {

                if (event.isShiftClick()) {
                    Debugger.log("Shift Click!");
                }else {
                    ItemStack currentItemStack = event.getClickedInventory().getItem(event.getSlot());
                    String PDC = (String) Utils.getPDCOfItem(currentItemStack, Skill.skillKey, PersistentDataType.STRING);
                    Skill.SkillType skillType = Skill.SkillType.valueOf(PDC);
                    Skill skill = checkForSkill(skillType);

                    if (skill == null) return;

                    // get the current player XP
                    int currentPlayerXP = Skill.getCurrentPlayerXP(skillType, player);

                    Skill.increasePlayerXP(skillType, player);

                    if (currentPlayerXP >= skill.getLevelUpOnLevel()) {
                        Skill.increasePlayerLevel(skillType, player);
                    }

                    skillsMenu.buildMenu(player);
                }


            }

            Utils.createClickCooldown(player);
        }

    }

    private Skill checkForSkill(Skill.SkillType skillType) {

        if (skillType == null) return null;

        Skill skill = null;

        if (skillType.name().equals("FIGHTING")) {
            skill = new FightingSkill();
        }

        return skill;
    }

}

package me.miskynet.customGamemode.commands.testCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.custom.item.skillsMenu.SkillsMenuItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCopperGolem implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getSender();

    }
}

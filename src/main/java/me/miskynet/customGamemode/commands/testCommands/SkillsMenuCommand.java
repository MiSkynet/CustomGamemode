package me.miskynet.customGamemode.commands.testCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.custom.menu.skillsMenu.SkillsMenu;
import org.bukkit.entity.Player;

public class SkillsMenuCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getSender();

        SkillsMenu skillsMenu = new SkillsMenu(player);
        skillsMenu.openForPlayer(player);
    }
}

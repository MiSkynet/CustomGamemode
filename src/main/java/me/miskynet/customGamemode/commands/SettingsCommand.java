package me.miskynet.customGamemode.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.custom.settings.SettingsMenu;
import org.bukkit.entity.Player;

public class SettingsCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        Player player = (Player) commandSourceStack.getSender();

        SettingsMenu menu = new SettingsMenu();

        menu.buildMenu(player);
        menu.openForPlayer(player);

    }
}

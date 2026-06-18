package me.miskynet.customGamemode.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.utils.customConfig.PlayerSettings;
import org.bukkit.entity.Player;

public class ToggleScoreboard implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        Player player = (Player) commandSourceStack.getSender();

        if ((Boolean) PlayerSettings.get(player, "settings.scoreboardStatus")) {
            PlayerSettings.set(player, "settings.scoreboardStatus", false);
        }else {
            PlayerSettings.set(player, "settings.scoreboardStatus", true);
        }
    }
}

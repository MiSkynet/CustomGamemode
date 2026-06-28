package me.miskynet.customGamemode.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.custom.config.PlayerData;
import org.bukkit.entity.Player;

public class ToggleScoreboard implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        Player player = (Player) commandSourceStack.getSender();

        if ((Boolean) PlayerData.get(PlayerData.FileType.SETTINGS, player.getUniqueId(), "settings.scoreboardStatus")) {
            PlayerData.set(PlayerData.FileType.SETTINGS, player.getUniqueId(), "settings.scoreboardStatus", false);
        }else {
            PlayerData.set(PlayerData.FileType.SETTINGS, player.getUniqueId(), "settings.scoreboardStatus", true);
        }
    }
}

package me.miskynet.customGamemode.commands.testCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.custom.config.PlayerData;
import org.bukkit.entity.Player;

public class ReloadCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (!(commandSourceStack.getSender().hasPermission("customgamemode.admin") || commandSourceStack.getSender().isOp())) {
            return;
        }

        Player player = (Player) commandSourceStack.getSender();


        PlayerData.reload(PlayerData.FileType.SKILLS, player.getUniqueId());
        PlayerData.reload(PlayerData.FileType.SETTINGS, player.getUniqueId());
        PlayerData.reload(PlayerData.FileType.BALANCE, player.getUniqueId());

    }
}

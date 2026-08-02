package me.miskynet.customGamemode.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.config.PlayerData;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.PermissionManager;
import org.bukkit.entity.Player;

public class ToggleScoreboard implements BasicCommand {

    private final Language language = Main.getInstance().getLanguage();

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender().sendMessage(ComponentUtils.component(language.getString("commands.general.nonPlayerSender")));
            return;
        }

        Player player = (Player) commandSourceStack.getSender();

        if (!(PermissionManager.Perm.COMMAND_PLAYER_TOGGLE_SCOREBOARD.hasPermission(player)) && !player.isOp()) {
            player.sendMessage(ComponentUtils.component(language.getString("commands.general.noPermission")));
            return;
        }

        if ((Boolean) PlayerData.get(PlayerData.FileType.SETTINGS, player.getUniqueId(), "settings.scoreboardStatus")) {
            PlayerData.set(PlayerData.FileType.SETTINGS, player.getUniqueId(), "settings.scoreboardStatus", false);
        }else {
            PlayerData.set(PlayerData.FileType.SETTINGS, player.getUniqueId(), "settings.scoreboardStatus", true);
        }
    }
}

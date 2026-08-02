package me.miskynet.customGamemode.commands.admin;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.CustomConfig;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.config.PlayerData;
import me.miskynet.customGamemode.custom.economy.EconomyManager;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class ReloadCommand implements BasicCommand {

    private final Language language = Main.getInstance().getLanguage();
    private final EconomyManager economyManager = Main.getInstance().getEconomyManager();

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (commandSourceStack.getSender() instanceof Player player && !(PermissionManager.Perm.COMMAND_ADMIN_RELOAD.hasPermission(player) || player.isOp())) {
            player.sendMessage(ComponentUtils.component(language.getString("commands.general.noPermission")));
            return;
        }

        CustomConfig.reload("config.yml");
        economyManager.reload();

        // check if the args lenght is 1 and if the argument is not "players" or "lang"
        // -> send an error message to the player and return
        if (args.length == 1){
            if (!(args[0].equals("players") || args[0].equals("lang"))) {
                commandSourceStack.getSender().sendMessage(ComponentUtils.component(language.getString("commands.admin.reload.invalidArgument")
                        .replace("%arg%", args[0])));
                return;
            }
        }

        // reload all config files for all online players if no argument is used or if the argument is "players"
        if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("players"))) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerData.reload(PlayerData.FileType.SETTINGS, player.getUniqueId());
                PlayerData.reload(PlayerData.FileType.BALANCE, player.getUniqueId());
                PlayerData.reload(PlayerData.FileType.INDEX, player.getUniqueId());
                PlayerData.reload(PlayerData.FileType.STATS, player.getUniqueId());
            }
        }

        // reload the language file if no argument is used or if the argument is "lang"
        if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("lang"))) {
            language.reload();
        }

        commandSourceStack.getSender().sendMessage(ComponentUtils.component(language.getString("commands.admin.reload.success")));
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {

        // return no suggestions if the player doesn't have permission to use the command
        if (commandSourceStack.getSender() instanceof Player player && !(PermissionManager.Perm.COMMAND_ADMIN_RELOAD.hasPermission(player) || player.isOp())) {
            return List.of();
        }

        if (args.length == 0) {
            return List.of("players", "lang");
        }

        if (args.length == 1) {
            if ("players".startsWith(args[0].toLowerCase())) {
                return List.of("players");
            }
            if ("lang".startsWith(args[0].toLowerCase())) {
                return List.of("lang");
            }
        }

        return List.of();
    }

}

package me.miskynet.customGamemode.commands.eco.player;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.commands.SubCommand;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.economy.EconomyManager;
import me.miskynet.customGamemode.utils.ComponentUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static me.miskynet.customGamemode.utils.PermissionManager.Perm.COMMAND_PLAYER_ECO_GET;

public class GetCommand implements SubCommand {

    private final Language language = Main.getInstance().getLanguage();
    private final EconomyManager economyManager = Main.getInstance().getEconomyManager();

    @Override
    public void command(CommandSender sender, String[] args) {

        if (!(COMMAND_PLAYER_ECO_GET.hasPermission(sender) && sender.isOp())) {
            sender.sendMessage(ComponentUtils.component(language.getString("commands.general.noPermission")));
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.invalidPlayer")
                    .replace("%target%", args[1])));
            return;
        }

        sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.get.balance")
                .replace("%target%", args[1])
                .replace("%balance%", economyManager.getDisplayFormat(true, target))));
    }

    @Override
    public Collection<String> tabCompleter(CommandSender sender, String[] args) {

        Collection<String> suggestions = Collections.emptyList();

        if (args.length == 2) {
            suggestions = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(args[1].toLowerCase())) suggestions.add(player.getName());
            }
            return suggestions;
        }

        return suggestions;
    }
}

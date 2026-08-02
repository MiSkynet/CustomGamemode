package me.miskynet.customGamemode.commands.eco.admin;

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

import static me.miskynet.customGamemode.utils.PermissionManager.Perm.*;

public class AddCommand implements SubCommand {

    private final Language language = Main.getInstance().getLanguage();
    private final EconomyManager economyManager = Main.getInstance().getEconomyManager();

    @Override
    public void command(CommandSender sender, String[] args) {

        if (!(COMMAND_ADMIN_ECO_ADD.hasPermission(sender) && sender.isOp())) {
            sender.sendMessage(ComponentUtils.component(language.getString("commands.general.noPermission")));
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.invalidPlayer")
                    .replace("%target%", args[1])));
            return;
        }

        // check for a valid amount
        double amount;

        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.invalidAmount")
                    .replace("%amount%", args[2])));
            return;
        }

        economyManager.addBalance(target, amount);
        sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.add.sender")
                .replace("%amount%", economyManager.getDisplayFormat(true, target))
                .replace("%target%", target.getName())));

        target.sendMessage(ComponentUtils.component(language.getString("commands.eco.add.target")
                .replace("%amount%", economyManager.getDisplayFormat(true, target))
                .replace("%sender%", sender.getName())));
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

        if (args.length == 3) {
            suggestions = new ArrayList<>();
            if ("1".startsWith(args[2])) suggestions.add("1");
            if ("10".startsWith(args[2])) suggestions.add("10");
            if ("100".startsWith(args[2])) suggestions.add("100");
            if ("1000".startsWith(args[2])) suggestions.add("1000");
            return suggestions;
        }

        return suggestions;
    }
}

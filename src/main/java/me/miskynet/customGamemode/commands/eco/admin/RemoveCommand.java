package me.miskynet.customGamemode.commands.eco.admin;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.commands.SubCommand;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.economy.EconomyManager;
import me.miskynet.customGamemode.utils.ComponentUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

import static me.miskynet.customGamemode.utils.PermissionManager.Perm.*;

public class RemoveCommand implements SubCommand {

    private final Language language = Main.getInstance().getLanguage();
    private final EconomyManager economyManager = Main.getInstance().getEconomyManager();

    @Override
    public void command(CommandSender sender, String[] args) {

        if (!(COMMAND_ADMIN_ECO_REMOVE.hasPermission(sender) && sender.isOp())) {
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

        // make sure the amount is positive
        if (amount < 0) amount = amount * -1;

        economyManager.setBalance(target, economyManager.getBalance(target) - amount);

        sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.remove.sender")
                .replace("%amount%", economyManager.getDisplayFormat(true, target))
                .replace("%target%", target.getName())));

        target.sendMessage(ComponentUtils.component(language.getString("commands.eco.remove.target")
                .replace("%amount%", economyManager.getDisplayFormat(true, target))
                .replace("%sender%", sender.getName())));

    }

    @Override
    public Collection<String> tabCompleter(CommandSender sender, String[] args) {
        return SubCommand.super.tabCompleter(sender, args);
    }
}

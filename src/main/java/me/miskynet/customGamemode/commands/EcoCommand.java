package me.miskynet.customGamemode.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.economy.EconomyManager;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.PermsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;


public class EcoCommand implements BasicCommand {

    private final Language language = Main.getInstance().getLanguage();
    private final EconomyManager economyManager = Main.getInstance().getEconomyManager();

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender().sendMessage(ComponentUtils.component(language.getString("commands.general.nonPlayerSender")));
            return;
        }

        Player sender = (Player) commandSourceStack.getSender();

        if (args.length >= 1 && args[0].equals("help")) {
            sendPlayerHelp(sender);
            return;
        }

        if (args.length >= 2 && args[0].equalsIgnoreCase("get")) {
            getFunction(sender, args);
            return;
        }

        if (args.length >= 3 && (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("add"))) {
            setAndAddFunction(sender, args);
            return;
        }

        sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.useHelp")));
    }

    public void sendPlayerHelp(Player sender) {
        List<String> helpMessages = language.getStringList("commands.eco.help");
        for (String message : helpMessages) {
            sender.sendMessage(ComponentUtils.component(message));
        }
    }

    public void getFunction(Player sender, String[] args) {

        if (!sender.hasPermission(PermsManager.Perms.COMMAND_ECO_GET.toLowerString()) && !sender.isOp()) {
            sender.sendMessage(ComponentUtils.component(language.getString("commands.general.noPermission")));
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.get.invalidPlayer")
                    .replace("%target%", args[1])));
            return;
        }

        sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.get.balance")
                .replace("%target%", target.getName())
                .replace("%balance%", economyManager.getDisplayFormat(true, target))));
    }

    public void setAndAddFunction(Player sender, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.invalidPlayer")
                    .replace("%target%", args[1])));
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.invalidAmount")
                    .replace("%amount%", args[2])));
            return;
        }

        switch (args[0].toLowerCase()) {
            case "set" -> {
                if (!sender.hasPermission(PermsManager.Perms.COMMAND_ECO_SET.toLowerString()) && !sender.isOp()) {
                    sender.sendMessage(ComponentUtils.component(language.getString("commands.general.noPermission")));
                    return;
                }

                economyManager.setBalance(target, amount);
                sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.set.sender")
                        .replace("%target%", target.getName())
                        .replace("%amount%", economyManager.getDisplayFormat(true, target))));
                target.sendMessage(ComponentUtils.component(language.getString("commands.eco.set.target")
                        .replace("%sender%", sender.getName())
                        .replace("%amount%", economyManager.getDisplayFormat(true, target))));
            }
            case "add" -> {
                if (!sender.hasPermission(PermsManager.Perms.COMMAND_ECO_ADD.toLowerString()) && !sender.isOp()) {
                    sender.sendMessage(ComponentUtils.component(language.getString("commands.general.noPermission")));
                    return;
                }

                economyManager.addBalance(target, amount);
                sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.add.sender")
                        .replace("%target%", target.getName())
                        .replace("%amount%", economyManager.getDisplayFormat(true, target))));
                target.sendMessage(ComponentUtils.component(language.getString("commands.eco.add.target")
                        .replace("%sender%", sender.getName())
                        .replace("%amount%", economyManager.getDisplayFormat(true, target))));
            }
            default -> {
                sender.sendMessage(ComponentUtils.component(language.getString("commands.eco.useHelp")));
            }
        }
    }
}






















//    if (firstArg.equals("set") || firstArg.equals("add")) {
//
//        Player target = Bukkit.getPlayer(args[1].toLowerCase());
//
//        if (target == null) {
//            sender.sendMessage(ComponentUtils.component("&cPlayer " + args[1] + " is not a valid player"));
//            return;
//        }
//
//        Double amount;
//        try {
//            amount = Double.parseDouble(args[2]);
//        } catch (NumberFormatException e) {
//            sender.sendMessage(ComponentUtils.component("&cYou need to enter a valid amount!"));
//            return;
//        }
//
//        switch (args[0].toLowerCase()) {
//            case "set" -> {
//                Main.economyManager.setBalance(target, amount);
//                sender.sendMessage(ComponentUtils.component("&aBalance of " + target.getName() + " set to " + Main.economyManager.getDisplayFormat(amount) + Main.economyManager.getEcoSymbol()));
//                Main.economyManager.getDisplayFormat(Main.economyManager.getBalance(target.getPlayer()));
//            }
//            case "add" -> {
//                Main.economyManager.addBalance(target, amount);
//                sender.sendMessage(ComponentUtils.component("&aAdded " + Main.economyManager.getDisplayFormat(amount) + Main.economyManager.getEcoSymbol() + " to the balance of " + target.getName()));
//            }
//        }
//        return;
//    }
//
//        if (firstArg.equals("get")) {
//        Player target = Bukkit.getPlayer(args[1].toLowerCase());
//
//        if (target == null) {
//            sender.sendMessage(ComponentUtils.component("&cPlayer " + args[1] + " is not a valid player"));
//            return;
//        }
//
//        sender.sendMessage(ComponentUtils.component("&a" + target.getName() + "'s balance is: " + Main.economyManager.getBalance(target) + Main.economyManager.getEcoSymbol()));
//    }
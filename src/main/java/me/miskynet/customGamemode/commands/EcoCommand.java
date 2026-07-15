package me.miskynet.customGamemode.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.PermsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class EcoCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender().sendMessage(ComponentUtils.component(Main.language.getString("commands.general.nonPlayerSender")));
            return;
        }

        Player sender = (Player) commandSourceStack.getSender();

        if (args.length < 1) {
            sender.sendMessage(ComponentUtils.component("&cUse /eco help"));
            return;
        }

        String firstArg = args[0].toLowerCase();

        if (firstArg.equals("help")) {
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
    }

    public void sendPlayerHelp(Player sender) {
        sender.sendMessage(ComponentUtils.component("&aAvailable commands:"));
        sender.sendMessage(ComponentUtils.component("&a/eco get <player> - Get the balance of a player"));
        sender.sendMessage(ComponentUtils.component("&a/eco set <player> <amount> - Set the balance of a player"));
        sender.sendMessage(ComponentUtils.component("&a/eco add <player> <amount> - Add to the balance of a player"));
        sender.sendMessage(ComponentUtils.component("&a/pay <player> <amount> - Pay a player"));
    }

    public void getFunction(Player sender, String[] args) {

        if (!sender.hasPermission(PermsManager.Perms.COMMAND_ECO_GET.toLowerString()) && !sender.isOp()) {
            sender.sendMessage(ComponentUtils.component(Main.language.getString("commands.general.noPermission")));
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ComponentUtils.component("&cPlayer " + args[1] + " is not a valid player"));
            sender.sendMessage(ComponentUtils.component("&cUse /eco help for a list of commands."));
            return;
        }

        sender.sendMessage(ComponentUtils.component("&a" + target.getName() + "'s balance is: " +
                Main.economyManager.getDisplayFormat(Main.economyManager.getBalance(target)) + Main.economyManager.getEcoSymbol()));
    }

    public void setAndAddFunction(Player sender, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ComponentUtils.component("Player " + args[1] + " is not a valid player"));
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ComponentUtils.component("This is not a valid amount!"));
            sender.sendMessage(ComponentUtils.component("&cUse /eco help for a list of commands."));
            return;
        }

        switch (args[0].toLowerCase()) {
            case "set" -> {
                if (!sender.hasPermission(PermsManager.Perms.COMMAND_ECO_SET.toLowerString()) && !sender.isOp()) {
                    sender.sendMessage(ComponentUtils.component(Main.language.getString("commands.general.noPermission")));
                    return;
                }

                Main.economyManager.setBalance(target, amount);
                sender.sendMessage(ComponentUtils.component("&aYou've set " + target.getName() + "'s balance to " + Main.economyManager.getDisplayFormat(amount) + Main.economyManager.getEcoSymbol()));
                target.sendMessage(ComponentUtils.component("&aYour balance has been set to " + Main.economyManager.getDisplayFormat(amount) + Main.economyManager.getEcoSymbol()));
            }
            case "add" -> {
                if (!sender.hasPermission(PermsManager.Perms.COMMAND_ECO_ADD.toLowerString()) && !sender.isOp()) {
                    sender.sendMessage(ComponentUtils.component(Main.language.getString("commands.general.noPermission")));
                    return;
                }

                Main.economyManager.addBalance(target, amount);
                sender.sendMessage(ComponentUtils.component("&aYou've added " + Main.economyManager.getDisplayFormat(amount) + Main.economyManager.getEcoSymbol()) + " to " + target.getName() + "'s balance");
                target.sendMessage(ComponentUtils.component("&a" + Main.economyManager.getDisplayFormat(amount) + Main.economyManager.getEcoSymbol() + " has been added to your balance"));
            }
            default -> {
                sender.sendMessage(ComponentUtils.component("&cInvalid command. Use /eco help for a list of commands."));
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
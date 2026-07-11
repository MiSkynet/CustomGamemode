package me.miskynet.customGamemode.commands.economy;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.utils.ComponentManager;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class EcoCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (!(commandSourceStack.getSender().isOp() || commandSourceStack.getSender().hasPermission("customgamemode.eco"))) return;

        CommandSender sender = commandSourceStack.getSender();

        String firstArg = args[0].toLowerCase();

        if (firstArg.equals("set") || firstArg.equals("add")) {

            Player target = Bukkit.getPlayer(args[1].toLowerCase());

            if (target == null) {
                sender.sendMessage(ComponentManager.component("&cPlayer " + args[1] + " is not a valid player"));
                return;
            }

            Double amount;
            try {
                amount = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ComponentManager.component("&cYou need to enter a valid amount!"));
                return;
            }

            switch (args[0].toLowerCase()) {
                case "set" -> {
                    Main.economyManager.setBalance(target, amount);
                    sender.sendMessage(ComponentManager.component("&aBalance of " + target.getName() + " set to " + Main.economyManager.getDisplayFormat(amount) + Main.economyManager.getEcoSymbol()));
                    Main.economyManager.getDisplayFormat(Main.economyManager.getBalance(target.getPlayer()));
                }
                case "add" -> {
                    Main.economyManager.addBalance(target, amount);
                    sender.sendMessage(ComponentManager.component("&aAdded " + Main.economyManager.getDisplayFormat(amount) + Main.economyManager.getEcoSymbol() + " to the balance of " + target.getName()));
                }
            }
            return;
        }

        if (firstArg.equals("get")) {
            Player target = Bukkit.getPlayer(args[1].toLowerCase());

            if (target == null) {
                sender.sendMessage(ComponentManager.component("&cPlayer " + args[1] + " is not a valid player"));
                return;
            }

            sender.sendMessage(ComponentManager.component("&a" + target.getName() + "'s balance is: " + Main.economyManager.getBalance(target) + Main.economyManager.getEcoSymbol()));
        }
    }
}

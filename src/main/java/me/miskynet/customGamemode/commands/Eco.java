package me.miskynet.customGamemode.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Eco implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (!(commandSourceStack.getSender().isOp() || commandSourceStack.getSender().hasPermission("customgamemode.eco"))) return;

        CommandSender sender = commandSourceStack.getSender();

        if (args[0].equalsIgnoreCase("set")) {

            if (Bukkit.getPlayer(args[1].toLowerCase()) != null) {
                Player target = Bukkit.getPlayer(args[1].toLowerCase());

                try {
                    Main.economyManager.setBalance(target, Integer.parseInt(args[2]));
                    sender.sendMessage(Utils.component("&aBalance of '" + target.getName() + "' set to " + args[2] + "."));
                } catch (NumberFormatException e) {
                    sender.sendMessage(Utils.component("&cYou need to enter a valid amount!"));
                }
            }
        }
    }

}

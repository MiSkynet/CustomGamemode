package me.miskynet.customGamemode.commands.economy;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.economy.EconomyManager;
import me.miskynet.customGamemode.utils.ComponentUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PayCommand implements BasicCommand {

    EconomyManager ecoManager = Main.economyManager;

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (!(commandSourceStack.getSender().isOp() || commandSourceStack.getSender().hasPermission(this.permission()) || commandSourceStack.getSender() instanceof Player)) return;

        Player player = (Player) commandSourceStack.getSender();

        Player target = Bukkit.getPlayer(args[0].toLowerCase());

        if (target == null) {
            player.sendMessage(ComponentUtils.component("&cPlayer " + args[1] + " is not a valid player"));
            return;
        }

        if (target.getName().equalsIgnoreCase(player.getName())) {
            player.sendMessage(ComponentUtils.component("&cYou cannot send yourself money!"));
            return;
        }

        Double amount;
        try {
            amount = Double.parseDouble(args[1]);
        }catch (NumberFormatException e) {
            player.sendMessage(ComponentUtils.component("&cPlease enter a valid amount!"));
            return;
        }

        if (amount < 0) {
            player.sendMessage(ComponentUtils.component("&cYou have to at least send 1" + ecoManager.getEcoSymbol()));
            return;
        }

        if (amount > ecoManager.getBalance(player)) {
            player.sendMessage(ComponentUtils.component("&cYou cannot send " + amount + ecoManager.getEcoSymbol() +
                    " to " + target.getName() + " since you only got " + ecoManager.getBalance(player)  +
                    ecoManager.getEcoSymbol() + "!"));
            return;
        }

        ecoManager.transfer(player, target, amount);
        player.sendMessage(ComponentUtils.component("&7You payed " + target.getName() + " " + amount + ecoManager.getEcoSymbol()));
        target.sendMessage(ComponentUtils.component("&7" + player.getName() + " payed you " + amount + ecoManager.getEcoSymbol()));

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 0 || args.length == 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                suggestions.add(player.getName());
            }
        }

        else if (args.length == 2) {
            suggestions.add("10");
            suggestions.add("100");
            suggestions.add("1000");
        }

        String currentInput = (args.length > 0) ? args[args.length - 1].toLowerCase() : "";
        return suggestions.stream()
                .filter(s -> s.toLowerCase().startsWith(currentInput))
                .toList();
    }

    @Override
    public @Nullable String permission() {
        return "customgamemode.command.pay";
    }


}

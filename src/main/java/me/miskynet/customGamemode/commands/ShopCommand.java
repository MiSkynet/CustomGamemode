package me.miskynet.customGamemode.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.custom.shop.ShopMenu;
import org.bukkit.entity.Player;

public class ShopCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (commandSourceStack.getSender() instanceof Player player) {
            ShopMenu shopMenu = new ShopMenu(0);
            shopMenu.openForPlayer(player);
        }

    }
}

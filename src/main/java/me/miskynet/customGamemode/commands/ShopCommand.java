package me.miskynet.customGamemode.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.custom.menu.shop.Shop;
import org.bukkit.entity.Player;

public class ShopCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (commandSourceStack.getSender() instanceof Player player) {
            Shop shop = new Shop(0);
            shop.openForPlayer(player);
        }

    }
}

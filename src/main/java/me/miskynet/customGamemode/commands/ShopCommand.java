package me.miskynet.customGamemode.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.shop.ShopMenu;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.PermsManager;
import org.bukkit.entity.Player;

public class ShopCommand implements BasicCommand {

    private final Language language = Main.getInstance().getLanguage();

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender().sendMessage(ComponentUtils.component(language.getString("commands.general.nonPlayerSender")));
            return;
        }

        Player player = (Player) commandSourceStack.getSender();

        if (!(player.hasPermission(PermsManager.Perms.COMMAND_SHOP.toLowerString())) && !player.isOp()) {
            player.sendMessage(ComponentUtils.component(language.getString("commands.general.noPermission")));
            return;
        }

        ShopMenu shopMenu = new ShopMenu(0);
        shopMenu.openForPlayer(player);

        player.sendMessage(ComponentUtils.component(language.getString("commands.shop.opened")));

    }
}

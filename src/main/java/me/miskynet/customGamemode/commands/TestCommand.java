package me.miskynet.customGamemode.commands;

import me.miskynet.customGamemode.custom.entitys.InteractEntity;
import me.miskynet.customGamemode.custom.item.Item;
import me.miskynet.customGamemode.custom.menu.Menu;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        Menu menu = new Menu(Component.text("Custom Menu"), 27);
        Item item = new Item(Material.DRAGON_EGG);
        menu.setItem(14, item);

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Component.text("You need to be a player to use this command!"));
            return true;
        }

        Player player = (Player) commandSender;
        if (!(player.isOp())) {
            player.sendMessage(Component.text("You need to be OP to use this command!"));
            return true;
        }

        if (strings.length == 3) {
            if (strings[0].equalsIgnoreCase("inventory")) {
                try {
                    Component title = Component.text(strings[1]);
                    int size = (Integer.parseInt(strings[2]) % 9) == 0 ? Integer.parseInt(strings[2]) : 9;

                    Menu menuTest = new Menu(title, size);
                    menuTest.openForPlayer(player);
                }catch (Exception e) {
                    Bukkit.getLogger().config("Error while running:\n" + e);
                }
            }
        }

        if (strings[0].equalsIgnoreCase("entity")) {
            InteractEntity entity = new InteractEntity(EntityType.VILLAGER);
            entity.setInteractionMenu(menu);
            entity.spawnEntity(player);
        }

        return false;
    }
}

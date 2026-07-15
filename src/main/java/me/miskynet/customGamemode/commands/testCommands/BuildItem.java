package me.miskynet.customGamemode.commands.testCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BuildItem implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getSender();

        if (args == null || args.length == 0) return;

        ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
        if (meta == null) return;

        if ("name".equals(args[0]) && args.length > 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) sb.append(args[i]).append(' ');
            meta.customName(ComponentUtils.component(false, sb.toString().trim()));
        }

        if ("lore".equals(args[0]) && args.length > 1) {
            String sub = args[1];
            if ("clear".equals(sub)) {
                meta.lore(null);
            } else if ("add".equals(sub) && args.length > 2) {
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < args.length; i++) sb.append(args[i]).append(' ');
                List<Component> lore = meta.lore();
                lore = (lore == null) ? new ArrayList<>() : new ArrayList<>(lore);
                lore.add(ComponentUtils.component(false, sb.toString().trim()));
                meta.lore(lore);
            } else if ("set".equals(sub) && args.length > 3) {
                int idx;
                try { idx = Integer.parseInt(args[2]); } catch (NumberFormatException e) { return; }
                List<Component> lore = meta.lore();
                if (lore == null) return;
                lore = new ArrayList<>(lore);
                if (idx < 0 || idx >= lore.size()) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < args.length; i++) sb.append(args[i]).append(' ');
                lore.set(idx, ComponentUtils.component(false, sb.toString().trim()));
                meta.lore(lore);
            }
        }

        player.getInventory().getItemInMainHand().setItemMeta(meta);
    }
}

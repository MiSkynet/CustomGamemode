package me.miskynet.customGamemode.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.custom.index.IndexMenu;
import me.miskynet.customGamemode.utils.ComponentUtils;
import org.bukkit.entity.Player;

public class IndexMenuCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        Player player = (Player) commandSourceStack.getSender();

        IndexMenu indexMenu = new IndexMenu(ComponentUtils.component("Index"));
        indexMenu.buildMenu(player);
        indexMenu.openForPlayer(player);

    }

    private void createProgressBarForNextLevel(Player player) {



    }

}

package me.miskynet.customGamemode.commands;

import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Collections;

/**
 * The SubCommand interface is used to define a command that can be executed by a player or the console.
 * The {@link #command(CommandSender, String[])} is not supposed to be registered in the main like a command, but rather should be
 * called from other methodes.
 * */
public interface SubCommand {

    void command(CommandSender sender, String[] args);

    default Collection<String> tabCompleter(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

}

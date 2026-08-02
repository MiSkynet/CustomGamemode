package me.miskynet.customGamemode.commands.eco;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.commands.SubCommand;
import me.miskynet.customGamemode.commands.eco.player.GetCommand;
import me.miskynet.customGamemode.commands.eco.admin.SetCommand;
import me.miskynet.customGamemode.custom.config.Language;
import org.bukkit.command.CommandSender;

import java.util.*;

import static me.miskynet.customGamemode.utils.PermissionManager.Perm.*;

public class EcoCommandManager implements BasicCommand {

    private final Language language = Main.getInstance().getLanguage();

    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public EcoCommandManager() {
        subCommands.put("set", new SetCommand());
        subCommands.put("get", new GetCommand());
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
            for (String string : language.getStringList("commands.eco.help")) {
                commandSourceStack.getSender().sendMessage(string);
            }
        }

        switch (args[0].toLowerCase()) {
            case "set":
                if (!COMMAND_ADMIN_ECO_SET.hasPermission(commandSourceStack.getSender())) {
                    commandSourceStack.getSender().sendMessage(language.getString("commands.general.noPermission"));
                    return;
                }
                subCommands.get("set").command(commandSourceStack.getSender(), args);
                return;

            case "get":
                if (!COMMAND_PLAYER_ECO_GET.hasPermission(commandSourceStack.getSender())) {
                    commandSourceStack.getSender().sendMessage(language.getString("commands.general.noPermission"));
                    return;
                }
                subCommands.get("get").command(commandSourceStack.getSender(), args);
                return;
        }
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {

        Collection<String> suggestions = new ArrayList<>();

        if (args.length == 0 || args.length == 1) {

            String input = args.length == 0 ? "" : args[0].toLowerCase();

            if (COMMAND_PLAYER_ECO_GET.hasPermission(commandSourceStack.getSender())) if ("get".startsWith(input)) suggestions.add("get");
            if (COMMAND_ADMIN_ECO_ADD.hasPermission(commandSourceStack.getSender())) if ("add".startsWith(input)) suggestions.add("add");
            if (COMMAND_ADMIN_ECO_SET.hasPermission(commandSourceStack.getSender())) if ("set".startsWith(input)) suggestions.add("set");
            if (COMMAND_ADMIN_ECO_REMOVE.hasPermission(commandSourceStack.getSender())) if ("remove".startsWith(input)) suggestions.add("remove");
        }

        if (args.length >= 2) {
            if (COMMAND_ADMIN_ECO_SET.hasPermission(commandSourceStack.getSender()) && args[0].equalsIgnoreCase("set")) {
                return subCommands.get("set").tabCompleter(commandSourceStack.getSender(), args);
            }
        }

        return suggestions;
    }

    @Override
    public boolean canUse(CommandSender sender) {

        if (COMMAND_PLAYER_ECO_GET.hasPermission(sender) ||
                COMMAND_ADMIN_ECO_ADD.hasPermission(sender) ||
                COMMAND_ADMIN_ECO_SET.hasPermission(sender) ||
                COMMAND_ADMIN_ECO_REMOVE.hasPermission(sender)) return true;

        return false;
    }
}

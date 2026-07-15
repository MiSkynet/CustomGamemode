package me.miskynet.customGamemode.commands.testCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.custom.levelingSystem.LevelingSystem;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.PermsManager;
import org.bukkit.entity.Player;

public class GetLevel implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender().sendMessage(ComponentUtils.component("This command can only be executed by a player."));
            return;
        }

        Player player = (Player) commandSourceStack.getSender();

        if (!(player.hasPermission(PermsManager.Perms.COMMAND_GET_LEVEL.toLowerString())) && !player.isOp()) {
            player.sendMessage(ComponentUtils.component("&cYou do not have permission to execute this command."));
            return;
        }

        LevelingSystem levelingSystem = new LevelingSystem();

        int level = levelingSystem.getPlayerLevel(player) + 1;

        if (args.length > 0) {
            try {
                level = Integer.parseInt(args[0]);

                if (level < 1) {
                    player.sendMessage(ComponentUtils.component("&cLevel must be greater than 0."));
                    return;
                }

            }catch (NumberFormatException e) {
                player.sendMessage(ComponentUtils.component("&cInvalid level number."));
                return;
            }
        }

        int requiredXPToLevelUp = new LevelingSystem().getRequiredXPToLevelUp(level);

        player.sendMessage(ComponentUtils.component("&aYou are currently level &e" + levelingSystem.getPlayerLevel(player) + " &a(&e" + levelingSystem.getPlayerXP(player) +"XP&a). For level &e" + level + "&a you need &e" + requiredXPToLevelUp + " XP&a to level up."));

    }
}

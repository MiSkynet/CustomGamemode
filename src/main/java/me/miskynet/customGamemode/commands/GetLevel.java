package me.miskynet.customGamemode.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.index.levelingSystem.IndexLevelingSystem;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.PermissionManager;
import org.bukkit.entity.Player;

public class GetLevel implements BasicCommand {

    private final IndexLevelingSystem indexLevelingSystem = Main.getInstance().getLevelingSystem();
    private final Language language = Main.getInstance().getLanguage();

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender().sendMessage(ComponentUtils.component(language.getString("commands.general.nonPlayerSender")));
            return;
        }

        Player player = (Player) commandSourceStack.getSender();

        if (!(PermissionManager.Perm.COMMAND_PLAYER_GET_LEVEL.hasPermission(player)) && !player.isOp()) {
            player.sendMessage(ComponentUtils.component(language.getString("commands.general.noPermission")));
            return;
        }

        int nextLevel = indexLevelingSystem.getPlayerLevel(player) + 1;

        if (args.length == 1) {
            try {
                nextLevel = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage(ComponentUtils.component(language.getString("commands.level.invalidLevel")));
                return;
            }
        }

        int requiredXPToLevelUp = indexLevelingSystem.getRequiredXPToLevelUp(nextLevel);

        player.sendMessage(ComponentUtils.component(language.getString("commands.level.currentLevel")
                .replace("%level%", indexLevelingSystem.getPlayerLevel(player) + "")
                .replace("%xp%", indexLevelingSystem.getPlayerXP(player) + "")
                .replace("%nextLevel%", nextLevel + "")
                .replace("%requiredXP%", requiredXPToLevelUp + "")));

    }
}

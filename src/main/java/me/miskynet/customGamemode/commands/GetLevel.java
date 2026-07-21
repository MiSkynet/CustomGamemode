package me.miskynet.customGamemode.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.index.levelingSystem.IndexLevelingSystem;
import me.miskynet.customGamemode.utils.ComponentUtils;
import me.miskynet.customGamemode.utils.PermsManager;
import org.bukkit.entity.Player;

public class GetLevel implements BasicCommand {

    private IndexLevelingSystem indexLevelingSystem = Main.getInstance().getLevelingSystem();
    private final Language language = Main.getInstance().getLanguage();

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender().sendMessage(ComponentUtils.component(language.getString("commands.general.nonPlayerSender")));
            return;
        }

        Player player = (Player) commandSourceStack.getSender();

        if (!(player.hasPermission(PermsManager.Perms.COMMAND_GET_LEVEL.toLowerString())) && !player.isOp()) {
            player.sendMessage(ComponentUtils.component(language.getString("commands.general.noPermission")));
            return;
        }

        int nextLevel = indexLevelingSystem.getPlayerLevel(player) + 1;

        int requiredXPToLevelUp = indexLevelingSystem.getRequiredXPToLevelUp(nextLevel);

        player.sendMessage(ComponentUtils.component(language.getString("commands.level.currentLevel")
                .replaceAll("%level%", indexLevelingSystem.getPlayerLevel(player) + "")
                .replaceAll("%xp%", indexLevelingSystem.getPlayerXP(player) + "")
                .replaceAll("%nextLevel%", nextLevel + "")
                .replaceAll("%requiredXP%", requiredXPToLevelUp + "")));

    }
}

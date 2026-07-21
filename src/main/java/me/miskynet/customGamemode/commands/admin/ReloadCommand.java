package me.miskynet.customGamemode.commands.admin;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.CustomConfig;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.config.PlayerData;
import me.miskynet.customGamemode.utils.ComponentUtils;
import org.bukkit.entity.Player;

public class ReloadCommand implements BasicCommand {

    private final Language language = Main.getInstance().getLanguage();

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        if (!(commandSourceStack.getSender().hasPermission("customgamemode.admin") || commandSourceStack.getSender().isOp())) {
            return;
        }

        Player player = (Player) commandSourceStack.getSender();

        PlayerData.reload(PlayerData.FileType.SETTINGS, player.getUniqueId());
        PlayerData.reload(PlayerData.FileType.BALANCE, player.getUniqueId());
        PlayerData.reload(PlayerData.FileType.INDEX, player.getUniqueId());
        PlayerData.reload(PlayerData.FileType.STATS, player.getUniqueId());

        CustomConfig.reload("lang/en_US.yml");

        player.sendMessage(ComponentUtils.component(language.getString("commands.admin.reload")));

    }
}

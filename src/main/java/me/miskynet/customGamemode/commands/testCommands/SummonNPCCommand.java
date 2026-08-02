package me.miskynet.customGamemode.commands.testCommands;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.entity.npc.NPC;
import me.miskynet.customGamemode.utils.ComponentUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SummonNPCCommand implements BasicCommand {

    private final Language language = Main.getInstance().getLanguage();

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        Player player = (Player) commandSourceStack.getSender();

        if (commandSourceStack.getSender() instanceof Player && !player.isOp()) {
            player.sendMessage(ComponentUtils.component(language.getString("commands.general.noPermission")));
            return;
        }

        player.sendMessage(ComponentUtils.component("\n&e&l[!] &r&eThis command is currently in testing state. It may not work as intended and " +
                "may not support some features! Errors may be fixed in the future, else this command will be replaced by an official version!\n"));

        Location location = player.getLocation();

        if (args.length == 0) {
            player.sendMessage(ComponentUtils.component("&eUsage: /summonnpc <shopType> <displayName> <skinHash>"));
        }

        // get and set the custom name
        String customName = (args.length >= 1) ? args[0] : "&dCustomNPC";

        // create the NPC with the custom name and location
        NPC npc = new NPC(ComponentUtils.component(customName), location);

        // since currently, there is only one npc type, it will automatically be set to shop
        // in case there will be more npc types in the future, this will change!
        npc.setInteractMenu(NPC.InteractType.SHOP);

        // get and set the texture hash, if not provided, use a default one
        String textureHash = (args.length >= 3) ? args[2] : "de39ab9468b064c7c01aeddd345217b6dae58b51b37d9342b5a9bbf33f1197d5";
        npc.setTextureHash(textureHash);

        npc.spawn();

        player.sendMessage(ComponentUtils.component(false, "&aSpawned new NPC."));
    }
}

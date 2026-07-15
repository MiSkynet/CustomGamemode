package me.miskynet.customGamemode.commands.testCommands;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.custom.entity.npc.NPC;
import me.miskynet.customGamemode.utils.ComponentUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SummonNPCCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        Player player = (Player) commandSourceStack.getSender();

        Location location = player.getLocation();

        NPC npc = new NPC(ComponentUtils.component("&dCooler NPC"), location);

        if (args.length > 0) {

            String npcType = args[0];

            npc = new NPC(ComponentUtils.component("&d" + npcType), location);

            npc.setInteractMenu(NPC.InteractType.valueOf(npcType.toUpperCase()));
        }

        npc.setSkinHash("de39ab9468b064c7c01aeddd345217b6dae58b51b37d9342b5a9bbf33f1197d5");

        npc.spawn();

        player.sendMessage(ComponentUtils.component(false, "&aSpawned new NPC."));
    }
}

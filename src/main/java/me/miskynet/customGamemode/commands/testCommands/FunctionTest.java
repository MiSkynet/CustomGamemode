package me.miskynet.customGamemode.commands.testCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.miskynet.customGamemode.utils.ComponentUtils;

public class FunctionTest implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        int firstArg = Integer.parseInt(args[0]);

        int xpResult = (int) (100 * Math.pow(firstArg, 1.45));

        commandSourceStack.getSender().sendMessage(ComponentUtils.component(firstArg + " -> " + xpResult));
    }
}

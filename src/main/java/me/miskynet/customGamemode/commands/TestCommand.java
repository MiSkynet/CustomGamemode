package me.miskynet.customGamemode.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.miskynet.customGamemode.custom.Utils;
import me.miskynet.customGamemode.custom.entitys.InteractEntity;
import me.miskynet.customGamemode.custom.menu.Menu;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class TestCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> getBuilder() {

        return Commands.literal("testcommand")
                .requires(source -> source.getSender() instanceof Player && source.getSender().isOp())
                .then(Commands.literal("inventory")
                    .then(Commands.argument("title", StringArgumentType.string())
                        .then(Commands.argument("size", IntegerArgumentType.integer(9, 54))
                            .executes(ctx -> {

                                Player player = (Player) ctx.getSource().getSender();
                                String title = StringArgumentType.getString(ctx, "title");
                                int size = IntegerArgumentType.getInteger(ctx, "size");

                                int validSlots = (size % 9 == 0) ? size : 9;

                                Menu menu = new Menu(Utils.component(title), validSlots);
                                menu.openForPlayer(player);

                                return 1;

                            })
                        )
                    )
                )
                .then(Commands.literal("entity")
                        .executes(ctx -> {
                            Player player = (Player) ctx.getSource().getSender();
                            InteractEntity interactEntity = new InteractEntity(EntityType.VILLAGER);

                            Menu menu = new Menu(Utils.component("Test Menu"), 54);

                            interactEntity.setInteractionMenu(menu);
                            interactEntity.spawnEntity(player);
                            return 1;
                        })
                );


    }

}

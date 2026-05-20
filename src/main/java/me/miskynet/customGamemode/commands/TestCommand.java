package me.miskynet.customGamemode.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.registry.RegistryKey;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.Utils;
import me.miskynet.customGamemode.custom.entitys.InteractEntity;
import me.miskynet.customGamemode.custom.menu.Menu;
import org.bukkit.Registry;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

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
                    .then(Commands.argument("type", StringArgumentType.string())

                            .executes(ctx -> {
                                spawnEntity(ctx, EntityType.VILLAGER);
                                return 1;
                            })
                        .then(Commands.argument("entity", ArgumentTypes.resource(RegistryKey.ENTITY_TYPE))
                            .executes(ctx -> {

                                EntityType entityType = EntityType.VILLAGER;

                                EntityType selectedType = ctx.getArgument("entity", EntityType.class);

                                if (selectedType != null) {
                                    if (Registry.ENTITY_TYPE.stream().collect(Collectors.toList()).contains(selectedType)) {
                                        entityType = selectedType;
                                    }
                                }

                                spawnEntity(ctx, entityType);

                                return 1;
                            })
                        )
                    )
                );

    }

    private static void spawnEntity(CommandContext<CommandSourceStack> ctx, EntityType entityType) {

        if (StringArgumentType.getString(ctx, "type").equalsIgnoreCase("menu")) {
            Player player = (Player) ctx.getSource().getSender();
            InteractEntity interactEntity = new InteractEntity(entityType);

            interactEntity.setInteractionMenu(Main.menuManager.searchId(82019383));
            interactEntity.spawnEntity(player);
        }
        else if (StringArgumentType.getString(ctx, "type").equalsIgnoreCase("talking")) {
            Player player = (Player) ctx.getSource().getSender();
            InteractEntity interactEntity = new InteractEntity(entityType);
            interactEntity.spawnEntity(player);
        }

    }

}

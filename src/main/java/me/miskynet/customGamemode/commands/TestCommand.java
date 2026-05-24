package me.miskynet.customGamemode.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.Utils;
import me.miskynet.customGamemode.custom.entitys.MenuEntity;
import me.miskynet.customGamemode.custom.item.PlayerHead;
import me.miskynet.customGamemode.custom.item.Relic;
import me.miskynet.customGamemode.custom.menu.Menu;
import me.miskynet.customGamemode.custom.menu.TextureMenu;
import org.bukkit.Registry;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class TestCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> getBuilder() {

        return Commands.literal("testcommand")
                .requires(source -> source.getSender() instanceof Player && source.getSender().isOp())

                /*
                 * /testcommand inventory
                 * */
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

                .then(Commands.literal("testinv")
                    .executes(ctx -> {

                        Player player = (Player) ctx.getSource().getSender();

                        TextureMenu textureMenu = new TextureMenu(Utils.component("Flower Extractor"), 27, "\uE003");
                        ArrayList<Integer> slots = new ArrayList<>();
                        slots.add(11);
                        slots.add(12);
                        slots.add(15);
                        textureMenu.setInteractSlots(slots);

                        textureMenu.openForPlayer(player);

                        return 1;
                    })
                )

                /*
                * /testcommand entity
                * */
                .then(Commands.literal("entity")
                        .then(Commands.literal("interact")
                            .then(Commands.argument("entityType", StringArgumentType.string())
                                .executes(ctx -> {

                                    EntityType entityType = EntityType.VILLAGER;

                                    EntityType selectedType = ctx.getArgument("entity", EntityType.class);

                                    if (selectedType != null) {
                                        if (Registry.ENTITY_TYPE.stream().collect(Collectors.toList()).contains(selectedType)) {
                                            entityType = selectedType;
                                        }
                                    }

                                    if (StringArgumentType.getString(ctx, "type").equalsIgnoreCase("menu")) {
                                        Player player = (Player) ctx.getSource().getSender();
                                        MenuEntity menuEntity = new MenuEntity(entityType, Main.menuManager.searchId(82019383));

                                        menuEntity.spawnEntity(player);
                                    }
                                    else if (StringArgumentType.getString(ctx, "type").equalsIgnoreCase("talking")) {
                                        Player player = (Player) ctx.getSource().getSender();
                                        MenuEntity menuEntity = new MenuEntity(entityType, Main.menuManager.searchId(82019383));
                                        menuEntity.spawnEntity(player);
                                    }

                                    return 1;
                                })
                            )
                        )
                )

                /*
                 * /testcommand give
                 * */
                .then(Commands.literal("give")
                        .then(Commands.literal("skull")
                                .then(Commands.argument("skullHash", StringArgumentType.string())
                                        .executes(ctx -> {
                                            Player player = (Player) ctx.getSource().getSender();

                                            String hash = StringArgumentType.getString(ctx, "skullHash");

                                            PlayerHead head = new PlayerHead(hash);

                                            head.setDisplayName(Utils.component(false, "&rCustom Head"));

                                            player.getInventory().addItem(head.toItemStack());
                                            return 1;
                                        })
                                        .then(Commands.argument("displayName", StringArgumentType.string())
                                                .executes(ctx -> {
                                                    Player player = (Player) ctx.getSource().getSender();

                                                    String hash = StringArgumentType.getString(ctx, "skullHash");
                                                    String displayName = StringArgumentType.getString(ctx, "displayName");

                                                    PlayerHead head = new PlayerHead(hash);

                                                    head.setDisplayName(Utils.component(false, displayName));

                                                    player.getInventory().addItem(head.toItemStack());
                                                    return 1;
                                                })
                                        )
                                )
                        )

                        .then(Commands.literal("relic")
                                .executes(ctx -> {
                                    Player player = (Player) ctx.getSource().getSender();

                                    int randomInt = ThreadLocalRandom.current().nextInt(1, 100);
                                    float randomFloat = 0;

                                    if (randomInt < 2) randomFloat = (float) ThreadLocalRandom.current().nextDouble(4.0, 5.0);
                                    else if (randomInt < 8) randomFloat = (float) ThreadLocalRandom.current().nextDouble(3.0, 4.0);
                                    else if (randomInt < 25) randomFloat = (float) ThreadLocalRandom.current().nextDouble(2.0, 3.0);
                                    else if (randomInt < 50) randomFloat = (float) ThreadLocalRandom.current().nextDouble(1.0, 2.0);
                                    else if (randomInt < 100) randomFloat = (float) ThreadLocalRandom.current().nextDouble(0.1, 1.0);

                                    float purity = Math.round(randomFloat * 10.0f) / 10.0f;

                                    Relic relic = new Relic(purity);
                                    player.getInventory().addItem(relic.toItemStack());

                                    return 1;
                                })
                        )
                );
    }




}

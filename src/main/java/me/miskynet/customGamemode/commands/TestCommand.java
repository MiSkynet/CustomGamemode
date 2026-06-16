package me.miskynet.customGamemode.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.economy.EconomyManager;
import me.miskynet.customGamemode.custom.menu.shop.Shop;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.entity.Player;


public class TestCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> getBuilder() {

        /*
         * /testcommand inventory
         * */
        return Commands.literal("testcommand")
                .requires(source -> source.getSender() instanceof Player && source.getSender().isOp())

                /*
                 * /testcommand inventory
                 * */
                /*

                .then(Commands.argument("getRelic", StringArgumentType.string())
                    .executes(ctx -> {
                        Player player = (Player) ctx.getSource().getSender();

                        Relic relic = new Relic();

                        player.getInventory().addItem(relic.toItemStack());

                        return 1;
                    })
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1, 50))
                            .executes(ctx -> {

                                Integer amount = ctx.getArgument("amount", Integer.class);
                                Player player = (Player) ctx.getSource().getSender();

                                for (int i = 0; i < amount; i++) {
                                    Relic relic = new Relic();
                                    player.getInventory().addItem(relic.toItemStack());
                                }

                                return 1;
                            })
                        )
                )

                .executes(ctx -> {
                    Player player = (Player) ctx.getSource().getSender();

                    TextureMenu flowerExtractor = MenuManager.getFlowerExtractorMenu();
                    flowerExtractor.openForPlayer(player);


                    return 1;
                });


                // open the shop

                */

                .then(Commands.literal("shop")
                        .executes(ctx -> {

                            Player player = (Player) ctx.getSource().getSender();
                            Shop shop = new Shop(0);
                            shop.openForPlayer(player);

                            return 1;
                        })
                )

                .then(Commands.literal("balance")
                        .executes(ctx -> {
                            Player player = (Player) ctx.getSource().getSender();

                            player.sendMessage(Utils.component("&7Your Balance: &6" + Main.economyManager.getBalance(player)));

                            Main.economyManager.addBalance(player, 5);

                            return 1;
                        })
                );
    }
}

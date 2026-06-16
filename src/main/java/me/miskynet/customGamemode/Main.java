package me.miskynet.customGamemode;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.miskynet.customGamemode.commands.Eco;
import me.miskynet.customGamemode.commands.ShopCommand;
import me.miskynet.customGamemode.custom.economy.EconomyListener;
import me.miskynet.customGamemode.custom.economy.EconomyManager;
import me.miskynet.customGamemode.custom.menu.shop.Shop;
import me.miskynet.customGamemode.custom.menu.shop.ShopListener;
import me.miskynet.customGamemode.utils.customConfig.CustomConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Main extends JavaPlugin {

    public static EconomyManager economyManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Preload all shop items
        Shop.cacheShopItems();

        // config setup
        CustomConfig.setup("economy/playerData.yml");

        economyManager = new EconomyManager();

        // listener setup
        getServer().getPluginManager().registerEvents(new ShopListener(), this);
        getServer().getPluginManager().registerEvents(new EconomyListener(), this);

        // command setup
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS.newHandler(event -> {
            var registrar = event.registrar();

            registrar.register("eco","Economy command",new Eco());
            registrar.register("shop","Shop command",new ShopCommand());
        }));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return Main.getPlugin(Main.class);
    }

}

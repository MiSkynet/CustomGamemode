package me.miskynet.customGamemode;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.miskynet.customGamemode.commands.SettingsCommand;
import me.miskynet.customGamemode.commands.ToggleScoreboard;
import me.miskynet.customGamemode.commands.economy.EcoCommand;
import me.miskynet.customGamemode.commands.ShopCommand;
import me.miskynet.customGamemode.commands.economy.PayCommand;
import me.miskynet.customGamemode.custom.economy.EconomyManager;
import me.miskynet.customGamemode.custom.menu.settings.SettingsListener;
import me.miskynet.customGamemode.custom.menu.shop.ItemPreviewListener;
import me.miskynet.customGamemode.custom.menu.shop.Shop;
import me.miskynet.customGamemode.custom.menu.shop.ShopListener;
import me.miskynet.customGamemode.custom.scoreboard.ScoreboardManager;
import me.miskynet.customGamemode.listener.OnJoin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static EconomyManager economyManager;
    public static ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // preload all shop items
        Shop.cacheShopItems();

        // setup managers
        economyManager = new EconomyManager();
        scoreboardManager = new ScoreboardManager();

        scoreboardManager.runUpdates();

        // listener setup
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new OnJoin(), this);
        pluginManager.registerEvents(new ShopListener(), this);
        pluginManager.registerEvents(new SettingsListener(), this);
        pluginManager.registerEvents(new ItemPreviewListener(), this);

        // command setup
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS.newHandler(event -> {
            var registrar = event.registrar();

            registrar.register("togglescoreboard", new ToggleScoreboard());
            registrar.register("settings", new SettingsCommand());
            registrar.register("eco", new EcoCommand());
            registrar.register("pay", new PayCommand());
            registrar.register("shop", new ShopCommand());
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

package me.miskynet.customGamemode;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.miskynet.customGamemode.commands.*;
import me.miskynet.customGamemode.commands.admin.ReloadCommand;
import me.miskynet.customGamemode.commands.testCommands.*;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.economy.EconomyManager;
import me.miskynet.customGamemode.custom.enchantments.mole.MoleListener;
import me.miskynet.customGamemode.custom.entity.npc.NPCInteractEvent;
import me.miskynet.customGamemode.custom.entity.npc.NPCMoveEvent;
import me.miskynet.customGamemode.custom.index.IndexMenu;
import me.miskynet.customGamemode.custom.index.levelingSystem.IndexLevelingSystem;
import me.miskynet.customGamemode.custom.index.levelingSystem.IndexLevelingSystemListener;
import me.miskynet.customGamemode.custom.index.listener.IndexMenuListener;
import me.miskynet.customGamemode.custom.index.levelingSystem.LevelUpListener;
import me.miskynet.customGamemode.custom.settings.SettingsListener;
import me.miskynet.customGamemode.custom.shop.ShopMenu;
import me.miskynet.customGamemode.custom.shop.itemPreview.ItemPreviewListener;
import me.miskynet.customGamemode.custom.shop.ShopListener;
import me.miskynet.customGamemode.custom.scoreboard.ScoreboardManager;
import me.miskynet.customGamemode.listener.OnJoin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private EconomyManager economyManager;
    private ScoreboardManager scoreboardManager;
    private Language language;
    private IndexLevelingSystem levelingSystem;

    @Override
    public void onEnable() {

        // preload all shop items
        ShopMenu.cacheShopItems();

        setupManagers();
        setupCommands();
        setupListener();

        scoreboardManager.runUpdates();

        IndexMenu.createRewardList();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return Main.getPlugin(Main.class);
    }

    private void setupManagers() {
        economyManager = new EconomyManager();
        scoreboardManager = new ScoreboardManager();
        language = new Language();
        levelingSystem = new IndexLevelingSystem();
    }

    // setup commands
    private void setupCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS.newHandler(event -> {
            var registrar = event.registrar();

            registrar.register("togglescoreboard", new ToggleScoreboard());
            registrar.register("settings", new SettingsCommand());
            registrar.register("eco", new EcoCommand());
            registrar.register("shop", new ShopCommand());
            registrar.register("index", new IndexMenuCommand());
            registrar.register("level", new GetLevel());registrar.register("reload", new ReloadCommand());


            // test commands
            registrar.register("summonnpc", new SummonNPCCommand());
        }));
    }

    // setup listeners
    private void setupListener() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new OnJoin(), this);

        // shop listener
        pluginManager.registerEvents(new ShopListener(), this);
        pluginManager.registerEvents(new ItemPreviewListener(), this);

        // settings listener
        pluginManager.registerEvents(new SettingsListener(), this);

        // npc listener
        pluginManager.registerEvents(new NPCMoveEvent(), this);
        pluginManager.registerEvents(new NPCInteractEvent(), this);

        // index listener
        pluginManager.registerEvents(new IndexMenuListener(), this);

        // leveling listener
        pluginManager.registerEvents(new IndexLevelingSystemListener(), this);
        pluginManager.registerEvents(new LevelUpListener(), this);

        // mole listener
        pluginManager.registerEvents(new MoleListener(), this);
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public Language getLanguage() {
        return language;
    }

    public IndexLevelingSystem getLevelingSystem() {
        return levelingSystem;
    }

}

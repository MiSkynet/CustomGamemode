package me.miskynet.customGamemode;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.miskynet.customGamemode.commands.admin.ReloadCommand;
import me.miskynet.customGamemode.commands.GetLevel;
import me.miskynet.customGamemode.commands.IndexMenuCommand;
import me.miskynet.customGamemode.commands.SettingsCommand;
import me.miskynet.customGamemode.commands.ShopCommand;
import me.miskynet.customGamemode.commands.eco.EcoCommandManager;
import me.miskynet.customGamemode.commands.testCommands.*;
import me.miskynet.customGamemode.commands.ToggleScoreboard;
import me.miskynet.customGamemode.custom.config.CustomConfig;
import me.miskynet.customGamemode.custom.config.Language;
import me.miskynet.customGamemode.custom.economy.EconomyManager;
import me.miskynet.customGamemode.experimental.enchantments.mole.MoleListener;
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
import me.miskynet.customGamemode.experimental.structures.RegisterStructures;
import me.miskynet.customGamemode.experimental.structures.overworld.StructurePlaceListener;
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

        CustomConfig.setup("config.yml");

        setupManagers();
        setupCommands();

        // register the structures
        RegisterStructures.registerStructures();

        setupListener();

        scoreboardManager.runUpdates();

        // preload all shop items
        ShopMenu.cacheShopItems();
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
        language = new Language();
        economyManager = new EconomyManager();
        scoreboardManager = new ScoreboardManager();
        levelingSystem = new IndexLevelingSystem();
    }

    // setup commands
    private void setupCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS.newHandler(event -> {
            var registrar = event.registrar();

            registrar.register("togglescoreboard", new ToggleScoreboard());
            registrar.register("settings", new SettingsCommand());

            registrar.register("eco", new EcoCommandManager());

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

        // register structure place listener to place all the structures
        pluginManager.registerEvents(new StructurePlaceListener(), this);
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

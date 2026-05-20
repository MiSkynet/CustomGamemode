package me.miskynet.customGamemode;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.miskynet.customGamemode.commands.TestCommand;
import me.miskynet.customGamemode.custom.menu.MenuManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static MenuManager menuManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.menuManager = new MenuManager();

        getServer().getPluginManager().registerEvents(new me.miskynet.customGamemode.custom.menu.Listener(), this);
        getServer().getPluginManager().registerEvents(new me.miskynet.customGamemode.custom.entitys.Listener(), this);

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS.newHandler(event -> {
            event.registrar().register(TestCommand.getBuilder().build());
        }));

        this.menuManager.menuSetup();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return Main.getPlugin(Main.class);
    }

}

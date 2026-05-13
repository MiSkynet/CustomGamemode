package me.miskynet.customGamemode;

import me.miskynet.customGamemode.commands.TestCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getPluginManager().registerEvents(new me.miskynet.customGamemode.custom.menu.Listener(), this);
        getServer().getPluginManager().registerEvents(new me.miskynet.customGamemode.custom.entitys.Listener(), this);

        getCommand("testcommand").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return Main.getPlugin(Main.class);
    }

}

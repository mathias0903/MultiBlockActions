package io.github.mathias0903.LocksAndLockpicking;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {

        getLogger().info("onEnable is called!");
    }
    @Override
    public void onDisable() {

        getLogger().info("onDisable is called!");
    }

    public static Main getInstance() {
        return instance;
    }
}
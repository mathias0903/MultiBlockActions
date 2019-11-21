package io.github.mathias0903.MultiBlockActions;

import io.github.mathias0903.MultiBlockActions.Listener.BlockClick;
import io.github.mathias0903.MultiBlockActions.Manager.MultiBlockManager;
import io.github.mathias0903.MultiBlockActions.misc.RelativeLocation;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    private MultiBlockManager multiBlockManager;

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(LockedMultiBlock.class);
        ConfigurationSerialization.registerClass(FreeMultiBlock.class);
        ConfigurationSerialization.registerClass(RelativeLocation.class);

        multiBlockManager = new MultiBlockManager();

        getServer().getPluginManager().registerEvents(new BlockClick(), this);

        getLogger().info("onEnable is called!");
    }
    @Override
    public void onDisable() {

        getLogger().info("onDisable is called!");
    }

    public static Main getInstance() {
        return instance;
    }
    public MultiBlockManager getMultiBlockManager() {
        return multiBlockManager;
    }
}
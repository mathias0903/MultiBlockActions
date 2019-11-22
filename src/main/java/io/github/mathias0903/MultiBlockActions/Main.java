package io.github.mathias0903.MultiBlockActions;

import io.github.mathias0903.MultiBlockActions.Commands.MainCommand;
import io.github.mathias0903.MultiBlockActions.Listener.BlockClick;
import io.github.mathias0903.MultiBlockActions.Manager.Config;
import io.github.mathias0903.MultiBlockActions.Manager.MultiBlockManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    private static Main instance;
    private MultiBlockManager multiBlockManager;
    private Config langConfig;
    private Config config;

    public Config getLangConfig() {
        return langConfig;
    }

    public Config getPluginConfig() {
        return config;
    }

    @Override
    public void onEnable() {
        instance = this;
        try {
            createMissingFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadFiles();
        multiBlockManager = new MultiBlockManager();
        multiBlockManager.loadMultiBlocks();

        getServer().getPluginManager().registerEvents(new BlockClick(), this);

        this.getCommand("MultiBlock").setExecutor(new MainCommand());

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

    public void loadFiles(){
        this.config = new Config("Config.yml", this);
        File langFile = new File("Lang/"+this.config.getConfig().getString("Lang")+".yml");
        if(langFile.exists()) {
            this.langConfig = new Config(new File(Main.getInstance().getDataFolder(), "Lang"), this.config.getConfig().getString("Lang"), this);
        } else {
            this.getLogger().warning("Language File not found using default Language file");
            this.langConfig = new Config(new File(this.getDataFolder(), "Lang"), "en.yml", this);
        }
    }
    public void createMissingFiles() throws IOException {
        File configFile = new File("Config.yml");
        if(!configFile.exists()) {
            FileUtils.copyToFile(this.getResource("Config.yml"), new File("Config.yml"));
        }
        File langFileEn = new File("Lang/en.yml");
        if(!langFileEn.exists()) {
            FileUtils.copyToFile(this.getResource("Lang/en.yml"), new File("Lang/en.yml"));
        }
    }
}
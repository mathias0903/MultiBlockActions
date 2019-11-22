package io.github.mathias0903.MultiBlockActions.misc;

import io.github.mathias0903.MultiBlockActions.Main;
import io.github.mathias0903.MultiBlockActions.Manager.Config;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Utils {
    private static FileConfiguration langConfig = Main.getInstance().getLangConfig().getConfig();
    public static String lang(String str) {
        return ChatColor.translateAlternateColorCodes('&', langConfig.getString("prefix"))+ChatColor.translateAlternateColorCodes('&', langConfig.getString(str));
    }
}

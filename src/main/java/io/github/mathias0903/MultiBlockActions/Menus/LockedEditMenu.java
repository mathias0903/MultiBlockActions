package io.github.mathias0903.MultiBlockActions.Menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class LockedEditMenu implements Listener {
    private HashMap<String, Inventory> pages;
    private Player p;

    public LockedEditMenu(Player p) {
        this.pages = new HashMap<>();
        this.p = p;
    }
    public Inventory getFrontPage() {
        Inventory inv = Bukkit.createInventory(null, 9, );
        }
    }
}

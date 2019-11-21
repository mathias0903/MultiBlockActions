package io.github.mathias0903.MultiBlockActions.Manager;

import io.github.mathias0903.MultiBlockActions.FreeMultiBlock;
import io.github.mathias0903.MultiBlockActions.LockedMultiBlock;
import io.github.mathias0903.MultiBlockActions.Main;
import io.github.mathias0903.MultiBlockActions.misc.Action;
import io.github.mathias0903.MultiBlockActions.misc.RelativeLocation;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultiBlockManager {
    private ArrayList<LockedMultiBlock> lockedMultiBlocks;
    private ArrayList<FreeMultiBlock> freeMultiBlocks;
    private File multiBlockFolder;

    public MultiBlockManager() {
        this.lockedMultiBlocks = new ArrayList<>();
        this.freeMultiBlocks = new ArrayList<>();
        this.multiBlockFolder = new File(Main.getInstance().getDataFolder(), "MultiBlocks");
        this.multiBlockFolder.mkdirs();
    }

    public void loadMultiBlocks() {
        File[] multiBlocks = multiBlockFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if(multiBlocks != null) {
            for (File multiBlock : multiBlocks) {
                Config multiBlockConfig = new Config(multiBlockFolder, multiBlock.getName(), Main.getInstance());
                if (multiBlock.getName().startsWith("Locked")) {
                    lockedMultiBlocks.add(lockedFromConfig(multiBlockConfig));
                } else if (multiBlock.getName().startsWith("Free")) {
                    //freeMultiBlocks.add((FreeMultiBlock) multiBlockConfig.get("MultiBlock"));
                }
            }
        }
    }
    public void saveMultiBlocks() {
        for(LockedMultiBlock lockedMultiBlock: lockedMultiBlocks) {
            Config multiBlockConfig = new Config(multiBlockFolder, "Locked_"+lockedMultiBlock.getId(), Main.getInstance());
            lockedToConfig(multiBlockConfig, lockedMultiBlock);
        }
        for(FreeMultiBlock freeMultiBlock: freeMultiBlocks) {
            Config multiBlockConfig = new Config(multiBlockFolder, "Free_"+freeMultiBlock.getId(), Main.getInstance());
            multiBlockConfig.set("MultiBlock", freeMultiBlock);
            multiBlockConfig.save();
        }
    }
    public void lockedToConfig(Config config, LockedMultiBlock multiBlock) {
        config.set("id", multiBlock.getId());
        for (Map.Entry me : multiBlock.getBlocks().entrySet()) {
            RelativeLocation loc = (RelativeLocation) me.getKey();
            BlockData data = (BlockData) me.getValue();
            config.set("blocks."+loc.getX()+"."+loc.getY()+"."+loc.getZ(), data.getAsString());
        }
        ArrayList<String> keyBlocks = new ArrayList<>();
        for (RelativeLocation loc : multiBlock.getKeyBlocks()) {
            keyBlocks.add(loc.getX()+";"+loc.getY()+";"+loc.getZ());
        }
        config.set("keyBlocks", keyBlocks);
        config.set("actionType", multiBlock.getActionType().name());
        config.set("command", multiBlock.getCommand());

        config.save();
    }
    public LockedMultiBlock lockedFromConfig(Config config) {
        FileConfiguration con = config.getConfig();
        String id = con.getString("id");
        ConfigurationSection configBlocks = con.getConfigurationSection("blocks");
        HashMap<RelativeLocation,BlockData> blocks = new HashMap<>();
        for (String str : configBlocks.getKeys(true)) {
            String[] strCords = str.split("\\.");
            if(strCords.length == 3) {
                BlockData data = Main.getInstance().getServer().createBlockData(configBlocks.getString(str));
                RelativeLocation loc = new RelativeLocation(Integer.parseInt(strCords[0]), Integer.parseInt(strCords[1]), Integer.parseInt(strCords[2]));
                blocks.put(loc, data);
            }
        }
        ArrayList<RelativeLocation> relativeLocations = new ArrayList<>();
        for (String str : con.getStringList("keyBlocks")) {
            String[] strCords = str.split(";");
            relativeLocations.add(new RelativeLocation(Integer.parseInt(strCords[0]), Integer.parseInt(strCords[1]), Integer.parseInt(strCords[2])));
        }
        Action action = Action.valueOf(con.getString("actionType"));
        String command = con.getString("command");
        Main.getInstance().getLogger().info("Loaded MultiBlock: "+id);
        return new LockedMultiBlock(id, blocks,relativeLocations,action,command);
    }

    public ArrayList<LockedMultiBlock> getLockedMultiBlocks() {
        return lockedMultiBlocks;
    }

    public void setLockedMultiBlocks(ArrayList<LockedMultiBlock> lockedMultiBlocks) {
        this.lockedMultiBlocks = lockedMultiBlocks;
    }

    public void addLockedMultiBlocks(LockedMultiBlock lockedMultiBlock) {
        this.lockedMultiBlocks.add(lockedMultiBlock);
        saveMultiBlocks();
    }

    public ArrayList<FreeMultiBlock> getFreeMultiBlocks() {
        return freeMultiBlocks;
    }

    public void setFreeMultiBlocks(ArrayList<FreeMultiBlock> freeMultiBlocks) {
        this.freeMultiBlocks = freeMultiBlocks;
    }
}

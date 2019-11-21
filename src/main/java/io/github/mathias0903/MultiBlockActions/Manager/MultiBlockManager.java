package io.github.mathias0903.MultiBlockActions.Manager;

import io.github.mathias0903.MultiBlockActions.FreeMultiBlock;
import io.github.mathias0903.MultiBlockActions.LockedMultiBlock;
import io.github.mathias0903.MultiBlockActions.Main;
import io.github.mathias0903.MultiBlockActions.misc.RelativeLocation;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Map;

public class MultiBlockManager {
    private ArrayList<LockedMultiBlock> lockedMultiBlocks;
    private ArrayList<FreeMultiBlock> freeMultiBlocks;
    private File multiBlockFolder = new File(Main.getInstance().getDataFolder(), "MultiBlocks");

    public void loadMultiBlocks() {
        File[] multiBlocks = multiBlockFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        for (File multiBlock : multiBlocks) {
            FileConfiguration multiBlockConfig = new Config(multiBlockFolder, multiBlock.getName(), Main.getInstance()).getConfig();
            if(multiBlock.getName().startsWith("Locked")) {
                lockedMultiBlocks.add((LockedMultiBlock) multiBlockConfig.get(""));
            }
            else if(multiBlock.getName().startsWith("Free")) {
                freeMultiBlocks.add((FreeMultiBlock) multiBlockConfig.get(""));
            }
        }
    }
    public void saveMultiBlocks() {
        for(LockedMultiBlock lockedMultiBlock: lockedMultiBlocks) {
            FileConfiguration multiBlockConfig = new Config(multiBlockFolder, "Locked_"+lockedMultiBlock.getId(), Main.getInstance()).getConfig();
            multiBlockConfig.set("", lockedMultiBlock);
        }
        for(FreeMultiBlock freeMultiBlock: freeMultiBlocks) {
            FileConfiguration multiBlockConfig = new Config(multiBlockFolder, "Free_"+freeMultiBlock.getId(), Main.getInstance()).getConfig();
            multiBlockConfig.set("", freeMultiBlock);
        }
    }

    public ArrayList<LockedMultiBlock> getLockedMultiBlocks() {
        return lockedMultiBlocks;
    }

    public void setLockedMultiBlocks(ArrayList<LockedMultiBlock> lockedMultiBlocks) {
        this.lockedMultiBlocks = lockedMultiBlocks;
    }

    public void addLockedMultiBlocks(LockedMultiBlock lockedMultiBlocks) {
        this.lockedMultiBlocks.add(lockedMultiBlocks);
    }

    public ArrayList<FreeMultiBlock> getFreeMultiBlocks() {
        return freeMultiBlocks;
    }

    public void setFreeMultiBlocks(ArrayList<FreeMultiBlock> freeMultiBlocks) {
        this.freeMultiBlocks = freeMultiBlocks;
    }
}

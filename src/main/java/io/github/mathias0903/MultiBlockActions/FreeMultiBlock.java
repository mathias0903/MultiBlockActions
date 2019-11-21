package io.github.mathias0903.MultiBlockActions;

import io.github.mathias0903.MultiBlockActions.misc.Action;
import io.github.mathias0903.MultiBlockActions.misc.RelativeLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FreeMultiBlock {
    private String id;
    private ArrayList<Material> blocks = new ArrayList<Material>();
    private ArrayList<Material> keyBlocks = new ArrayList<Material>();
    // since RelativeLocation has the 3 varibles x,y,z its used her as size
    private RelativeLocation size;
    private Action actionType;
    private String command;

    public FreeMultiBlock(String id, ArrayList<Material> blocks, ArrayList<Material> keyBlocks,RelativeLocation size, Action actionType, String command) {
        this.id = id;
        this.blocks = blocks;
        this.keyBlocks = keyBlocks;
        this.size = size;
        this.actionType = actionType;
        this.command = command;
    }

    public boolean isFreeMultiBlock(Block sb) {
        for(int x = 0; x <= size.getX(); x++) {
            for(int y = 0; y <= size.getY(); y++) {
                for(int z = 0; z <= size.getZ(); z++) {
                    World world;
                    Location min = new Location(sb.getWorld(), sb.getX()-x, sb.getY()-y, sb.getZ()-z);
                    Location max = new Location(min.getWorld(), min.getX()+size.getX(), min.getY()+size.getY(), min.getZ()+size.getZ());
                    ArrayList<Material> containedBlocks = new ArrayList<>();
                    for(int ix = min.getBlockX(); ix <= max.getBlockX(); ix++) {
                        for(int iy = min.getBlockY(); iy <= max.getBlockY(); iy++) {
                            for(int iz = min.getBlockZ(); iz <= max.getBlockZ(); iz++) {
                                Location loc = new Location(min.getWorld(), min.getX()+ix, min.getY()+iy, min.getZ()+iz);
                                containedBlocks.add(loc.getBlock().getType());
                            }
                        }
                    }
                    if(containedBlocks.containsAll(blocks)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //checks if the provided block's type matches any block type defined in keyBlocks
    public boolean isKeyBlock(Block clickedBlock) {
        return keyBlocks.contains(clickedBlock.getType());
    }
    public void doAction(Player p) {
        if(actionType.equals(Action.COMMAND)) {
            p.performCommand(command);
        }
        else if(actionType.equals(Action.COMMANDASOP)) {
            p.setOp(true);
            p.performCommand(command);
            p.setOp(false);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Material> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Material> blocks) {
        this.blocks = blocks;
    }

    public ArrayList<Material> getKeyBlocks() {
        return keyBlocks;
    }

    public void setKeyBlocks(ArrayList<Material> keyBlocks) {
        this.keyBlocks = keyBlocks;
    }

    public RelativeLocation getSize() {
        return size;
    }

    public void setSize(RelativeLocation size) {
        this.size = size;
    }

    public Action getActionType() {
        return actionType;
    }

    public void setActionType(Action actionType) {
        this.actionType = actionType;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}

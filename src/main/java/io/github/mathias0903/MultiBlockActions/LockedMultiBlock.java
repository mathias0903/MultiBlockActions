package io.github.mathias0903.MultiBlockActions;

import io.github.mathias0903.MultiBlockActions.misc.Action;
import io.github.mathias0903.MultiBlockActions.misc.RelativeLocation;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

public class LockedMultiBlock implements ConfigurationSerializable {
    private String id;
    private HashMap<RelativeLocation, BlockData> blocks;
    private ArrayList<RelativeLocation> keyBlocks;
    private Action actionType;
    private String command;

    public LockedMultiBlock(String id, HashMap<RelativeLocation, BlockData> blocks, ArrayList<RelativeLocation> keyBlocks, Action actionType, String command) {
        this.id = id;
        this.blocks = blocks;
        this.keyBlocks = keyBlocks;
        this.actionType = actionType;
        this.command = command;
    }
    public LockedMultiBlock(Map<String, Object> serializedLockedMultiBlock) {
        this.id = (String) serializedLockedMultiBlock.get("id");
        this.blocks = (HashMap<RelativeLocation, BlockData>) serializedLockedMultiBlock.get("blocks");
        this.actionType = Action.valueOf((String) serializedLockedMultiBlock.get("actionType"));
        this.command = (String) serializedLockedMultiBlock.get("command");

        ArrayList<Map<String, Object>> mappedRelativeLocation = (ArrayList<Map<String, Object>>) serializedLockedMultiBlock.get("keyBlocks");
        ArrayList<RelativeLocation> deserializedRelativeLocationContainer = new ArrayList<>(); // temporary container for deserialized allergies
        for (Map<String, Object> serializedRelativeLocation : mappedRelativeLocation)
            deserializedRelativeLocationContainer.add(new RelativeLocation(serializedRelativeLocation));
        this.keyBlocks = deserializedRelativeLocationContainer;
    }

    public boolean isLockedMultiBlock(Block startBlock) {
        long start = currentTimeMillis();
        if(this.blocks.containsValue(startBlock.getBlockData())) {
            for (Map.Entry me : this.blocks.entrySet()) {
                if(me.getValue().equals(startBlock.getBlockData())) {
                    HashMap<RelativeLocation, BlockData> copyBlocks = (HashMap<RelativeLocation, BlockData>) this.blocks.clone();
                    for (int i = 0; i < 4; i++) {
                        RelativeLocation clickedLocation = (RelativeLocation) me.getKey();
                        boolean isCorrect = true;
                        if(keyBlocks.isEmpty() || keyBlocks.contains(clickedLocation)) {
                            Location origin = new Location(startBlock.getWorld(), startBlock.getX() - clickedLocation.getX(), startBlock.getY() - clickedLocation.getY(), startBlock.getZ() - clickedLocation.getZ());
                            for (Map.Entry inner : copyBlocks.entrySet()) {
                                RelativeLocation relative = (RelativeLocation) inner.getKey();
                                BlockData blockData_is = origin.add(relative.getX(), relative.getY(), relative.getZ()).getBlock().getBlockData();
                                BlockData blockData_should = (BlockData) inner.getValue();
                                if (!blockData_is.equals(blockData_should)) {
                                    isCorrect = false;
                                    break;
                                }
                            }
                        } else {isCorrect = false;}
                        if(isCorrect) {
                            long taken = currentTimeMillis()-start;
                            Main.getInstance().getLogger().info("Check took: "+taken+" ms");
                            Main.getInstance().getLogger().info("Check returned postive");
                            Main.getInstance().getLogger().info("Id: "+id);
                            Main.getInstance().getLogger().info("ActionType: "+actionType);
                            Main.getInstance().getLogger().info("Command: "+command);
                            return true;
                        } else {
                            HashMap<RelativeLocation, BlockData> rotated = new HashMap<>();
                            for (Map.Entry inner : copyBlocks.entrySet()) {
                                RelativeLocation relative = (RelativeLocation) inner.getKey();
                                rotated.put(relative.rotateCW(), (BlockData) inner.getValue());
                            }
                            copyBlocks = rotated;
                        }
                    }
                }
            }
        }
        long taken = currentTimeMillis()-start;
        Main.getInstance().getLogger().info("Check took: "+taken+" ms");
        Main.getInstance().getLogger().info("Check returned negative");
        return false;
    }
    //checks if the provided block's BlockData equals any BlockData at RelativeLocations saved in keyBlocks
    //it does not check if its part of a multiblock or if its placed correctly.
    //for that use it in conjoertion with isLockedMultiBlock
    public boolean isKeyBlock(Block clickedBlock) {
        if(keyBlocks.isEmpty())  {
            return true;
        } else {
            for(RelativeLocation loc : keyBlocks) {
                if(blocks.get(loc).equals(clickedBlock.getBlockData())) {
                    return true;
                }
            }
            return false;
        }
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

    public HashMap<RelativeLocation, BlockData> getBlocks() {
        return blocks;
    }

    public void setBlocks(HashMap<RelativeLocation, BlockData> blocks) {
        this.blocks = blocks;
    }

    public ArrayList<RelativeLocation> getKeyBlocks() {
        return keyBlocks;
    }

    public void setKeyBlocks(ArrayList<RelativeLocation> keyBlocks) {
        this.keyBlocks = keyBlocks;
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

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> mapSerializer = new HashMap<>();

        mapSerializer.put("id", id);
        mapSerializer.put("blocks", blocks);
        mapSerializer.put("actionType", actionType.name());
        mapSerializer.put("command", command);



        ArrayList<Map<String, Object>> tempSerializeKeyBlocks = new ArrayList<>(); // Using this as temporary container allowing us to serialize each allergy into a list
        for(RelativeLocation keyBlock : this.keyBlocks)
            tempSerializeKeyBlocks.add(keyBlock.serialize());
        mapSerializer.put("keyBlocks", tempSerializeKeyBlocks);

        return mapSerializer;
    }
}

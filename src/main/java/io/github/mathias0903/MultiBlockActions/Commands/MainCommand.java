package io.github.mathias0903.MultiBlockActions.Commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.internal.annotation.Selection;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import io.github.mathias0903.MultiBlockActions.LockedMultiBlock;
import io.github.mathias0903.MultiBlockActions.Main;
import io.github.mathias0903.MultiBlockActions.Manager.MultiBlockManager;
import io.github.mathias0903.MultiBlockActions.misc.Action;
import io.github.mathias0903.MultiBlockActions.misc.RelativeLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class MainCommand implements CommandExecutor {
    WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
    MultiBlockManager multiBlockManager = Main.getInstance().getMultiBlockManager();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            sender.sendMessage("player, length: "+args.length);
            sender.sendMessage(args);
            if(args.length == 2) {
                sender.sendMessage("succese");
                if(args[0].equals("Locked")) {
                    sender.sendMessage("dobule succese");
                    Player p = (Player) sender;
                    LocalSession session = worldEdit.getSession(p);
                    try {
                        Region selection = session.getSelection(session.getSelectionWorld());
                        BlockVector3 min = selection.getMinimumPoint();
                        BlockVector3 max = selection.getMaximumPoint();
                        HashMap<RelativeLocation, BlockData> blocks = new HashMap<>();
                        for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                            for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                                    RelativeLocation relaLoc = new RelativeLocation(x - min.getBlockX(), y - min.getBlockY(), z - min.getBlockZ());
                                    BlockData data = new Location(p.getWorld(), x, y, z).getBlock().getBlockData();
                                    blocks.put(relaLoc, data);
                                }
                            }
                        }
                        LockedMultiBlock locked = new LockedMultiBlock(args[1], blocks, new ArrayList<>(), Action.COMMANDASOP, "say ive done it");
                        p.sendMessage(locked.getId());
                        p.sendMessage(locked.getKeyBlocks().toString());
                        p.sendMessage(locked.getBlocks().toString());
                        p.sendMessage(locked.getActionType().name());
                        p.sendMessage(locked.getCommand());
                        multiBlockManager.addLockedMultiBlocks(locked);
                        return true;
                    } catch (IncompleteRegionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
}
